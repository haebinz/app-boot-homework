package it.korea.app_boot.gallery.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.qlrm.executor.PageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.korea.app_boot.common.dto.PageVO;
import it.korea.app_boot.common.files.FileUtils;
import it.korea.app_boot.gallery.dto.GalleryDTO;
import it.korea.app_boot.gallery.dto.GalleryRequest;
import it.korea.app_boot.gallery.entity.GalleryEntity;
import it.korea.app_boot.gallery.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GalleryService {
     @Value("${server.file.gallery.path}")
     private String filePath;

     private final GalleryRepository galleryRepository;
     private final FileUtils fileUtils;
     private List<String> extentions = 
              Arrays.asList("jpg", "jpeg", "gif", "png", "webp", "bmp");


     public Map<String, Object> getGalleryList(Pageable pageable) throws Exception{

          Map<String, Object>  resultMap = new HashMap<>();
          Page<GalleryEntity> list =  galleryRepository.findAll(pageable);
          
          List<GalleryDTO> gallerys =
                    list.getContent().stream().map(GalleryDTO::of).toList();

          PageVO pageVO = new PageVO();
          pageVO.setData(list.getNumber(), (int)list.getTotalElements());


          resultMap.put("total", list.getTotalElements());
          resultMap.put("page", list.getNumber());
          resultMap.put("content", gallerys);
          resultMap.put("pageHTML", pageVO.pageHTML());

          return resultMap;

     }          

              
     @Transactional
     public void addGallery(GalleryRequest request) throws Exception {
          
          //파일 업로드 처리 
          Map<String, Object> fileMap = this.uploadImageFiles(request.getFile());
     
          GalleryEntity entity = new GalleryEntity();
          //갤러리 랜덤 ID 생성 
          String newNums = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10); 

          entity.setNums(newNums);
          entity.setTitle(request.getTitle());
          entity.setWriter("admin");
          entity.setFileName(fileMap.get("fileName").toString());
          entity.setStoredName(fileMap.get("storedFileName").toString());
          entity.setFilePath(filePath);
          entity.setFileThumbName(fileMap.get("thumbName").toString());

          galleryRepository.save(entity);


     }

     @Transactional
     public void updateGallery(GalleryRequest request) throws Exception {
     
          GalleryEntity entity = 
               galleryRepository.findById(request.getNums())
               .orElseThrow(() -> new RuntimeException("갤러리 없음"));
         
          //삭제 하기 위해서 기존 정보를 dto 에 넣는다 
          GalleryDTO dto = GalleryDTO.of(entity);
          
          entity.setTitle(request.getTitle());

          Map<String, Object> fileMap = null;

         //변경할 신규 파일이 존재 
         if(!request.getFile().isEmpty()) {
              //파일 업로드 
              fileMap = this.uploadImageFiles(request.getFile());
              entity.setFileName(fileMap.get("fileName").toString());
              entity.setStoredName(fileMap.get("storedFileName").toString());
              entity.setFilePath(filePath);
              entity.setFileThumbName(fileMap.get("thumbName").toString());
         }

         //업데이트 
         galleryRepository.save(entity);

          //신규파일 정보가 있다면 기존 파일은 삭제 
          if(fileMap != null){
               String originFilePath = dto.getFilePath() + dto.getStoredName();
               String thumbFilePath  = dto.getFilePath() + "thumb" + File.separator + dto.getFileThumbName();
               fileUtils.deleteFile(thumbFilePath);
               fileUtils.deleteFile(originFilePath);
          }
     }


     @Transactional
     public void deleteGallery(String targetIds) throws Exception {
          
          String[] deleteIds = targetIds.split(",");

          List<GalleryEntity> list =  galleryRepository.findByNumsIn(deleteIds);
          

          for(GalleryEntity entity : list) {
               
               galleryRepository.delete(entity);

               String originFilePath = entity.getFilePath() + entity.getStoredName();
               String thumbFilePath  = entity.getFilePath() + "thumb" + File.separator + entity.getFileThumbName();
               fileUtils.deleteFile(thumbFilePath);
               fileUtils.deleteFile(originFilePath);

          }
     }


     //파일 업로드 별도 처리
     private  Map<String, Object> uploadImageFiles(MultipartFile file) throws Exception {
          
          String fileName  = file.getOriginalFilename();
          String ext = fileName.substring(fileName.lastIndexOf(".")+1);

          if(!extentions.contains(ext)) {
               throw new RuntimeException("파일형식이 맞지 않습니다. 이미지만 가능합니다.");
          }

          Map<String, Object> fileMap = 
             fileUtils.uploadFiles(file, filePath);

          if(fileMap == null) {
            throw new RuntimeException("파일 업로드가 실패했습니다.");
          }

          String thumbFilePath = filePath + "thumb" + File.separator;
          String storedFilePath = filePath + fileMap.get("storedFileName").toString();

          File thumbFile = new File(storedFilePath);

          if(!thumbFile.exists()) {
             throw new RuntimeException("업로드파일이 존재하지 않음 ");
          }

          String thumbName = fileUtils.thumbNailFile(150, 150, thumbFile, thumbFilePath);

          fileMap.put("thumbName", thumbName);

          return fileMap;
     }
}
