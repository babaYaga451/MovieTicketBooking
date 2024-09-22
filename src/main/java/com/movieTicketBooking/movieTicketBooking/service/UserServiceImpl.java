package com.movieTicketBooking.movieTicketBooking.service;

import static com.movieTicketBooking.movieTicketBooking.constants.Constant.MOVIE_NOT_FOUND_WITH_ID;
import static com.movieTicketBooking.movieTicketBooking.constants.Constant.MOVIE_NOT_FOUND_WITH_TITLE;
import static com.movieTicketBooking.movieTicketBooking.constants.Constant.TICKET_NOT_FOUND_WITH_ID;

import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.entity.MovieEntity;
import com.movieTicketBooking.movieTicketBooking.entity.TicketEntity;
import com.movieTicketBooking.movieTicketBooking.exception.InvalidMovieIdException;
import com.movieTicketBooking.movieTicketBooking.exception.InvalidTicketIdException;
import com.movieTicketBooking.movieTicketBooking.mapper.Mapper;
import com.movieTicketBooking.movieTicketBooking.repository.MovieRepo;
import com.movieTicketBooking.movieTicketBooking.repository.TicketRepo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final MovieRepo movieRepo;
  private final TicketRepo ticketRepo;
  private final Mapper mapper;

  public UserServiceImpl(MovieRepo movieRepo, TicketRepo ticketRepo, Mapper mapper) {
    this.movieRepo = movieRepo;
    this.ticketRepo = ticketRepo;
    this.mapper = mapper;
  }

  @Override
  public MovieDTO createMovie(MovieDTO movie) {
    MovieEntity movieEntity = mapper.transform(movie);
    return mapper.transform(movieRepo.save(movieEntity));
  }

  @Override
  public MovieDTO getMovieById(Long id) {
   return movieRepo.findById(id)
        .map(mapper::transform)
        .orElseThrow(() -> new InvalidMovieIdException(MOVIE_NOT_FOUND_WITH_ID + id));
  }

  @Override
  public TicketDTO bookMovieTicket(TicketDTO ticket) {
   return movieRepo.findByTitle(ticket.getTitle())
        .map(movieEntity -> {
          TicketEntity ticketEntity = mapper.transform(ticket);
          ticketEntity.setMovie(movieEntity);
          return mapper.transform(ticketRepo.save(ticketEntity));
        })
        .orElseThrow(
            () -> new InvalidMovieIdException(MOVIE_NOT_FOUND_WITH_TITLE + ticket.getTitle()));
  }

  @Override
  public TicketDTO getTicketById(Long id) {
    return ticketRepo.findById(id)
        .map(mapper::transform)
        .orElseThrow(() -> new InvalidTicketIdException(TICKET_NOT_FOUND_WITH_ID + id));
  }

  @Override
  public List<TicketDTO> getTicketsByMovieId(Long movieId) {
    return movieRepo.findById(movieId)
        .map(MovieEntity::getTickets)
        .map(mapper::transformTicketDtoList)
        .orElseThrow(() -> new InvalidMovieIdException(MOVIE_NOT_FOUND_WITH_ID + movieId));
  }
}
