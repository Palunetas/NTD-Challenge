package com.ntd.challenge.imp;

import com.ntd.challenge.model.Operation;
import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationServiceImpTest {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserService userService;

    @Autowired
    UserServiceImp userServiceImp;

    static OperationServiceImp operationServiceImp;

    static User user;

    @BeforeAll
    public static void initUser(){

        operationServiceImp= new OperationServiceImp();
        user = new User();
        user.setActive(true);
        user.setUsername("sat@gmail.com");
        user.setPassword("sat565");
        Record record= new Record();
        record.setBalance(500);
        //record.setOperation(operation);
        user.addRecords(record);

    }

    /*
    @Test
    public void operation(){
        /*
        if(isPayable(user,operator)) {
            if (operator.equals("square_root")) {
                return Math.sqrt(Double.parseDouble(firstValue));
            } else if (operator.equals("random")) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> call = restTemplate.getForEntity(url, String.class);
                return call.getBody();
            } else {
                return returnParsedValues(firstValue, secondValue, operator);
            }
        }else {
            return false;
        }

    }


     */



    @Test
    public void isPayable() {
        User user = new User();
        user = new User();
        user.setActive(true);
        user.setUsername("jonhdoe@gmail.com");
        user.setPassword("$2a$10$5e5AK.dZG9WJ4xyZjFIZguEYHZQNZ/ikUs9liY1XtI5BlTYI0UCtu");
        Record record= new Record();
        record.setBalance(500);

        Operation operation = new Operation();
        operation.setCost(20);
        operation.setType("+");
        record.setBalance(500-20);
        record.setOperation(operation);
        user.addRecords(record);


        when(userService.getUserByUserName(Mockito.anyString())).thenReturn(user);

        Assertions.assertTrue(operationServiceImp.isPayable(user, "+"));

    }

/*
    public boolean isPayable(User user, String operator) {
        User userDb = userService.getUserByUserName(user.getUsername());
        log.info("Records "+userDb.toString());
        double balance=0;
        try {
            balance = userDb.getRecords().get(userDb.getRecords().size() - 1).getBalance();
        }catch (Exception e){
            balance=500;
        }
        double pay = 0;
        double cost = 0;
        if(operator.equals("/")){
            pay = 30;
            cost = 30;
        }
        if(operator.equals("+")){
            pay = 20;
            cost = 20;
        }else if(operator.equals("-")){
            pay = 40;
            cost = 40;
        }else if(operator.equals("*")){
            pay = 60;
            cost = 60;
        }else if(operator.equals("square_root")){
            pay = 100;
            cost = 100;
        }else if(operator.equals("random")){
            pay = 200;
            cost = 200;
        }
        if(balance - pay < 0){
            return false;
        }else{
            Operation operation = new Operation();
            operation.setType(operator);
            operation.setCost(cost);

            Record record = new Record();
            record.setOperation(operation);
            record.setBalance(balance - pay);
            user.addRecords(record);
            userService.updateUser(user);
            return true;
        }
    }
*/


}
