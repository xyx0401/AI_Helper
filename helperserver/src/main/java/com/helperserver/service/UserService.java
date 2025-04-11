package com.helperserver.service;

import com.helperserver.bean.User;
import java.util.List;

public interface
UserService {
    User getUserById(String id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String id);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
}