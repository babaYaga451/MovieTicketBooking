package com.movieTicketBooking.movieTicketBooking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.movieTicketBooking.movieTicketBooking.entity.MovieEntity;

public interface MovieRepo extends CrudRepository<MovieEntity, Long> {
	Optional<MovieEntity> findByTitle(String title);
}