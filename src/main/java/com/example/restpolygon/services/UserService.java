package com.example.restpolygon.services;

import com.example.restpolygon.entity.User;
import com.example.restpolygon.error.exception.UserAlreadyExistsException;
import com.example.restpolygon.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;

	public void save(User user) {
		repository.save(user);
	}


	public void create(User user) throws UserAlreadyExistsException {
		if (repository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistsException("User already exists");
		}

		if (repository.existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("User with this email already exists");
		}

		save(user);
	}

	public User getByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

	}

	public UserDetailsService userDetailsService() {
		return this::getByUsername;
	}

	public User getCurrentUser() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return getByUsername(username);
	}


}
