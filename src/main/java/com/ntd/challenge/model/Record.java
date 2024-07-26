package com.ntd.challenge.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @OneToOne(cascade = CascadeType.ALL)
    private Operation operation;

    private double amount;
    private double balance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @PrePersist
    public void prePersist(){
        date = new Date();
    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
