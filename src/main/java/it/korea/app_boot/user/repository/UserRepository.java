package it.korea.app_boot.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.korea.app_boot.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,String>{

}
