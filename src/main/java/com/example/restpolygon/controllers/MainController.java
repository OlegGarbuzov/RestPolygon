package com.example.restpolygon.controllers;

import com.example.restpolygon.controllers.doc.MainControllerDocumentation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MainController implements MainControllerDocumentation {

	@PostMapping("/stock")
	@PreAuthorize("hasRole('USER')")
	public String saveStockRecord() {
		return "Hello, admin!";
	}

	@GetMapping("/stock?ticker=symbol")
	@PreAuthorize("hasRole('USER')")
	public void getStockRecord() {

	}

}
