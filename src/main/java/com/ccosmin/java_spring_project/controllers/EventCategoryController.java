package com.ccosmin.java_spring_project.controllers;

import com.ccosmin.java_spring_project.data.EventCategoryRepository;
import com.ccosmin.java_spring_project.models.EventCategory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("events/categories")
public class EventCategoryController {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String displayAllCategories(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "categories/index";
    }

    @GetMapping("create")
    public String displayCreateCategoryForm(Model model) {
        model.addAttribute("title", "Create Category");
        model.addAttribute(new EventCategory());
        return "categories/create";
    }

    @PostMapping("create")
    public String processCreateCategoryForm(@ModelAttribute @Valid EventCategory newCategory, Errors errors) {
        if (errors.hasErrors()) {
            return "categories/create";
        }
        eventCategoryRepository.save(newCategory);
        return "redirect:/events/categories";
    }
}
