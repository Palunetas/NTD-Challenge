package com.ntd.challenge.service;

import com.ntd.challenge.model.User;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;

@Service
public interface OperationService {

    public Object operation(String firstValue,String secondValue,String operator,User user) throws Exception;
}
