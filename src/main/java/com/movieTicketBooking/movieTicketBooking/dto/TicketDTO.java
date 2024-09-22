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
public class TicketDTO {

	private Long id;
	@NotNull(message = "title cannot be null")
  private String title;
	@NotNull(message = "type cannot be null")
  private String type;
	@NotNull(message = "type unitPrice cannot be null")
  private double unitPrice;
	@NotNull(message = "noOfTickets cannot be null")
  private int noOfTickets;
	private double price;


}
