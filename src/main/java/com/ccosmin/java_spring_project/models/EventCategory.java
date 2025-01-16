package com.ccosmin.java_spring_project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class EventCategory extends AbstractEntity{
    @Size(min=3, message="Name must be at least 3 characters")
    private String name;
    @OneToMany(mappedBy = "eventCategory")
    private final List<Event> events=new ArrayList<>();
    public EventCategory(){
    }
    public EventCategory(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EventCategory{" +
                "name='" + name + '\'' +
                '}';
    }

    public List<Event> getEvents() {
        return events;
    }
}
