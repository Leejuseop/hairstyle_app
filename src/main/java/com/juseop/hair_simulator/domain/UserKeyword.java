package com.juseop.hair_simulator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "user_keyword")
public class UserKeyword {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkId;

    private String category;
    private String keyWord;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public UserKeyword(String category, String keyWord, User user) {
        this.category = category;
        this.keyWord = keyWord;
        this.user = user;
    }
}
