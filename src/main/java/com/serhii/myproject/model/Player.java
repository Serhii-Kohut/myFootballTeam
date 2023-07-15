package com.serhii.myproject.model;

import jakarta.validation.constraints.NotNull;
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
    @Column(name = "position")
    private PlayerPosition playerPosition;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @NotNull
    private String country;

    @Column(name = "notes")
    private String importantNotes;

}
