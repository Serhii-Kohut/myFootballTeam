package com.serhii.myproject.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

}
