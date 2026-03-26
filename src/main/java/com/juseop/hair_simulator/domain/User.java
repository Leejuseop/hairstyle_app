package com.juseop.hair_simulator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity //For Jpa
@Getter
@Setter
@Table(name = "users") //Avoiding conflict at sql
public class User {
    @Id //For Jpa
    @GeneratedValue(strategy = GenerationType.IDENTITY) //For manage of Pk
    private Long pkId; //Pk Id

    private String userId;
    private String userPassword;
    private String userName;

    //Constructor
    public User(String userId, String userPassword, String userName){
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
    }

}
