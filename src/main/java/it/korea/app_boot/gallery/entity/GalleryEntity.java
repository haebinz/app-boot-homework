package it.korea.app_boot.gallery.entity;

import it.korea.app_boot.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name ="gallery")
public class GalleryEntity extends BaseEntity{

    @Id
    private String nums;

    private String title;

    private String writer;

    private String fileName;

    private String storedName;

    private String filePath;

    private String fileThumbName;


}
