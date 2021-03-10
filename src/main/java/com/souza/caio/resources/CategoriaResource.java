package com.souza.caio.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.souza.caio.domain.Categoria;
import com.souza.caio.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria categoriaEncontrada = service.find(id);	
		return ResponseEntity.ok().body(categoriaEncontrada);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria novaCategoria){ //TUTORIAL: @RequestBody Converte o objeto automaticamente
		novaCategoria = service.insert(novaCategoria);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(novaCategoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria categoriaAtualizar, @PathVariable Integer id){
		categoriaAtualizar = service.update(categoriaAtualizar);
		return ResponseEntity.noContent().build();
	}
	
}
