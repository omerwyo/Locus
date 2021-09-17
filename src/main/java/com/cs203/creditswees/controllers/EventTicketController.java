package com.cs203.creditswees.controllers;

import java.util.List;

import com.cs203.creditswees.models.Event.EventTicket;
import com.cs203.creditswees.service.EventTicketService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



//TODO: Remaining PUT method
@RestController
public class EventTicketController {
    private EventTicketService eventTicketService;

    public EventTicketController(EventTicketService ets){
        this.eventTicketService = ets;
    }
    
    @GetMapping("/ticket/{id}")
    public EventTicket findById(@PathVariable("id") Integer id){
        // return eventTicketService.findbyId(id);
        System.out.println("Hello2");
        EventTicket result =  new EventTicket(1,1,1);
        System.out.println(result.getEventId());
        return result;
    }

    @GetMapping("/ticket")
    public List<EventTicket> findAll(){
        System.out.println("Hello");
        return eventTicketService.findAll();
    }


    @PostMapping("/ticket")
    public EventTicket addTicket(@RequestBody EventTicket ticket){
        return eventTicketService.addTicket(ticket);
    }

    //TODO: Change to return type to void after backend structure is up and include parameters
    @DeleteMapping("/ticket/{id}")
    public @ResponseBody ResponseEntity<?> deleteWithId(@PathVariable("id") Integer id) {
        eventTicketService.deleteById(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
