package com.projetoestudo.curso.resources;

import java.net.URI;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetoestudo.curso.domain.Categoria;
import com.projetoestudo.curso.dto.CategoriaDTO;
import com.projetoestudo.curso.services.CategoriaServices;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaServices services;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = services.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = services.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(obj.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = services.update(obj);

		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> categorias = services.findAll();
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}
}
