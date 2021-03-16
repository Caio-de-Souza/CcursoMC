package com.souza.caio.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.caio.domain.Categoria;
import com.souza.caio.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	/*@Query("SELECT DISTINCT obj FROM Produto INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias") //TUTORIAL: O framework faz um pré-processamento da query e vincula com o método search
	Page<Produto> search(@Param("nome") String nome, @Param("catgorias") List<Categoria> categorias, PageRequest pageRequest); //TUTORIAL: @Param vincula o parâmetro do método com os definidos na query
	 */
	//findDistinctByNomeContainingAndCategoriasIn e search são equivalentes
	//TUTORIAL: Alternativa com padrão de nomes SpringData:
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, PageRequest pageRequest); //TUTORIAL: @Param vincula o parâmetro do método com os definidos na query
	
}
