package com.example.library.services;

import com.example.library.dto.request.UserRequest;
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

    // Fungsi untuk menampilkan seluruh data user(member/employee)
    public List<User> userList() {
        return userRepository.findAll();
    }

    // Fungsi untuk menampikan data user(member/employee) berdasarkan id
    public User getUserById(Long id) {
        Optional<User> users = userRepository.findById(id);

        if (users.isPresent()) {
            return users.get();
        } else {
            return null;
        }
    }

    // Fungsi untuk menambah data user(member/employee)
    public User addUser(UserRequest request) {
        if (isEmptyOrSpace(request.getName()) || isEmptyOrSpace(request.getAddress())
                || !isValidGender(request.getGender()) || !isValidPhoneNumber(request.getPhone())
                || !isValidUserType(request.getType()) || containsSpecialCharacter(request.getName())) {
            return null;
        }

        User user = new User();
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        user.setType(request.getType());

        return userRepository.save(user);
    }

    // Fungsi untuk mengupdate data user(member/employee)
    public boolean updateUser(Long id, UserRequest request) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return false;
        } else {
            if (isEmptyOrSpace(request.getName()) || isEmptyOrSpace(request.getAddress())
                    || !isValidGender(request.getGender()) || !isValidPhoneNumber(request.getPhone())
                    || !isValidUserType(request.getType()) || containsSpecialCharacter(request.getName())) {
                return false;
            }

            user.get().setName(request.getName());
            user.get().setAddress(request.getAddress());
            user.get().setGender(request.getGender());
            user.get().setPhone(request.getPhone());
            user.get().setType(request.getType());
            userRepository.save(user.get());
            return true;
        }
    }


    // Fungsi untuk menghapus data user(member/employee)
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

    private boolean isEmptyOrSpace(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10,13}$");
    }

    private boolean containsSpecialCharacter(String str) {
        return !str.matches("^[a-zA-Z0-9\\s]*$");
    }

    private boolean isValidUserType(String type) {
        return "staff".equalsIgnoreCase(type) || "member".equalsIgnoreCase(type);
    }

    private boolean isValidGender(String gender) {
        String lowerCaseGender = gender.toLowerCase();
        return "male".equals(lowerCaseGender) || "female".equals(lowerCaseGender) || "other".equals(lowerCaseGender);
    }
}
