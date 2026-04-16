package com.juseop.hair_simulator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //For manage of Pk
    private Long pkId;

    private String userId;
    private String userPassword;
    private String userName;
    private String userGender; // men, women -> select at join page button

    public User(String userId, String userPassword, String userName, String userGender){
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userGender = userGender;
    }



}
