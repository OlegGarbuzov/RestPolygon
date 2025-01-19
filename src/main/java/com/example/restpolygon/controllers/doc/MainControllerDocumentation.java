package com.example.restpolygon.controllers.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

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
					@ApiResponse(responseCode = "200", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "404", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})
	String saveStockRecord();

	@Operation(
			summary = "Получения списка сохраненной информации у пользователя",
			description = "Этот endpoint используется для получения списка сохраненной информации у пользователя. " +
					"Пример входных данных: ticker=AAPL",
			responses =  {
					@ApiResponse(responseCode = "200", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "404", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
					@ApiResponse(responseCode = "500", content =  @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
			})
	void getStockRecord();

}
