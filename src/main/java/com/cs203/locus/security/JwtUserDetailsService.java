package com.cs203.locus.security;

import com.cs203.locus.models.organiser.Organiser;
import com.cs203.locus.models.participant.Participant;
import com.cs203.locus.models.user.User;
import com.cs203.locus.models.user.UserDTO;
import com.cs203.locus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    public User create(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setName(user.getName());
        // Default to ROLE_USER
        // User with ROLE_ADMIN field can be edited in database to produce an Administrator account
        newUser.setRole("ROLE_USER");
        newUser.setEmail(user.getEmail());
        newUser.setEmailVerified(false);

        Participant newParticipant = new Participant();
        newParticipant.setVaxStatus(false);
        newParticipant.setUser(newUser);
        newParticipant.setEventTicket(new ArrayList<>());

        newUser.setParticipantProfile(newParticipant);
        newParticipant.setUser(newUser);

        Organiser newOrganiser = new Organiser();
        newOrganiser.setUser(newUser);
        newOrganiser.setEvents(new ArrayList<>());

        newUser.setOrganiserProfile(newOrganiser);
        newOrganiser.setUser(newUser);

        newUser = userRepository.save(newUser);

        newUser.setPassword("");
        return newUser;
    }

    public void updatePwd(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));

        userRepository.save(user);
        user.setPassword("");
    }
}
