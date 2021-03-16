package com.souza.caio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.souza.caio.domain.Categoria;
import com.souza.caio.domain.Produto;
import com.souza.caio.repositories.CategoriaRepository;
import com.souza.caio.repositories.ProdutoRepository;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private ProdutoRepository repository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private CategoriaRepository categoriaRepository;
	
	public Produto read(Integer id){
		Optional<Produto> categoriaEncontrada = repository.findById(id);
		return categoriaEncontrada.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
