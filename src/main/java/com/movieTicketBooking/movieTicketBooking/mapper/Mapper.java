package com.movieTicketBooking.movieTicketBooking.mapper;

import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.entity.MovieEntity;
import com.movieTicketBooking.movieTicketBooking.entity.TicketEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

  public MovieDTO transform(MovieEntity movieEntity) {
    return MovieDTO.builder()
        .id(movieEntity.getId())
        .title(movieEntity.getTitle())
        .year(movieEntity.getYear())
        .director(movieEntity.getDirector())
        .language(movieEntity.getLanguage())
        .build();
  }

  public MovieEntity transform(MovieDTO movieDTO) {
    return MovieEntity.builder()
        .year(movieDTO.getYear())
        .director(movieDTO.getDirector())
        .language(movieDTO.getLanguage())
        .title(movieDTO.getTitle())
        .build();
  }

  public TicketDTO transform(TicketEntity ticketEntity) {
    return TicketDTO.builder()
        .id(ticketEntity.getId())
        .type(ticketEntity.getType())
        .noOfTickets(ticketEntity.getNoOfTickets())
        .unitPrice(ticketEntity.getUnitPrice())
        .price(ticketEntity.getPrice())
        .title(ticketEntity.getMovie().getTitle())
        .build();
  }

  public TicketEntity transform(TicketDTO ticketDTO) {
    return TicketEntity.builder()
        .type(ticketDTO.getType())
        .unitPrice(ticketDTO.getUnitPrice())
        .noOfTickets(ticketDTO.getNoOfTickets())
        .price(ticketDTO.getUnitPrice() * ticketDTO.getNoOfTickets())
        .build();
  }

  public List<TicketDTO> transformTicketDtoList(List<TicketEntity> ticketEntityList) {
    return ticketEntityList.stream()
        .map(this::transform)
        .toList();
  }

}
