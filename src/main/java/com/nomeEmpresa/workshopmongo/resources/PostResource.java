package com.nomeEmpresa.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nomeEmpresa.workshopmongo.domain.Post;
import com.nomeEmpresa.workshopmongo.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;

	/*@GetMapping
	public ResponseEntity<List<Post>> findAll() {

		List<Post> list = service.findAll();
		//List<Post> listDto = list.stream().map(x -> new Post(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}*/

	@GetMapping(value = "/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {

		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<Post> insert(@RequestBody Post obj) {

		obj = service.insert(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Post> update(@RequestBody Post obj, @PathVariable String id) {
		
		obj = service.update(obj);

		return ResponseEntity.ok().body(obj);
	}

}
