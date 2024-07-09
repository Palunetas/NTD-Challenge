package com.ntd.challenge.controller;

import com.ntd.challenge.model.OperationDetails;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.OperationService;
import com.ntd.challenge.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class OperationController {

    private final Logger log =  Logger.getLogger(String.valueOf(getClass()));
    @Autowired
    private OperationService operationService;
    @Autowired
    private UserService userService;

    @PostMapping("operation")
    public ResponseEntity<Object> operation(@RequestBody OperationDetails operation) throws Exception {
        log.info("username "+operation.getUserName());
        if(userService.isActive(operation.getUserName())) {
            final User user = userService.getUserByUserName(operation.getUserName());
            Object obj = operationService.operation(operation.getFirstValue(),
                    operation.getSecondValue(),
                    operation.getOperator(), user);
            if (obj.equals(false)) {
                return new ResponseEntity<Object>("Not enough founds for operation " + operation.getOperator(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<Object>(obj, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No active user "+operation.getUserName(),HttpStatus.FORBIDDEN);
        }
    }
}
