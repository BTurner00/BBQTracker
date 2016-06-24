package com.theironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Ben on 6/23/16.
 */
@Entity
@Table(name="bbqs")
public class Bbq {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    double cookTime;

    @Column(nullable = false)
    LocalDateTime startTime;

    @ManyToOne
    User user;

    public Bbq() {
    }

    public Bbq(String name, double cookTime, LocalDateTime startTime, User user) {
        this.name = name;
        this.cookTime = cookTime;
        this.startTime = startTime;
        this.user = user;
    }

    public Bbq(int id, String name, double cookTime, LocalDateTime startTime, User user) {
        this.id = id;
        this.name = name;
        this.cookTime = cookTime;
        this.startTime = startTime;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCookTime() {
        return cookTime;
    }

    public void setCookTime(double cookTime) {
        this.cookTime = cookTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
