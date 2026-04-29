package com.github.valentinary.cinemabooking.dto;

import lombok.Builder;

@Builder
public class SeatDto {
    private Long id;
    private String row;
    private String num;
    private SeatStatus status;
}
