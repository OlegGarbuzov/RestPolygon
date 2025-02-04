package com.example.restpolygon;

import com.example.restpolygon.user.dto.JwtAuthenticationResponseDto;
import com.example.restpolygon.user.dto.SignInRequestDto;
import com.example.restpolygon.user.dto.SignUpRequestDto;
import com.example.restpolygon.user.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("application-component-test.properties")
class AuthControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private final String AUTH_ROOT_URI = "/api/user";
	private final String LOGIN_URI = "/login";
	private final String REGISTER_URI = "/register";
	private final String REFRESH_TOKEN_URI = "/refreshToken";
	private final String AUTH_PREFIX = "Bearer ";
	private final String AUTH_HEADER = "Authorization";

	@Test
	@Transactional
	void authTest() throws Exception {

		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
				.username("TestUser")
				.email("TestEmail")
				.password("TestPass")
				.build();

		SignInRequestDto signInRequestDto = SignInRequestDto.builder()
				.username("TestUser")
				.password("TestPass")
				.build();

		mockMvc.perform(post(AUTH_ROOT_URI + REGISTER_URI)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(signUpRequestDto))
						.with(anonymous()))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		MvcResult result = mockMvc.perform(post(AUTH_ROOT_URI + LOGIN_URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signInRequestDto))
				.with(anonymous()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();


		String refreshToken = objectMapper.readValue(result.getResponse().getContentAsString(), JwtAuthenticationResponseDto.class).getRefreshToken();
		mockMvc.perform(get(AUTH_ROOT_URI + REFRESH_TOKEN_URI)
						.contentType(MediaType.APPLICATION_JSON)
						.header(AUTH_HEADER, AUTH_PREFIX + refreshToken)
						.with(anonymous()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

}
