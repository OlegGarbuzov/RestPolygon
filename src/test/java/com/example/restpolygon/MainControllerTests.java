package com.example.restpolygon;

import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.example.restpolygon.controllers.MainController.ADD_TICKER_URI;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("application-component-test.properties")
class MainControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private final String ROOT_URI = "/api/v1/stock";

	@Test
	void getStockRecordTest() throws Exception {

		UserDetails user = userRepository.findByUsername("Oleg6").get();

		mockMvc.perform(get(ROOT_URI + "?ticker={ticker}", "AAPL")
						.with(user(user)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(get(ROOT_URI + "?ticker={ticker}", "AAPL")
						.with(anonymous()))
				.andExpect((status().isUnauthorized()));

	}

	@Test
	@Transactional
	void saveStockRecordTest() throws Exception {

		UserDetails user = userRepository.findByUsername("Oleg6").get();

		SaveRequestDto saveRequestDto = SaveRequestDto.builder()
				.ticker("AAPL")
				.start(LocalDate.of(2025, 1,13))
				.end(LocalDate.of(2025, 1,14))
				.build();

		mockMvc.perform(post("/api/v1/stock")
						.with(user(user))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(saveRequestDto)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/api/v1/stock")
						.with(anonymous()))
				.andExpect(status().isUnauthorized());

	}

	@Test
	@Transactional
	@WithMockUser(roles = "TEST")
	void addStockInCatalogTest() throws Exception {

		mockMvc.perform(post(ROOT_URI + ADD_TICKER_URI + "?ticker={ticker}", "TestTicker"))
				.andExpect(status().isCreated());

		mockMvc.perform(post(ROOT_URI + ADD_TICKER_URI + "?ticker={ticker}", "TestTicker")
				.with(anonymous()))
				.andExpect(status().isUnauthorized());

	}


}
