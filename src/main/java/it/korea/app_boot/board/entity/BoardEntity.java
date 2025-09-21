package it.korea.app_boot.board.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import it.korea.app_boot.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="board")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brdId;

    private String title;

    private String contents;

    private String writer;

    private int readCount;

    private int  likeCount;

    @OneToMany(mappedBy="board", cascade = CascadeType.ALL, orphanRemoval= true)
    private Set<BoardFileEntity> fileList = new HashSet<>();


    public void addFiles(BoardFileEntity entity) {
        if(fileList == null) this.fileList = new HashSet<>(); 
        entity.setBoard(this);
        fileList.add(entity);
    }

}
