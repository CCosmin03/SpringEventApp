package com.ccosmin.java_spring_project.controllers;

import com.ccosmin.java_spring_project.data.EventCategoryRepository;
import com.ccosmin.java_spring_project.data.EventData;
import com.ccosmin.java_spring_project.data.EventRepository;
import com.ccosmin.java_spring_project.data.TagRepository;
import com.ccosmin.java_spring_project.models.Event;
import com.ccosmin.java_spring_project.models.EventCategory;
import com.ccosmin.java_spring_project.models.EventType;
import com.ccosmin.java_spring_project.models.Tag;
import com.ccosmin.java_spring_project.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.validation.Valid;

import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String displayAllEvents(@RequestParam(required = false) Integer categoryId, Model model){
        if (categoryId==null){
            model.addAttribute("title","All Events");
            model.addAttribute("events", eventRepository.findAll());
        }else{
           Optional<EventCategory> result= eventCategoryRepository.findById(categoryId);
           if (result.isEmpty()){
               model.addAttribute("title","Invalid Category ID: "+categoryId);
           }else{
               EventCategory category=result.get();
               model.addAttribute("title","Events in category: "+category.getName());
               model.addAttribute("events",category.getEvents());
           }
        }

        return "events/index";
    }
    @GetMapping("create")
    public String displayCreateEventForm(Model model){
        model.addAttribute("title","Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories",eventCategoryRepository.findAll());
        return "events/create";
    }
    @PostMapping("create")
    public String processCreateEventsForm(@ModelAttribute @Valid Event newEvent, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute("title","Create Event");
           // model.addAttribute("errorMsg", "Bad Data!");
            return "events/create";
        }
         eventRepository.save(newEvent);
         return "redirect:/events";
    }
    @GetMapping("delete")
    public String displayDeleteForm(Model model){
        model.addAttribute("title","Delete Events");
        model.addAttribute("events",eventRepository.findAll());
        return "events/delete";
    }
    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) int[] eventIds){
        if (eventIds!=null){
            for (int id: eventIds){
                eventRepository.deleteById(id);
            }
        }
        return "redirect:/events";
    }
    @GetMapping("detail")
    public String displayEventDetails(@RequestParam Integer eventId, Model model){
        Optional<Event> result=eventRepository.findById(eventId);
        if (result.isEmpty()){
            model.addAttribute("title","Invalid event Id: "+eventId);
        }else{
            Event event=result.get();
            model.addAttribute("title",event.getName()+"Details");
            model.addAttribute("event",event);
            model.addAttribute("tags",event.getTags());
        }
        return "events/detail";
    }
    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model){
        Optional<Event> result=eventRepository.findById(eventId);
        Event event =result.get();
        model.addAttribute("title", "Add Tag to: "+ event.getName());
        model.addAttribute("tags",tagRepository.findAll());
        EventTagDTO eventTag=new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag",eventTag);
        return "events/add-tag.html";
    }
    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag, Errors errors, Model model){
        if (!errors.hasErrors()){
            Event event=eventTag.getEvent();
            Tag tag=eventTag.getTag();
            if (!event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:/events/detail?eventId="+event.getId();
        }
        return "redirect:/add-tag";
    }
}
