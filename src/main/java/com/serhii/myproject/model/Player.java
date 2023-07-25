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

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private PlayerPosition position;

    @Column(name = "dateofbirth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    private String country;

    @Column(name = "notes")
    private String importantNotes;

    @Column(name = "market_value", nullable = false)
    private Long marketValue;

    @Column(name = "playerfirstname")
    private String playerFirstName;

    @Column(name = "playerlastname")
    private String playerLastName;

    @Column(name = "jersey_number", length = 2)
    private Long jerseyNumber;

}
