package com.souza.caio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.caio.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Transactional(readOnly=true) // TUTORIAL: Não necessita ser envolvida com uma transação do BD, fazendo ficar mais rapido e diminuindo o locking do BD
	Cliente findByEmail(String email); //TUTORIAL: findBy é uma palavra chave do Spring para buscar pelo parametro do restante do nome
}
