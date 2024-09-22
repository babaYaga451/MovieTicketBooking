package com.movieTicketBooking.movieTicketBooking;

import static com.movieTicketBooking.movieTicketBooking.util.TestUtil.BASE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieTicketBooking.movieTicketBooking.dto.MovieDTO;
import com.movieTicketBooking.movieTicketBooking.dto.TicketDTO;
import com.movieTicketBooking.movieTicketBooking.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MovieTicketBookingApplicationTests {

  @Autowired
  MockMvc mockMvc;

  public static final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void createNewMovie() throws Exception {
    MovieDTO createdMovieDto = TestUtil.readJsonFile("CreateMovie.json", MovieDTO.class);
    String createMovieRequest = TestUtil.getJsonString(createdMovieDto);
    String response = mockMvc.perform(post(BASE_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(createMovieRequest))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();
    MovieDTO movieDTO = objectMapper.readValue(response, MovieDTO.class);
    assertEquals(1, movieDTO.getId());
  }

  @Test
  void createMovieTicket() throws Exception {
    TicketDTO bookTicket = TestUtil.readJsonFile("CreateTicket.json", TicketDTO.class);
    String bookTicketRequest = TestUtil.getJsonString(bookTicket);
    var response = mockMvc.perform(post(TestUtil.buildUri("/ticket/booking"))
            .content(bookTicketRequest)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();
    TicketDTO ticketDTO = objectMapper.readValue(response, TicketDTO.class);
    assertEquals(1, ticketDTO.getId());
  }

	@Test
	void getTicketDetails() throws Exception {
		var response = mockMvc.perform(get(TestUtil.buildUri("/ticket/booking/{ticketId}", 1)))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		TicketDTO ticketDTO = objectMapper.readValue(response, TicketDTO.class);
		assertEquals(1, ticketDTO.getId());
		assertEquals("Avengers", ticketDTO.getTitle());
	}

  @Test
  void getMovieDetails() throws Exception {
    var response = mockMvc.perform(get(TestUtil.buildUri("/{movieId}", 1)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
    MovieDTO movieDTO = objectMapper.readValue(response, MovieDTO.class);
    assertEquals(1, movieDTO.getId());
    assertEquals("Avengers", movieDTO.getTitle());
  }

  @Test
  void getAllMovieTickets() throws Exception {
    var response = mockMvc.perform(get(TestUtil.buildUri("/{movieId}/ticket", 1)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
    TicketDTO[] ticketDTOList = objectMapper.readValue(response, TicketDTO[].class);
    assertEquals(1, ticketDTOList.length);
    assertEquals("Avengers", ticketDTOList[0].getTitle());
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
