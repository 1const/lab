package org.example.entity;

import lombok.*;

import java.sql.Date;


@Builder
@Getter
@Setter
@ToString
public class User implements Entity {

    private long id;

    private String name;

    private String surname;

    private Date dateOfBirth;
}
