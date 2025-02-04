package com.example.restpolygon.user.service.userValidation;

import com.example.restpolygon.user.entity.User;
import com.example.restpolygon.user.exception.UserAlreadyExistsException;
import com.example.restpolygon.user.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
public class UserNameValidation implements UserValidationStrategy{

	private final UserRepository userRepository;
	@Override
	public void validation(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistsException("User already exists");
		}
	}
}
