package com.juseop.hair_simulator.service;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(User user){
        userRepository.save(user);
    }

    public Optional<User> login(String userId, String userPassword){
        return userRepository.findByUserId(userId)
                .filter(u -> u.getUserPassword().equals(userPassword));
    }
}
