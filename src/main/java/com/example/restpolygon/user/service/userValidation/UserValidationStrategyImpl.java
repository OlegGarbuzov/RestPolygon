package com.example.restpolygon.user.service.userValidation;

import com.example.restpolygon.user.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class UserValidationStrategyImpl {

	private final UserNameValidation userNameValidation;
	private final UserEmailValidation userEmailValidation;

	public void validation(User user) {
		List<UserValidationStrategy> validationList = new ArrayList<>();
		validationList.add(userNameValidation);
		validationList.add(userEmailValidation);

		validationList.forEach(t -> t.validation(user));
	}
}
