package com.serhii.myproject.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id")
    private long userId;

    @Enumerated(EnumType.STRING)
    private PlayerPosition playerPosition;

}
