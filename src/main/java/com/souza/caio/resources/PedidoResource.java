package com.souza.caio.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.souza.caio.domain.Categoria;
import com.souza.caio.domain.Pedido;
import com.souza.caio.dto.CategoriaDTO;
import com.souza.caio.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> read(@PathVariable Integer id) {
		Pedido pedidoEncontrado = service.read(id);
		
		return ResponseEntity.ok().body(pedidoEncontrado);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> create(@Valid @RequestBody Pedido novoPedido){ //TUTORIAL: @RequestBody Converte o objeto automaticamente
		novoPedido = service.create(novoPedido);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(novoPedido.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	
}
