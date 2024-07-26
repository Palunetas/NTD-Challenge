package com.ntd.challenge.controller;

import com.ntd.challenge.model.OperationDetails;
import com.ntd.challenge.model.User;
import com.ntd.challenge.service.OperationService;
import com.ntd.challenge.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    UserDetailsService detailsService;

    @PostMapping("/operation")
    public ResponseEntity<Object> operation(@RequestBody OperationDetails operation) throws Exception {
        final User user = userService.getUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        Object obj = operationService.operation(operation.getOperation(),
                operation.getOperator(), user);
        if (obj.equals(false)) {
            return new ResponseEntity<Object>("Not enough founds for operation " + operation.getOperator(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
            }
        return new ResponseEntity<Object>(obj, HttpStatus.OK);
    }
}
