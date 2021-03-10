package com.souza.caio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Pedido;
import com.souza.caio.repositories.PedidoRepository;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private PedidoRepository repository;
	
	public Pedido find(Integer id){
		Optional<Pedido> categoriaEncontrada = repository.findById(id);
		return categoriaEncontrada.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
