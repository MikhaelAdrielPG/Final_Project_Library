package com.example.library.services;

import com.example.library.dto.UserRequest;
import com.example.library.model.User;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> userList() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> users = userRepository.findById(id);

        if (users.isPresent()) {
            return users.get();
        } else {
            return null;
        }
    }

    public User addUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        user.setType(request.getType());
        return userRepository.save(user);
    }

    public boolean updateUser(Long id, UserRequest request) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return false;
        } else {
            user.get().setName(request.getName());
            user.get().setAddress(request.getAddress());
            user.get().setGender(request.getGender());
            user.get().setPhone(request.getPhone());
            user.get().setType(request.getType());
            userRepository.save(user.get());
            return true;
        }
    }

    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return false;
        } else {
            user.get().setDeletedAt(new Date());
            userRepository.save(user.get());
            return true;
        }
    }
}
