package com.souza.caio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Categoria;
import com.souza.caio.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired //Instanciada automaticamente
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id){
		Optional<Categoria> categoriaEncontrada = repository.findById(id);
		return categoriaEncontrada.orElse(null);
	}
}
