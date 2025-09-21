package it.korea.app_boot.board.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="board_files")
public class BoardFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bfId;
    
    private String fileName;
    private String storedName;
    private String filePath;
    private Long fileSize;
    @Column(updatable = false)
    private LocalDateTime createDate;

    //게시글 매핑
    @ManyToOne
    @JoinColumn(name = "brd_id")
    private BoardEntity board;

}
