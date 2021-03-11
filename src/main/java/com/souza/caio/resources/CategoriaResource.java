package com.souza.caio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.souza.caio.domain.Categoria;
import com.souza.caio.dto.CategoriaDTO;
import com.souza.caio.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> read(@PathVariable Integer id) {
		Categoria categoriaEncontrada = service.read(id);	
		return ResponseEntity.ok().body(categoriaEncontrada);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> readAll() {
		List<Categoria> categoriasEncontradas = service.readAll();	
		List<CategoriaDTO> categoriasDTO = 
				categoriasEncontradas
				.stream()
				.map(categoria -> new CategoriaDTO(categoria))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> readAllPageable(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Categoria> categoriasEncontradas = service.readPage(page, linesPerPage, orderBy, direction);	
		Page<CategoriaDTO> categoriasDTO = 
				categoriasEncontradas
				.map(categoria -> new CategoriaDTO(categoria));
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody CategoriaDTO novaCategoriaDTO){ //TUTORIAL: @RequestBody Converte o objeto automaticamente
		Categoria novaCategoria = service.fromDTO(novaCategoriaDTO);
		novaCategoria = service.create(novaCategoria);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(novaCategoria.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaAtualizarDTO, @PathVariable Integer id){
		Categoria categoriaAtualizar = service.fromDTO(categoriaAtualizarDTO);
		categoriaAtualizar = service.update(categoriaAtualizar);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
