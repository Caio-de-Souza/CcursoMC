package com.souza.caio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Categoria;
import com.souza.caio.repositories.CategoriaRepository;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id){
		Optional<Categoria> categoriaEncontrada = repository.findById(id);
		return categoriaEncontrada.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria novaCategoria) {
		novaCategoria.setId(null); //TUTORIAL: caso a primary key não esteja nula, o objeto é atualizado
		return repository.save(novaCategoria);
	}
}
