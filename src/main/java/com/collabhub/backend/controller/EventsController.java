package com.collabhub.backend.controller;

import com.collabhub.backend.model.Event;
import com.collabhub.backend.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
// @CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true") // only if needed; else rely on WebConfig
public class EventsController {

    private final EventService service;

    public EventsController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<Event> getAll() {
        return service.findAll();
    }
}
