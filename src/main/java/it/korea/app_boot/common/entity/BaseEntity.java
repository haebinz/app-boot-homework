package it.korea.app_boot.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable{

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;
    //업데이트 될때만 갱신
    private LocalDateTime updateDate;


    //업데이트 되기 전에 실행 
    @PreUpdate
    public void preUpdate(){
        this.updateDate = LocalDateTime.now();
    }

}
