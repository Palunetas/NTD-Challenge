package com.ntd.challenge.imp;

import com.ntd.challenge.model.Operation;
import com.ntd.challenge.model.Record;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.OperationService;
import com.ntd.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.logging.Logger;


@Repository
public class OperationServiceImp implements OperationService {
    private final Logger log =  Logger.getLogger(String.valueOf(getClass()));
    @Autowired
    private UserService userService;

    @Value("${url.random}")
    private String url;

    @Override
    public Object operation(String firstValue, String secondValue, String operator,User user) throws Exception {
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

    private Object returnParsedValues(String firstValue,String secondValue,String operator) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
        System.out.println("Operations "+firstValue+operator+secondValue);
        return  engine.eval(firstValue +operator+secondValue);
    }


}
