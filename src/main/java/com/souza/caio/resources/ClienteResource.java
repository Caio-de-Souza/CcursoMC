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

import com.souza.caio.domain.Cliente;
import com.souza.caio.domain.Cliente;
import com.souza.caio.dto.ClienteDTO;
import com.souza.caio.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> read(@PathVariable Integer id) {
		Cliente clienteEncontrado = service.read(id);
		
		return ResponseEntity.ok().body(clienteEncontrado);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> readAll() {
		List<Cliente> categoriasEncontradas = service.readAll();	
		List<ClienteDTO> categoriasDTO = 
				categoriasEncontradas
				.stream()
				.map(categoria -> new ClienteDTO(categoria))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> readAllPageable(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Cliente> categoriasEncontradas = service.readPage(page, linesPerPage, orderBy, direction);	
		Page<ClienteDTO> categoriasDTO = 
				categoriasEncontradas
				.map(categoria -> new ClienteDTO(categoria));
		return ResponseEntity.ok().body(categoriasDTO);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO categoriaAtualizarDTO, @PathVariable Integer id){
		Cliente categoriaAtualizar = service.fromDTO(categoriaAtualizarDTO);
		categoriaAtualizar = service.update(categoriaAtualizar);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
