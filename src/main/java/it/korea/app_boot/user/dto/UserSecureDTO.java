package it.korea.app_boot.user.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import it.korea.app_boot.user.entity.UserEntity;
import it.korea.app_boot.user.entity.UserRoleEntity;
import lombok.Getter;

@Getter
public class UserSecureDTO extends User{

    //스프링 보안이 권한 값을 받을 때 prefix를 ROLE_ 을 붙여야 인식한다.
    private static final String ROLE_PREFIX = "ROLE_";

    private String userId;
    private String userName;
    
    public UserSecureDTO(UserEntity entity) {
        super(entity.getUserId(), entity.getPasswd(), makeGrantedAuthorities(entity.getRole()));

        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
    }

    //생성자에서 사용해야하기 때문에 static 으로 처리 
    private static  List<GrantedAuthority> makeGrantedAuthorities(UserRoleEntity entity) {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + entity.getRoleId()));
        return list;
    }
}
