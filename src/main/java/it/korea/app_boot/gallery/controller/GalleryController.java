package it.korea.app_boot.gallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
@RequestMapping("/gal")
public class GalleryController {


    @GetMapping("")
    public ModelAndView  getView() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/gal/galleryList");
        return view;
    }
    

}
