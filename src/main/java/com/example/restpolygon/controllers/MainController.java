package com.example.restpolygon.controllers;

import com.example.restpolygon.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class MainController {
	private final UserService service;

	@PostMapping("/stock")
	@Operation(summary = "Этот endpoint используется для сохранения нового запроса на акции пользователем(только этим пользователем)." +
			"Пример входных данных: ticker=AAPL")
	@PreAuthorize("hasRole('USER')")
	public String saveStockRecord() {
		return "Hello, admin!";
	}

	@GetMapping("/stock?ticker=symbol")
	@Operation(summary = "Этот endpoint используется для получения списка сохраненной информации у пользователя (только этого пользователя) по ticker акции. " +
			"Требования: Запрос должен быть аутентифицирован и содержать в теле информацию о новом запросе")
	public void getStockRecord() {
		service.getAdmin();
	}

}
