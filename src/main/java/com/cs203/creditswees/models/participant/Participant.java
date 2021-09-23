package com.cs203.creditswees.models.participant;

import com.cs203.creditswees.models.user.User;
import com.cs203.creditswees.models.event.EventTicket;
//import com.cs203.creditswees.models.organiser.Organiser;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer id;

    @NotBlank
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean vaxStatus;

    private String vaxAwsUrl;

    // map to User
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    // map to Ticket
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventTicket> eventTicket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isVaxStatus() {
        return vaxStatus;
    }

    public void setVaxStatus(boolean vaxStatus) {
        this.vaxStatus = vaxStatus;
    }

    public String getVaxAwsUrl() {
        return vaxAwsUrl;
    }

    public void setVaxAwsUrl(String vaxAwsUrl) {
        this.vaxAwsUrl = vaxAwsUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<EventTicket> getEventTicket() {
        return eventTicket;
    }

    public void setEventTicket(List<EventTicket> eventTicket) {
        this.eventTicket = eventTicket;
    }
}
