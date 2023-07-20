package com.serhii.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PlayerDto {
    private long id;

    private String position;

    private LocalDate dateOfBirth;

    private String country;

    private String importantNotes;

    private long marketValue;

    private String playerFirstName;

    private String playerLastName;

    public PlayerDto() {
    }
}
