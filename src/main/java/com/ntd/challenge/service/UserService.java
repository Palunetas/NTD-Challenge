package com.ntd.challenge.service;

import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {

    void save(User user);

    void disableUser(User user);

    void enableUser(User user);
    User getUserById(Long id);
    User getUserByUserName(String name);

    List<User> findAll();

    void updateUser(User user);

    boolean isActive(String name);

    void deleteRecords(List<Record> records,String username);
}
