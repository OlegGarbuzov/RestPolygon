package com.example.restpolygon;

import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.example.restpolygon.feign.entity.TickerCatalog;
import com.example.restpolygon.feign.repo.TickerCatalogRepository;
import com.example.restpolygon.user.entity.User;
import com.example.restpolygon.user.enums.Role;
import com.example.restpolygon.user.repo.UserRepository;
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
import java.util.HashSet;

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
	private TickerCatalogRepository tickerCatalogRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private final String ROOT_URI = "/api/v1/stock";

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

	@Test
	@Transactional
	void saveAndGetStockRecordTest() throws Exception {

		UserDetails user = userRepository.save(User.builder()
				.email("testE")
				.password("testP")
				.role(Role.ROLE_USER)
				.username("testU")
				.tickers(new HashSet<>())
				.build());

		SaveRequestDto saveRequestDto = SaveRequestDto.builder()
				.ticker("AAPL")
				.start(LocalDate.of(2025, 1,13))
				.end(LocalDate.of(2025, 1,14))
				.build();

		tickerCatalogRepository.save(TickerCatalog.builder()
				.symbol("AAPL")
				.build());

		mockMvc.perform(post("/api/v1/stock")
						.with(user(user))
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(saveRequestDto)))
				.andExpect(status().isCreated());

		mockMvc.perform(post("/api/v1/stock")
						.with(anonymous()))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(get(ROOT_URI + "?ticker={ticker}", "AAPL")
						.with(user(user)))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(get(ROOT_URI + "?ticker={ticker}", "UnavailableTicker")
						.with(user(user)))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get(ROOT_URI + "?ticker={ticker}", "AAPL")
						.with(anonymous()))
				.andExpect((status().isUnauthorized()));

	}

}
