package com.example.restpolygon.controllers.doc;

import com.example.restpolygon.client.dto.ClientResponseDto;
import com.example.restpolygon.error.exception.ClientRequestValidationException;
import com.example.restpolygon.error.exception.DataNotFoundException;
import com.example.restpolygon.error.exception.StockAlreadyExistException;
import com.example.restpolygon.feign.dto.SaveRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;

public interface MainControllerDocumentation {

	@Operation(
			summary = "Сохранение нового запроса на акции",
			description = "Этот endpoint используется для сохранения нового запроса на акции пользователем." +
					"Требования: Запрос должен быть аутентифицирован и содержать в теле информацию о новом запросе. Пример входных данных:" +
					"    {" +
					"        \"ticker\": \"AAPL\"," +
					"        \"start\": \"2022-01-01\"," +
					"        \"end\": \"2022-02-03\"" +
					"    }",
			responses =  {
					@ApiResponse(responseCode = "201"),
					@ApiResponse(responseCode = "404", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})
	ResponseEntity<Void> saveStockRecord(SaveRequestDto saveRequestDto) throws URISyntaxException, JsonProcessingException, DataNotFoundException, ClientRequestValidationException;

	@Operation(
			summary = "Получения списка сохраненной информации у пользователя",
			description = "Этот endpoint используется для получения списка сохраненной информации у пользователя. " +
					"Пример входных данных: ticker=AAPL",
			responses =  {
					@ApiResponse(responseCode = "200", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "404", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "204", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})
	ResponseEntity<ClientResponseDto> getStockRecord(@PathVariable String ticker) throws DataNotFoundException, ClientRequestValidationException;

	@Operation(
			summary = "Добавление новой записи в каталог акций",
			description = "Этот endpoint используется для добавления новой записи в каталог акций. " +
					"Пример входных данных: stock=AAPL",
			responses =  {
					@ApiResponse(responseCode = "201"),
					@ApiResponse(responseCode = "409", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})
	ResponseEntity<Void> addStockInCatalog(@RequestParam("stock") String stock) throws StockAlreadyExistException;

}
