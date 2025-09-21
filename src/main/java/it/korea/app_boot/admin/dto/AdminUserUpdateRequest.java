package it.korea.app_boot.admin.dto;

import lombok.Data;

@Data
public class AdminUserUpdateRequest {
private String userId;    
    private String password;   
    private String userName;   
    private String phone;
    private String email;     
    private String addr;
    private String addrDetail;
    private String userRole;   
    private String useYn; 
}
