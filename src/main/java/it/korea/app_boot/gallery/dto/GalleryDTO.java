package it.korea.app_boot.gallery.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import lombok.Builder;
import it.korea.app_boot.gallery.entity.GalleryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GalleryDTO {

    private String nums;
    private String title;
    private String writer;
    private String fileName;
    private String storedName;
    private String filePath;
    private String fileThumbName;
    private LocalDateTime lastModifiedDate;

    public static GalleryDTO of(GalleryEntity entity) {

        LocalDateTime lastModifiedDate =
                entity.getUpdateDate() == null ? entity.getCreateDate() : entity.getUpdateDate();

        return GalleryDTO
            .builder()
            .nums(entity.getNums())
            .title(entity.getTitle())
            .writer(entity.getWriter())
            .fileName(entity.getFileName())
            .storedName(entity.getStoredName())
            .filePath(entity.getFilePath())
            .fileThumbName(entity.getFileThumbName())
            .lastModifiedDate(lastModifiedDate)
            .build();
    }

    public static GalleryEntity to(GalleryDTO dto) {
        
        GalleryEntity entity = new GalleryEntity();
        entity.setNums(dto.getNums());
        entity.setWriter(dto.getWriter());
        entity.setFileName(dto.getFileName());
        entity.setStoredName(dto.getStoredName());
        entity.setFilePath(dto.getFilePath());
        entity.setFileThumbName(dto.getFileThumbName());

        return entity;
    }
}
