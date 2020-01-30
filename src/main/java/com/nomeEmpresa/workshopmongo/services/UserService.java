package com.nomeEmpresa.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomeEmpresa.workshopmongo.domain.User;
import com.nomeEmpresa.workshopmongo.repository.UserRepository;
import com.nomeEmpresa.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		/*User user = repo.findOne(id);

		if (user == null) {
			throw new ObjectNotFoundException("Objeto não encontrado!");
		}
		
		return user;
		*/
		
		Optional<User> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}
}
