package it.korea.app_boot.gallery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.korea.app_boot.gallery.entity.GalleryEntity;

public interface GalleryRepository extends JpaRepository<GalleryEntity, String>{

}
