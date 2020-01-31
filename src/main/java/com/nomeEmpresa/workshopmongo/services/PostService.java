package com.nomeEmpresa.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.nomeEmpresa.workshopmongo.domain.Post;
import com.nomeEmpresa.workshopmongo.repository.PostRepository;
import com.nomeEmpresa.workshopmongo.services.exceptions.DatabaseException;
import com.nomeEmpresa.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	/*public List<Post> findAll() {
		return repo.findAll();
	}*/

	public Post findById(String id) {

		Optional<Post> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public Post insert(Post obj) {
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

	/*public Post fromDTO(PostDTO objDto) {
		return new Post(objDto.getId(), objDto.getName(), objDto.getEmail());

	}*/
}