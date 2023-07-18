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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private PlayerPosition position;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @NotNull
    private String country;

    @Column(name = "notes")
    private String importantNotes;

    @Column(name = "market_value")
    private long marketValue;

}
