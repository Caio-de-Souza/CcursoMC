package com.souza.caio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.caio.domain.Cidade;
import com.souza.caio.domain.Cliente;
import com.souza.caio.domain.Endereco;
import com.souza.caio.domain.enums.TipoCliente;
import com.souza.caio.dto.ClienteDTO;
import com.souza.caio.dto.ClienteNovoDTO;
import com.souza.caio.repositories.CidadeRepository;
import com.souza.caio.repositories.ClienteRepository;
import com.souza.caio.repositories.EnderecoRepository;
import com.souza.caio.services.exceptions.DataIntegrityException;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private ClienteRepository repository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private CidadeRepository cidadeRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public Cliente create(Cliente novoCliente) {
		novoCliente.setId(null); //TUTORIAL: caso a primary key não esteja nula, o objeto é atualizado
		novoCliente = repository.save(novoCliente);
		enderecoRepository.saveAll(novoCliente.getEnderecos());
		return novoCliente;
	}
	
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
			throw new DataIntegrityException("Não é possível excluir o cliente pois possui pedidos vinculados");
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
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNovoDTO clienteDTO) {
		Cliente novoCliente = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipo()), passwordEncoder.encode(clienteDTO.getSenha()));
		Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
		Endereco novoEndereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), novoCliente, cidade);
		
		novoCliente.getEnderecos().add(novoEndereco);
		novoCliente.getTelefones().add(clienteDTO.getTelefone1());
		
		if(clienteDTO.getTelefone2() != null) {
			novoCliente.getTelefones().add(clienteDTO.getTelefone2());
		}
		
		if(clienteDTO.getTelefone3() != null) {
			novoCliente.getTelefones().add(clienteDTO.getTelefone3());
		}
		
		return novoCliente;
	}
}
