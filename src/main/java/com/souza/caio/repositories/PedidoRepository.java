package com.souza.caio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.souza.caio.domain.Cliente;
import com.souza.caio.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	@Transactional(readOnly=true) // TUTORIAL: Não necessita ser envolvida com uma transação do BD, fazendo ficar mais rapido e diminuindo o locking do BD
	Page<Pedido> findByCliente(Cliente cliente, PageRequest pageRequest);
}
