package com.ntd.challenge.imp;

import com.ntd.challenge.model.Operation;
import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.OperationService;
import com.ntd.challenge.service.UserService;
import com.ntd.challenge.service.imp.OperationServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


@SpringBootTest
@AutoConfigureMockMvc
public class OperationServiceImpTest {

    @Autowired
    UserService userService;

    @Autowired
    OperationService operationService;

    @Autowired
    OperationServiceImp operationServiceImp;

    static User user;

    @BeforeAll
    public static void initUser(){

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
    public void operation() throws Exception {
        User user;
        user = userService.getUserByUserName("jonhdoe@gmail.com");
        Object object =  operationService.operation("50",
                "random",user);
        String transfor = String.valueOf(object);
        Assertions.assertEquals(operationService.operation("50",
                "square_root",user),7.0710678118654755);
        Assertions.assertEquals(transfor.length(),90);
    }


    @Test
    public void isPayable() {
        User user = new User();
        user = new User();
        user.setActive(true);
        user = userService.getUserByUserName("jonhdoe@gmail.com");
        Record record= new Record();
        record.setBalance(500);

        Operation operation = new Operation();
        operation.setCost(20);
        operation.setType("+");
        record.setBalance(500-20);
        record.setOperation(operation);
        user.addRecords(record);

        Assertions.assertEquals(operationServiceImp.isPayable(user,"+"),true);

    }

    @Test
    public void returnParsedValues() throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
        String value = "4+60-9";
        Assertions.assertEquals(engine.eval(value),55);
    }

}