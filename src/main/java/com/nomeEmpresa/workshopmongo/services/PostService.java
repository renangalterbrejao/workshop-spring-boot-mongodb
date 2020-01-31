package com.nomeEmpresa.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.nomeEmpresa.workshopmongo.domain.Post;
import com.nomeEmpresa.workshopmongo.domain.User;
import com.nomeEmpresa.workshopmongo.repository.PostRepository;
import com.nomeEmpresa.workshopmongo.repository.UserRepository;
import com.nomeEmpresa.workshopmongo.services.exceptions.DatabaseException;
import com.nomeEmpresa.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	public Post findById(String id) {

		Optional<Post> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public Post insert(Post obj) {

		repo.insert(obj);

		User user = userService.findById(obj.getAuthor().getId());

		user.getPosts().add(obj);
		userRepository.save(user);

		return obj;
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

	public Post update(Post obj) {

		Post newPost = (repo.findById(obj.getId())).get();
		updateData(newPost, obj);
		return repo.save(newPost);

	}

	private void updateData(Post newPost, Post obj) {
		newPost.setDate(obj.getDate());
		newPost.setTitle(obj.getTitle());
		newPost.setBody(obj.getBody());
		newPost.setAuthor(obj.getAuthor());
	}

	public List<Post> findByTitle(String text) {

		return repo.searchTitle(text);
	}

	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		
		return repo.fullSearch(text, minDate, maxDate);
	}
}
