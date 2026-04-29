package com.github.valentinary.cinemabooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeatDto {
    private Long id;
    private String row;
    private String num;
    private SeatStatus status;
}
