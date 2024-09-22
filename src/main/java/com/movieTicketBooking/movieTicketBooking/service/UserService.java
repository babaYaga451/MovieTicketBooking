package com.movieTicketBooking.movieTicketBooking.service;

import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import java.util.List;

public interface UserService {
 
	  MovieDTO createMovie(MovieDTO movie);
    TicketDTO bookMovieTicket(TicketDTO ticket);
    MovieDTO getMovieById(Long movieId);
    TicketDTO getTicketById(Long ticketId);
    List<TicketDTO> getTicketsByMovieId(Long movieId);
}
