package it.korea.app_boot.admin.dto;

import java.time.LocalDateTime;

public interface AdminUserProjection {

    String getUserId();
    String getUserName();
    String getBirth();
    String getGender();
    String getPhone();
    String getEmail();
    String getAddr();
    String getAddrDetail();
    String getUseYn();
    String getDelYn();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
    String getRoleId();
    String getRoleName();

}
