package com.movieTicketBooking.movieTicketBooking.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.entity.MovieEntity;
import com.movieTicketBooking.movieTicketBooking.entity.TicketEntity;
import com.movieTicketBooking.movieTicketBooking.util.TestUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class MapperTest {

  Mapper mapper = new Mapper();

  @Test
  void transformMovieDtoToMovie() throws Exception {
    MovieDTO movieDTO = TestUtil.readJsonFile("CreateMovie.json", MovieDTO.class);
    assertNotNull(mapper.transform(movieDTO));
  }

  @Test
  void transformMovieToMovieDto() throws Exception {
    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    assertNotNull(mapper.transform(movieEntity));
  }

  @Test
  void transformTicketDtoToTicket() throws Exception {
    TicketDTO ticketDTO = TestUtil.readJsonFile("CreateTicket.json", TicketDTO.class);
    TicketEntity ticketEntity = mapper.transform(ticketDTO);
    double totalPrice = ticketDTO.getNoOfTickets() * ticketDTO.getUnitPrice();
    assertEquals(totalPrice, ticketEntity.getPrice());
  }

  @Test
  void transformTicketToTicketDto() throws Exception {
    TicketEntity ticketEntity = TestUtil.readJsonFile("TicketEntity.json", TicketEntity.class);
    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    ticketEntity.setMovie(movieEntity);
    TicketDTO ticketDTO = mapper.transform(ticketEntity);
    assertNotNull(ticketDTO);
  }

  @Test
  void transformTicketDtoList() throws Exception {
    TicketEntity ticketEntity = TestUtil.readJsonFile("TicketEntity.json", TicketEntity.class);
    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    ticketEntity.setMovie(movieEntity);
    List<TicketDTO> ticketDTOList = mapper.transformTicketDtoList(Collections.singletonList(ticketEntity));
    assertNotNull(ticketDTOList);
    assertEquals(1, ticketDTOList.size());
  }
}