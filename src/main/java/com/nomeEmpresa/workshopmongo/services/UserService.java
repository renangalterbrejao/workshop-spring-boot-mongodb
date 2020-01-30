package com.nomeEmpresa.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.nomeEmpresa.workshopmongo.domain.User;
import com.nomeEmpresa.workshopmongo.dto.UserDTO;
import com.nomeEmpresa.workshopmongo.repository.UserRepository;
import com.nomeEmpresa.workshopmongo.services.exceptions.DatabaseException;
import com.nomeEmpresa.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		/*
		 * User user = repo.findOne(id);
		 * 
		 * if (user == null) { throw new
		 * ObjectNotFoundException("Objeto não encontrado!"); }
		 * 
		 * return user;
		 */

		Optional<User> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public User insert(User obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());

	}
}
