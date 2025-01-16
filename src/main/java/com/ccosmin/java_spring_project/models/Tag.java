package com.ccosmin.java_spring_project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity{
    @Size(min=1, max=25)
    @NotBlank
    private String name;
    public Tag(){}
    @ManyToMany(mappedBy = "tags")
    private final List<Event> events=new ArrayList<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName(){return  '#'+name+" ";}

    public Tag(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }
}
