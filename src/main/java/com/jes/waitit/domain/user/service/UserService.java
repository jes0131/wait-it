package com.jes.waitit.domain.user.service;

import com.jes.waitit.domain.user.entity.User;
import com.jes.waitit.domain.user.respository.UserRepository;
import com.jes.waitit.global.exception.CustomException;
import com.jes.waitit.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
