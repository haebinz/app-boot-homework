package it.korea.app_boot.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.korea.app_boot.admin.dto.AdminUserDTO;
import it.korea.app_boot.admin.dto.AdminUserProjection;
import it.korea.app_boot.admin.dto.AdminUserSearchDTO;
import it.korea.app_boot.common.dto.PageVO;
import it.korea.app_boot.user.entity.UserEntity;
import it.korea.app_boot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

   private final UserRepository userRepository;
   

    @Transactional
    public Map<String, Object> getUserList(Pageable pageable) throws Exception {
         Map<String, Object> resultMap = new HashMap<>();

         Page<UserEntity> pageList = 
                userRepository.findAll(pageable);

         
         List<AdminUserDTO> list = pageList.getContent()
                    .stream().map(AdminUserDTO::of).toList();

        PageVO pageVO = new PageVO();
        pageVO.setData(pageList.getNumber(), (int)pageList.getTotalElements());

        resultMap.put("total", pageList.getTotalElements());
        resultMap.put("content", list);
        resultMap.put("pageHTML", pageVO.pageHTML());
        resultMap.put("page", pageList.getNumber());

         return resultMap;
    }

    @Transactional
    public Map<String, Object> getUserList(Pageable pageable, AdminUserSearchDTO searchDTO) throws Exception {
         Map<String, Object> resultMap = new HashMap<>();

         Page<UserEntity> pageList =  null;

         if(StringUtils.isNotBlank(searchDTO.getSearchText())){
            pageList = userRepository
              .findByUserIdContainingOrUserNameContaining(searchDTO.getSearchText(),searchDTO.getSearchText(), pageable );
         }else {
            pageList = userRepository.findAll(pageable);
         }

            
         List<AdminUserDTO> list = pageList.getContent()
                    .stream().map(AdminUserDTO::of).toList();

        PageVO pageVO = new PageVO();
        pageVO.setData(pageList.getNumber(), (int)pageList.getTotalElements());

        resultMap.put("total", pageList.getTotalElements());
        resultMap.put("content", list);
        resultMap.put("pageHTML", pageVO.pageHTML());
        resultMap.put("page", pageList.getNumber());

         return resultMap;
    }


    @Transactional
     public  AdminUserDTO getUser(String userId) throws Exception {
         AdminUserProjection user = userRepository
                .getUserById(userId).orElseThrow(() -> new RuntimeException("사용자없음"));
      
         return AdminUserDTO.of(user);
     }

      @Transactional
    public void deleteUser(String userId) {
        UserEntity entity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자없음: " + userId));

       
     userRepository.delete(entity);

        
     entity.setDelYn("Y");
     entity.setUseYn("N");
    }

}
 