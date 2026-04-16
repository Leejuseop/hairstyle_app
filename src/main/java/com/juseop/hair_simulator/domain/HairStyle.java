package com.juseop.hair_simulator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "hairstyle")
public class HairStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkId;

    private String hairGender; //men, women
    private String hairStyle; //wave, curly
    private int hairLength; // 1~10 -> 10 == longest, 1 == shortest

    private String imagePath;
}
