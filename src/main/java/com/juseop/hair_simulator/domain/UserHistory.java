package com.juseop.hair_simulator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "user_history")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkId;

    private String rawContent;
    private String keyword;

    @ManyToOne
    @JoinColumn(name = "user_pkId")
    private User user;
}
