package com.ntd.challenge.controller;

import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.OperationService;
import com.ntd.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class UserController {

    private final Logger log =  Logger.getLogger(String.valueOf(getClass()));
    @Autowired
    private UserService userService;
    @Autowired
    private OperationService operationService;

    @GetMapping("/create")
    public void createUser(){
        User user1 = new User();
        user1.setActive(true);
        user1.setUsername("sat@gmail.com");
        //user1.setPassword(bCryptPasswordEncoder.encode("sat565"));
        Record record= new Record();
        record.setBalance(500);
        //record.setOperation(operation);
        user1.addRecords(record);
        userService.save(user1);
        User user2 = new User();
        user2.setUsername("ntduser@ntdsoftware.com");
        //user2.setPassword(bCryptPasswordEncoder.encode("ntdsoftware"));
        Record record2 = new Record();
        record2.setBalance(500);
        user2.addRecords(record2);
        userService.save(user2);

        User user3 = new User();
        user3.setUsername("jhondoe@industries.com");
        //user3.setPassword(bCryptPasswordEncoder.encode("doe254"));
        Record record3 = new Record();
        record3.setBalance(500);
        user3.addRecords(record3);
        userService.save(user3);

    }

    /*
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<Object> access(@RequestBody Map<String,String> login){

        User user = new User();
        ResponseEntity responseEntity = null;
        //status 400 for no found parameters
        if(login.get("username") !=null || login.get("password") != null){
            log.info("username "+login.get("username")+"pass "+login.get("password"));
            user = userService.getUserByUserName(login.get("username"));
        }else{
            responseEntity = new ResponseEntity<Object>(HttpStatus.ACCEPTED);
        }
        //check if the user from request is the same as the one from the db
        if(user.getPassword().equals(login.get("password")) && user.getUsername().equals(login.get("username"))){
            userService.enableUser(user);
            responseEntity = new ResponseEntity<Object>(HttpStatus.ACCEPTED);
        }else{
            responseEntity = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

     */

    //checking the adding operation in use
    @GetMapping("/all")
    public List<User> users(){
       return userService.findAll();
    }

    @PostMapping("/remove")
    public ResponseEntity<Object> removeRecords(List<Record> records,String username){
        try{
            userService.deleteRecords(records,username);
            return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
