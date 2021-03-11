package com.souza.caio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Cliente;
import com.souza.caio.domain.Cliente;
import com.souza.caio.dto.ClienteDTO;
import com.souza.caio.repositories.ClienteRepository;
import com.souza.caio.services.exceptions.DataIntegrityException;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private ClienteRepository repository;
	
	public Cliente read(Integer id){
		Optional<Cliente> clienteEncontrado = repository.findById(id);
		return clienteEncontrado.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente clienteAtualizar) {
		Cliente cliente = read(clienteAtualizar.getId());
		updateData(clienteAtualizar, cliente);
		return repository.save(cliente);
	}

	private void updateData(Cliente clienteAtualizar, Cliente cliente) {
		cliente.setNome(clienteAtualizar.getNome());
		cliente.setEmail(clienteAtualizar.getEmail());
	}

	public void delete(Integer id) {
		read(id);
		try {
			repository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos vinculados");
		}
	}

	public List<Cliente> readAll() {
		return repository.findAll();
	}
	
	public Page<Cliente> readPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
}
