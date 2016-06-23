package com.theironyard.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Ben on 6/23/16.
 */
@Entity
@Table(name="bbqs")
public class Bbq {
    @Id
    @GeneratedValue
    int id;

    String name;

    double cookTime;


}
