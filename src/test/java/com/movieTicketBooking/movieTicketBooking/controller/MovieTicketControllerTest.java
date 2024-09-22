package com.movieTicketBooking.movieTicketBooking.controller;

import static com.movieTicketBooking.movieTicketBooking.util.TestUtil.BASE_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.service.UserService;
import com.movieTicketBooking.movieTicketBooking.util.TestUtil;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieTicketController.class)
class MovieTicketControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  UserService userService;


  @Test
  void createNewMovie() throws Exception {
    MovieDTO createdMovieDto = TestUtil.readJsonFile("CreateMovie.json", MovieDTO.class);
    String createMovieRequest = TestUtil.getJsonString(createdMovieDto);
    when(userService.createMovie(any(MovieDTO.class))).thenReturn(createdMovieDto);
    mockMvc.perform(post(BASE_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createMovieRequest))
        .andExpect(status().isCreated());
  }

  @Test
  void bookMovieTicket() throws Exception {
    TicketDTO bookTicket = TestUtil.readJsonFile("CreateTicket.json", TicketDTO.class);
    String bookTicketRequest = TestUtil.getJsonString(bookTicket);
    when(userService.bookMovieTicket(any(TicketDTO.class))).thenReturn(bookTicket);
    mockMvc.perform(post(TestUtil.buildUri("/ticket/booking"))
            .content(bookTicketRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  void getMovieDetails() throws Exception {
    MovieDTO createdMovieDto = TestUtil.readJsonFile("CreateMovie.json", MovieDTO.class);
    when(userService.getMovieById(anyLong())).thenReturn(createdMovieDto);
    mockMvc.perform(get(TestUtil.buildUri("/{movieId}", 1)))
        .andExpect(status().isOk());
  }

  @Test
  void getTicketDetails() throws Exception {
    TicketDTO bookTicket = TestUtil.readJsonFile("CreateTicket.json", TicketDTO.class);
    when(userService.getTicketById(anyLong())).thenReturn(bookTicket);
    mockMvc.perform(get(TestUtil.buildUri("/ticket/booking/{ticketId}", 1)))
        .andExpect(status().isOk());
  }

  @Test
  void getAllMovieTickets() throws Exception {
    TicketDTO bookTicket = TestUtil.readJsonFile("CreateTicket.json", TicketDTO.class);
    when(userService.getTicketsByMovieId(anyLong())).thenReturn(
        Collections.singletonList(bookTicket));
    mockMvc.perform(get(TestUtil.buildUri("/{movieId}/ticket", 1)))
        .andExpect(status().isOk());
  }

  @Test
  void createNewMovie_BadRequest() throws Exception {
    MovieDTO createdMovieDto =
        TestUtil.readJsonFile("CreateMovieBadRequest.json", MovieDTO.class);
    String createMovieRequest = TestUtil.getJsonString(createdMovieDto);
    mockMvc.perform(post(BASE_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createMovieRequest))
        .andExpect(status().isBadRequest());
  }

  @Test
  void bookMovieTicket_BadRequest() throws Exception {
    TicketDTO bookTicket =
        TestUtil.readJsonFile("CreateTicketBadRequest.json", TicketDTO.class);
    String bookTicketRequest = TestUtil.getJsonString(bookTicket);
    mockMvc.perform(post(TestUtil.buildUri("/ticket/booking"))
            .content(bookTicketRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

}