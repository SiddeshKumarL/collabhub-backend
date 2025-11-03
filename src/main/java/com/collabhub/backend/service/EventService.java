package com.collabhub.backend.service;

import com.collabhub.backend.model.Event;
import com.collabhub.backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public List<Event> findAll() {
        return repo.findAll();
    }
}
