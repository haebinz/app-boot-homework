package it.korea.app_boot.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.korea.app_boot.admin.dto.AdminUserSearchDTO;
import it.korea.app_boot.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdminUserAPIController {

    private final AdminUserService userService;


    @GetMapping("/admin/user")
    public ResponseEntity<Map<String, Object>> getUserList(
        @PageableDefault(page = 0, size = 10, sort = "createDate", direction = Direction.DESC) Pageable pageable,
        AdminUserSearchDTO searchDTO) throws Exception {
       
        Map<String, Object> resultMap = userService.getUserList(pageable, searchDTO);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String userId) {
        Map<String, Object> resp = new HashMap<>();
        try {
            userService.deleteUser(userId); 
            resp.put("resultCode", 200);
            resp.put("resultMsg", "OK");
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            resp.put("resultCode", 500);
            resp.put("resultMsg", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }
}
