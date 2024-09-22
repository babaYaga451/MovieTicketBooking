package com.movieTicketBooking.movieTicketBooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.movieTicketBooking.movieTicketBooking.constants.Constant;
import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.entity.MovieEntity;
import com.movieTicketBooking.movieTicketBooking.entity.TicketEntity;
import com.movieTicketBooking.movieTicketBooking.exception.InvalidMovieIdException;
import com.movieTicketBooking.movieTicketBooking.exception.InvalidTicketIdException;
import com.movieTicketBooking.movieTicketBooking.mapper.Mapper;
import com.movieTicketBooking.movieTicketBooking.repository.MovieRepo;
import com.movieTicketBooking.movieTicketBooking.repository.TicketRepo;
import com.movieTicketBooking.movieTicketBooking.util.TestUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

  @Mock
  MovieRepo movieRepo;

  @Mock
  TicketRepo ticketRepo;

  @Mock
  Mapper mapper;

  @InjectMocks
  UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createMovie() throws IOException {
    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    when(movieRepo.save(any())).thenReturn(movieEntity);
    when(mapper.transform(any(MovieDTO.class))).thenReturn(movieEntity);

    MovieDTO movieDTO = TestUtil.readJsonFile("MovieEntity.json", MovieDTO.class);
    when(mapper.transform(any(MovieEntity.class))).thenReturn(movieDTO);
    MovieDTO response = userService.createMovie(movieDTO);
    assertNotNull(response);
    assertEquals(response.getTitle(), movieDTO.getTitle());
    assertEquals(response.getId(), 1);
  }

  @Test
  void getMovieById() throws Exception {
    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    when(movieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(movieEntity));
    MovieDTO movieDTO = TestUtil.readJsonFile("MovieEntity.json", MovieDTO.class);
    when(mapper.transform(any(MovieEntity.class))).thenReturn(movieDTO);
    MovieDTO response = userService.getMovieById(1L);
    assertNotNull(response);
    assertEquals(response.getTitle(), movieDTO.getTitle());
    assertEquals(response.getId(), 1);
  }

  @Test
  void bookMovieTicket() throws Exception {
    TicketEntity ticketEntity = TestUtil.readJsonFile("TicketEntity.json", TicketEntity.class);
    when(ticketRepo.save(any())).thenReturn(ticketEntity);
    when(mapper.transform(any(TicketDTO.class))).thenReturn(ticketEntity);

    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    when(movieRepo.findByTitle(anyString())).thenReturn(Optional.ofNullable(movieEntity));

    TicketDTO ticketDTO = TestUtil.readJsonFile("TicketEntity.json", TicketDTO.class);
    when(mapper.transform(any(TicketEntity.class))).thenReturn(ticketDTO);
    ticketDTO.setTitle("test");

    TicketDTO response = userService.bookMovieTicket(ticketDTO);
    assertNotNull(response);
    assertEquals(response.getTitle(), ticketDTO.getTitle());
    assertEquals(response.getId(), 1);
  }

  @Test
  void getTicketById() throws Exception {
    TicketEntity ticketEntity = TestUtil.readJsonFile("TicketEntity.json", TicketEntity.class);
    when(ticketRepo.findById(anyLong())).thenReturn(Optional.ofNullable(ticketEntity));
    TicketDTO ticketDTO = TestUtil.readJsonFile("TicketEntity.json", TicketDTO.class);
    when(mapper.transform(any(TicketEntity.class))).thenReturn(ticketDTO);
    TicketDTO response = userService.getTicketById(1L);
    assertNotNull(response);
    assertEquals(response.getTitle(), ticketDTO.getTitle());
    assertEquals(response.getId(), 1);
  }

  @Test
  void getTicketsByMovieId() throws Exception {
    MovieEntity movieEntity = TestUtil.readJsonFile("MovieEntity.json", MovieEntity.class);
    when(movieRepo.findById(anyLong())).thenReturn(Optional.ofNullable(movieEntity));
    TicketDTO ticketDTO = TestUtil.readJsonFile("TicketEntity.json", TicketDTO.class);
    when(mapper.transformTicketDtoList(any())).thenReturn(Collections.singletonList(ticketDTO));
    List<TicketDTO> response =  userService.getTicketsByMovieId(1L);
    assertNotNull(response);
    assertEquals(response.get(0).getId(), 1);
  }

  @Test
  void getMovieById_BadRequest() {
    when(movieRepo.findById(anyLong())).thenReturn(Optional.empty());
    InvalidMovieIdException ex = assertThrows(InvalidMovieIdException.class,
        () -> userService.getMovieById(1L));
    assertEquals(Constant.MOVIE_NOT_FOUND_WITH_ID +1L, ex.getMessage());
  }

  @Test
  void getTicketById_BadRequest() {
    when(ticketRepo.findById(anyLong())).thenReturn(Optional.empty());
    InvalidTicketIdException ex = assertThrows(InvalidTicketIdException.class,
        () -> userService.getTicketById(1L));
    assertEquals(Constant.TICKET_NOT_FOUND_WITH_ID +1L, ex.getMessage());
  }

  @Test
  void bookMovieTicket_BadRequest() throws IOException {
    when(movieRepo.findByTitle(anyString())).thenReturn(Optional.empty());
    TicketDTO ticketDTO = TestUtil.readJsonFile("TicketEntity.json", TicketDTO.class);
    ticketDTO.setTitle("test");
    InvalidMovieIdException ex = assertThrows(InvalidMovieIdException.class,
        () -> userService.bookMovieTicket(ticketDTO));
    assertEquals(Constant.MOVIE_NOT_FOUND_WITH_TITLE +ticketDTO.getTitle(), ex.getMessage());
  }

  @Test
  void getTicketsByMovieId_BadRequest() {
    when(movieRepo.findById(anyLong())).thenReturn(Optional.empty());
    InvalidMovieIdException ex = assertThrows(InvalidMovieIdException.class,
        () -> userService.getMovieById(1L));
    assertEquals(Constant.MOVIE_NOT_FOUND_WITH_ID +1L, ex.getMessage());
  }

}