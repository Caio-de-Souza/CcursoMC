package com.souza.caio.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.souza.caio.domain.Produto;
import com.souza.caio.dto.ProdutoDTO;
import com.souza.caio.resources.utils.URL;
import com.souza.caio.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired // TUTORIAL: Instanciada automaticamente
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> read(@PathVariable Integer id) {
		Produto produtoEncontrado = service.read(id);

		return ResponseEntity.ok().body(produtoEncontrado);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> readAllPageable(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		List<Integer> idsCategorias = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> produtosEncontrados = service.search(nomeDecoded, idsCategorias, page, linesPerPage, orderBy, direction);	
		Page<ProdutoDTO> produtosDTO = 
				produtosEncontrados
				.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok().body(produtosDTO);
	}

}
