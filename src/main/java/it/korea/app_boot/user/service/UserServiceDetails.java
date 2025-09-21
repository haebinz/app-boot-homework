package it.korea.app_boot.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.korea.app_boot.user.dto.UserSecureDTO;
import it.korea.app_boot.user.entity.UserEntity;
import it.korea.app_boot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceDetails implements UserDetailsService{
  
    private final UserRepository userRepository;
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = 
            userRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));

        return new UserSecureDTO(user);
    }

}
