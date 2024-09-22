package com.movieTicketBooking.movieTicketBooking.controller;

import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theater")
public class MovieTicketController {

  private final UserService userService;

  @Autowired
  public MovieTicketController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/movie")
  public ResponseEntity<MovieDTO> createNewMovie(@Valid @RequestBody MovieDTO movie) {
    MovieDTO createdMovie = userService.createMovie(movie);
    return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
  }

  @PostMapping("/movie/ticket/booking")
  public ResponseEntity<TicketDTO> bookMovieTicket(@Valid @RequestBody TicketDTO ticketRequest) {
    TicketDTO ticketDTO = userService.bookMovieTicket(ticketRequest);
    return new ResponseEntity<>(ticketDTO, HttpStatus.CREATED);
  }

  @GetMapping("/movie/{movieId}")
  public ResponseEntity<MovieDTO> getMovieDetails(@PathVariable Long movieId) {
    return new ResponseEntity<>(userService.getMovieById(movieId), HttpStatus.OK);
  }

  @GetMapping("/movie/ticket/booking/{ticketId}")
  public ResponseEntity<TicketDTO> getTicketDetails(@PathVariable Long ticketId) {
      return new ResponseEntity<>(userService.getTicketById(ticketId), HttpStatus.OK);
  }

  @GetMapping("/movie/{movieId}/ticket")
  public ResponseEntity<List<TicketDTO>> getAllMovieTickets(@PathVariable Long movieId) {
      return new ResponseEntity<>(userService.getTicketsByMovieId(movieId), HttpStatus.OK);
  }
}
