package com.ntd.challenge.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {

    public User(){
        records = new ArrayList<>();
    }

    @Id
    //identity will be id only for this particular column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Record> records;
    private String username;


    private String password;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void addRecords(Record record) {
        this.records.add(record);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", records=" + records +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                '}';
    }
}
