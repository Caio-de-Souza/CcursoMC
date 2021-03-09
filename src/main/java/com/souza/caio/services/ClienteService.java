package com.souza.caio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Cliente;
import com.souza.caio.repositories.ClienteRepository;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired //Instanciada automaticamente
	private ClienteRepository repository;
	
	public Cliente buscar(Integer id){
		Optional<Cliente> clienteEncontrado = repository.findById(id);
		return clienteEncontrado.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
