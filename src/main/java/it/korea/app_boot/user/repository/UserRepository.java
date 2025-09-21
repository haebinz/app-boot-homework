package it.korea.app_boot.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.korea.app_boot.admin.dto.AdminUserProjection;
import it.korea.app_boot.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{


    @EntityGraph(attributePaths = {"role"})
    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findByUserIdContainingOrUserNameContaining(String searchText1, String searchText2, Pageable pageable);

    @Query(value="""
           select u.user_id,
                  u.user_name,
                  u.birth,
                  u.gender,
                  u.phone,
                  u.email,
                  u.addr,
                  u.addr_detail,
                  u.use_yn,
                  u.del_yn,
                  u.create_date,
                  u.update_date,
                  r.role_id,
                  r.role_name
           from tb_users u
             join  user_role r on u.user_role = r.role_id
            where user_id = :userId
        """, nativeQuery = true)
    Optional<AdminUserProjection> getUserById(@Param("userId") String userId);
}
