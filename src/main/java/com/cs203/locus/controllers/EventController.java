package com.cs203.locus.controllers;

import com.cs203.locus.models.event.Event;
import com.cs203.locus.models.event.EventDTO;
import com.cs203.locus.service.EventService;
import com.cs203.locus.service.OrganiserService;
import com.cs203.locus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private OrganiserService organiserService;
    @Autowired
    private UserService userService;

    // List all events
    @GetMapping(value = "/list")
    public @ResponseBody
    ResponseEntity<?> getAllEvents() {
        Iterable<Event> temp = eventService.findAll();
        ArrayList<EventDTO> result = new ArrayList<>();
        for (Event event : temp) {
            EventDTO toRet = new EventDTO();
            toRet.setId(event.getId());
            toRet.setName(event.getName());
            toRet.setDescription(event.getDescription());
            toRet.setAddress(event.getAddress());
            toRet.setStartDateTime(event.getStartDateTime().toString());
            toRet.setEndDateTime(event.getEndDateTime().toString());
            toRet.setTag(event.getTag());
            toRet.setOrganiserId(event.getOrganiser().getId());
            result.add(toRet);
        }
        return ResponseEntity.ok(result);
    }

    // List all events for a Participant
    @GetMapping(value = "/listParticipantEvents/{id}")
    // TODO: need to configure such that a user can list only his own participating events
    public @ResponseBody
    ResponseEntity<?> getAllEventsByParticipant(@PathVariable Integer id) {
        List<Event> temp = eventService.findEventByParticipant(id);
        ArrayList<EventDTO> result = new ArrayList<>();

        for (Event event : temp) {
            EventDTO toRet = new EventDTO();
            toRet.setId(event.getId());
            toRet.setName(event.getName());
            toRet.setDescription(event.getDescription());
            toRet.setAddress(event.getAddress());
            toRet.setStartDateTime(event.getStartDateTime().toString());
            toRet.setEndDateTime(event.getEndDateTime().toString());
            toRet.setTag(event.getTag());
            toRet.setOrganiserId(event.getOrganiser().getId());
            result.add(toRet);
        }
        return ResponseEntity.ok(result);
    }

    // List all events of an Organiser
    @GetMapping(value = "/listOrganiserEvents/{id}")
    // TODO: need to configure such that a user can list only events he is organising
    public @ResponseBody
    ResponseEntity<?> getAllEventsByOrganiser(@PathVariable String id) {
        Integer idInt = Integer.parseInt(id);
        Iterable<Event> temp = eventService.findEventByOrganiser(idInt);
        ArrayList<EventDTO> result = new ArrayList<>();
        for (Event event : temp) {
            EventDTO toRet = new EventDTO();
            toRet.setName(event.getName());
            toRet.setDescription(event.getDescription());
            toRet.setAddress(event.getAddress());
            toRet.setStartDateTime(event.getStartDateTime().toString());
            toRet.setEndDateTime(event.getEndDateTime().toString());
            toRet.setTag(event.getTag());
            toRet.setOrganiserId(event.getOrganiser().getId());
            toRet.setId(event.getId());
            result.add(toRet);
        }
        return ResponseEntity.ok(result);
    }

    // Read an Event
    @GetMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<EventDTO> getEvent(@PathVariable Integer id) {
        if (eventService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No event with ID: " + id);
        }

        Event result = eventService.findById(id);

        EventDTO toRet = new EventDTO();
        toRet.setName(result.getName());
        toRet.setDescription(result.getDescription());
        toRet.setAddress(result.getAddress());
        toRet.setStartDateTime(result.getStartDateTime().toString());
        toRet.setEndDateTime(result.getEndDateTime().toString());
        toRet.setTag(result.getTag());
        toRet.setOrganiserId(result.getOrganiser().getId());

        return ResponseEntity.ok(toRet);
    }

    // Create an Event
    @PostMapping(path = "/new")
    public @ResponseBody
    ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO,
                                         BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int organiserId = userService.findByUsername(auth.getName()).getId();

        if (bindingResult.hasErrors()) {
            // TODO: handle various exceptions
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Event Fields");
        }

        Event newEvent = new Event();

        newEvent.setTag(eventDTO.getTag());
        newEvent.setDescription(eventDTO.getDescription());
        newEvent.setName(eventDTO.getName());
        newEvent.setAddress(eventDTO.getAddress());
        newEvent.setPrivate(eventDTO.isPrivate());
        try {
            newEvent.setStartDateTime(LocalDateTime.parse(eventDTO.getStartDateTime()));
            newEvent.setEndDateTime(LocalDateTime.parse(eventDTO.getEndDateTime()));
        } catch (DateTimeParseException e) {
            // Expects this format: 2007-12-03T10:15:30
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Date/Time");
        }

        if (organiserService.findById(organiserId) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Organiser ID");
        } else {
            newEvent.setOrganiser(organiserService.findById(organiserId));
        }

        Event created = eventService.createEvent(newEvent);
        if (created == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Event Fields");
        }

        eventDTO.setOrganiserId(created.getOrganiser().getId());
        eventDTO.setInviteCode(created.getInviteCode());
        eventDTO.setId(created.getId());
        return ResponseEntity.ok(eventDTO);
    }

    // update an event
    // TODO: need to configure such that only an organiser can update his own event
    @PutMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<EventDTO> updateEvent(@PathVariable Integer id,
                                         @Valid @RequestBody EventDTO eventDTO, BindingResult bindingResult) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (bindingResult.hasErrors()) {
            // TODO: handle various bad input
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Event Fields");
        }

        if (eventService.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No event with ID: " + id);
        }

        if (organiserService.findById(eventDTO.getOrganiserId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No Organiser with ID: " + eventDTO.getOrganiserId());
        }

        Event current = eventService.findById(id);
        current.setTag(eventDTO.getTag());
        current.setDescription(eventDTO.getDescription());
        current.setName(eventDTO.getName());
        current.setAddress(eventDTO.getAddress());
        // TODO: error handling for LocalDateTime.parse
        current.setStartDateTime(LocalDateTime.parse(eventDTO.getStartDateTime()));
        current.setEndDateTime(LocalDateTime.parse(eventDTO.getEndDateTime()));
        current.setOrganiser(organiserService.findById(eventDTO.getOrganiserId()));

        eventService.updateEvent(current);

        return ResponseEntity.ok(eventDTO);
    }

    // delete an event
    // TODO: need to configure such that only an organiser can delete his own event
    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity<EventDTO> deleteEvent(@PathVariable Integer id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (eventService.deleteEvent(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No event with ID: " + id);
        }

        Event deleted = eventService.deleteEvent(id);

        EventDTO toRet = new EventDTO();
        toRet.setName(deleted.getName());
        toRet.setDescription(deleted.getDescription());
        toRet.setAddress(deleted.getAddress());
        toRet.setStartDateTime(deleted.getStartDateTime().toString());
        toRet.setEndDateTime(deleted.getEndDateTime().toString());
        toRet.setTag(deleted.getTag());
        toRet.setOrganiserId(deleted.getOrganiser().getId());

        return ResponseEntity.ok(toRet);
    }
}
