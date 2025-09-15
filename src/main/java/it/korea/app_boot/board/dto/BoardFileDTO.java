package it.korea.app_boot.board.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import it.korea.app_boot.board.entity.BoardFileEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BoardFileDTO {

    private int bfId;
    private String fileName;
    private String storedName;
    private String filePath;
    private Long fileSize;
    private LocalDateTime createDate;


    public static  BoardFileDTO of(BoardFileEntity entity) {
        return BoardFileDTO
              .builder()
              .bfId(entity.getBfId())
              .fileName(entity.getFileName())
              .storedName(entity.getStoredName())
              .filePath(entity.getFilePath())
              .fileSize(entity.getFileSize())
              .createDate(entity.getCreateDate())
              .build();
    }
}
