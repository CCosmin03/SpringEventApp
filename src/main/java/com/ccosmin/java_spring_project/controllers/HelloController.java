package com.ccosmin.java_spring_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @RequestMapping(value="hello", method={RequestMethod.GET, RequestMethod.POST})
    public String hello(@RequestParam String name, Model model){
        String greeting="Hello, "+name+"!";
        model.addAttribute("greeting",greeting);
        return "hello";
    }
    @GetMapping("hello/{name}")
    public String helloWithPathParam(@PathVariable String name, Model model){
        model.addAttribute("greeting","Hello, "+name+"!");
        return "hello";
    }
    @GetMapping("form")
    public String helloForm(){
        return "form";
    }
    @GetMapping("hello-names")
    public String helloNames(Model model){
        List<String> names=new ArrayList<>();
        names.add("Cosmin");
        names.add("Ana");
        names.add("Ioana");
        names.add("Rares");
        model.addAttribute("names",names);
        return "hello-list";
    }
}
