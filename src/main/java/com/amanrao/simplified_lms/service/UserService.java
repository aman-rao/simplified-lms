package com.amanrao.simplified_lms.service;

import com.amanrao.simplified_lms.model.User;

public interface UserService {
    void registerUser(User user);
    User findByEmail(String email);
}
