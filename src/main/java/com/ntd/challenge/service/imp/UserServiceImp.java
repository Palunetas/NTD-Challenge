package com.ntd.challenge.service.imp;

import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserServiceImp implements UserService {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void disableUser(User user) {
        user.setActive(false);
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void enableUser(User user) {
        user.setActive(true);
        entityManager.merge(user);
    }

    @Override
    public User getUserByUserName(String name) {
        User user = new User();
        //Query was implemented instead of CrudRepository
        //this query is to get by the username, should be by id, but this is just to show
        //a little bit more complex
        Query query= entityManager.createQuery("SELECT u from User u WHERE u.username = ?1",User.class);
        user= (User) query.setParameter(1,name).getSingleResult();
        return user;
    }

    @Override
    public User findByUserName(String name) {
        User user = new User();
        //Query was implemented instead of CrudRepository
        //this query is to get by the username, should be by id, but this is just to show
        //a little bit more complex
        Query query= entityManager.createQuery("SELECT u from User u WHERE u.username = ?1",User.class);
        user= (User) query.setParameter(1,name).getSingleResult();
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        return users= entityManager.createQuery("SELECT c FROM User c", User.class).getResultList();
    }

    @Override
    @Transactional
    //merge will be used to update
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public boolean isActive(String name) {
        User user = getUserByUserName(name);
        return user.isActive();
    }

    @Override
    @Transactional
    public void deleteRecords(List<Record> records,String username) {
        User user;
        user = getUserByUserName(username);
        List<Record> userRecords= new ArrayList<>();
        userRecords = user.getRecords();
        List<Record> finalUserRecords = userRecords;
        records.forEach(e->{
            finalUserRecords.removeIf(a->a.getId()==e.getId());
        });

        user.setRecords(userRecords);
        updateUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= null;
        try {
            user = getUserByUserName(username);
        }catch (Exception e){
            e.getMessage();
        }
        if(user != null) {
            List<GrantedAuthority> authorityList = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(username,
                    user.getPassword(),
                    user.isActive(),
                    true,
                    true,
                    true,
                    authorityList
            );
        }else {
            throw new UsernameNotFoundException(String.format("usario no existe en el sistema ",username));
        }
    }

}

