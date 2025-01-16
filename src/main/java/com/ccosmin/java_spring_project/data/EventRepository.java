package com.ccosmin.java_spring_project.data;

import com.ccosmin.java_spring_project.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}
