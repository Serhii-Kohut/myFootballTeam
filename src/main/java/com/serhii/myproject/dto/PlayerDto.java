package com.serhii.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PlayerDto {
    private long id;

    private String position;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String country;

    private String importantNotes;

    private Long marketValue;

    private String playerFirstName;

    private String playerLastName;

    private Long jerseyNumber;

    public PlayerDto() {
    }
}
