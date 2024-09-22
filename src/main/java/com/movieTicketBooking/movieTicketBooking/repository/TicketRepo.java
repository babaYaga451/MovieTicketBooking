package com.movieTicketBooking.movieTicketBooking.repository;

import org.springframework.data.repository.CrudRepository;

import com.movieTicketBooking.movieTicketBooking.entity.TicketEntity;

public interface TicketRepo extends CrudRepository<TicketEntity, Long> {
}