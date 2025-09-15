package it.korea.app_boot.gallery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.korea.app_boot.gallery.dto.GalleryRequest;
import it.korea.app_boot.gallery.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GalleryAPIController {

    private final GalleryService galleryService;


     @PostMapping("/gal")
    public ResponseEntity<Map<String, Object>> writeBoard(@Valid @ModelAttribute GalleryRequest request) throws Exception {      
        
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try{

            galleryService.addGallery(request);
            resultMap.put("resultCode", 200);
            resultMap.put("resultMessage", "OK");
       
        }catch(Exception e) {
            //예외 발생 시 공통 모듈을 실행하기 위해 예외를 던진다 
            throw new Exception(e.getMessage() == null ? " 이미지 등록 실패" : e.getMessage());
        }

        return new ResponseEntity<>(resultMap, status);
    }

}
