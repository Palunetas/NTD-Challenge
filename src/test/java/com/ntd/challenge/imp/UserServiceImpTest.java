package com.ntd.challenge.imp;

import com.ntd.challenge.model.Operation;
import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImpTest {

    static User user;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeAll
    public static void initUser(){
        //userServiceImp = new UserServiceImp();
        user = new User();
        user.setActive(true);
        user.setUsername("sat@gmail.com");
        user.setPassword("sat565");
        Record record= new Record();
        record.setBalance(500);
        //record.setOperation(operation);
        user.addRecords(record);

    }



    @Test
    public void updateUserTest() {
        user = userService.getUserByUserName("ntduser@ntdsoftware.com");
        user.setPassword("UaZZ0tM2");
        userService.updateUser(user);

        String pass = userService.getUserByUserName("ntduser@ntdsoftware.com").getPassword();
        Assertions.assertEquals(pass,"UaZZ0tM2");
    }

    @Test
    public void findAll(){
        List<User> users = userService.findAll();
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals(3,users.size());
    }

    @Test
    public void getUserByName(){
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("jonhdoe@gmail.com");
        user2.setPassword("$2a$10$5e5AK.dZG9WJ4xyZjFIZguEYHZQNZ/ikUs9liY1XtI5BlTYI0UCtu");
        user2.setActive(false);
        List<Record> records = new ArrayList<>();
        user.setRecords(records);

        User user = new User();
        user = userService.getUserByUserName("jonhdoe@gmail.com");
        Assertions.assertEquals(user.getPassword(),user2.getPassword());
        Assertions.assertEquals(user.getUsername(), user2.getUsername());
        Assertions.assertEquals(user.getId(), user2.getId());
    }

    @Test
    public void isActive() {
        //all the users from the imported file are not active
        boolean isActive;
        User user = userService.getUserByUserName("jonhdoe@gmail.com");
        user.setActive(false);
        userService.updateUser(user);
        isActive = userService.isActive("jonhdoe@gmail.com");
        Assertions.assertEquals(isActive,false);
    }
    @Test
    public void deleteRecords() {

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("jonhdoe@gmail.com");
        user2.setPassword("$2a$10$5e5AK.dZG9WJ4xyZjFIZguEYHZQNZ/ikUs9liY1XtI5BlTYI0UCtu");
        user2.setActive(false);


        Record record = new Record();
        record.setBalance(500);
        Operation operation = new Operation();
        operation.setType("+");
        operation.setCost(20);
        record.setOperation(operation);

        Record record2 = new Record();
        record2.setBalance(300);
        Operation operation2 = new Operation();
        operation2.setCost(100);
        record2.setOperation(operation2);

        Record record3 = new Record();
        record3.setBalance(200);
        Operation operation3 = new Operation();
        record3.setOperation(operation3);

        List<Record> records = new ArrayList<>();
        records.add(record);
        records.add(record2);
        records.add(record3);

        User userDb= new User();
        userDb = userService.getUserByUserName("jonhdoe@gmail.com");
        userDb.setRecords(records);

        userService.updateUser(userDb);

        userDb = userService.getUserByUserName("jonhdoe@gmail.com");

        List<Record> eraseRecords = new ArrayList<>();
        eraseRecords.add(userDb.getRecords().get(0));
        eraseRecords.add(userDb.getRecords().get(2));

        userService.deleteRecords(eraseRecords,"jonhdoe@gmail.com");
        userDb = userService.getUserByUserName("jonhdoe@gmail.com");

        Assertions.assertEquals(userDb.getRecords().size(),1);
        Assertions.assertEquals(userDb.getRecords().get(0).getBalance(),300);
        Assertions.assertEquals(userDb.getRecords().get(0).getOperation().getCost(),100);

    }

    @Test
    public void disableUser() {
        User user = userService.getUserByUserName("jonhdoe@gmail.com");
        user.setActive(true);
        userService.updateUser(user);
        userService.disableUser(user);
        user = userService.getUserByUserName("jonhdoe@gmail.com");
        Assertions.assertFalse(user.isActive());
    }


    @Test
    public void enableUser() {
        User user = userService.getUserByUserName("jonhdoe@gmail.com");
        userService.enableUser(user);
        user = userService.getUserByUserName("jonhdoe@gmail.com");
        Assertions.assertTrue(user.isActive());
    }

    @Test
    public void save(){
        User user2 = new User();
        user2.setUsername("newuser@gmail.com");
        user2.setPassword(bCryptPasswordEncoder.encode("jkkk115") );
        user2.setActive(false);
        userService.save(user2);

        User userdb= userService.getUserByUserName("newuser@gmail.com");
        Assertions.assertEquals(userdb.getUsername(),"newuser@gmail.com");
        Assertions.assertEquals(userdb.getPassword(),user2.getPassword());
    }

}
