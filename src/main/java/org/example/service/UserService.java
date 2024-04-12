package org.example.service;

import org.example.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);
}
