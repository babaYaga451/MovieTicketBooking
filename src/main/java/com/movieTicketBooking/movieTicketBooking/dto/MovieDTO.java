package com.movieTicketBooking.movieTicketBooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

  private Long id;
  @NotNull(message = "Title cannot be null")
  private String title;
  @NotNull(message = "Director cannot be null")
  private String director;
  @NotNull(message = "Language cannot be null")
  private String language;
  @NotNull(message = "year cannot be null")
  private int year;


}
