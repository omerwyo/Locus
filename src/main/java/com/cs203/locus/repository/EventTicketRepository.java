package com.cs203.locus.repository;

import com.cs203.locus.models.event.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
//import javax.transaction.Transactional;
import com.cs203.locus.models.event.EventTicket;

@Repository
public interface EventTicketRepository extends CrudRepository<EventTicket, Integer> {
    @Query("SELECT a FROM EventTicket a WHERE a.participant.id = :id")
    List<EventTicket> findByParticipantId(Integer id);
}