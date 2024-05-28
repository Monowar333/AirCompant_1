package org.example.central.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.api.model.Flight;
import org.example.central.entity.FlightEntity;
import org.example.central.entity.UserEntity;
import org.example.central.repository.FlightRepository;
import org.example.central.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String ANONYMOUS_USER = "anonymousUser";
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity loadUserByName(String name) {
       return userRepository.findByUserName(name);
    }

    public UserEntity loadCurrentUser() {
        if (ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())) {
            return null;
        }
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loadUserByName(currentUser.getUsername());

    }
}
