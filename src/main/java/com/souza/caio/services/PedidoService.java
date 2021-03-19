package com.souza.caio.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.caio.domain.ItemPedido;
import com.souza.caio.domain.PagamentoComBoleto;
import com.souza.caio.domain.Pedido;
import com.souza.caio.domain.enums.EstadoPagamento;
import com.souza.caio.repositories.ItemPedidoRepository;
import com.souza.caio.repositories.PagamentoRepository;
import com.souza.caio.repositories.PedidoRepository;
import com.souza.caio.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired //TUTORIAL: Instanciada automaticamente
	private PedidoRepository repository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private BoletoService boletoService;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private PagamentoRepository pagamentoRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private ProdutoService produtoService;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido read(Integer id){
		Optional<Pedido> pedidosEncontrados = repository.findById(id);
		return pedidosEncontrados.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido create( Pedido novoPedido) {
		novoPedido.setId(null);
		novoPedido.setInstante(new Date());
		
		novoPedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		novoPedido.getPagamento().setPedido(novoPedido);
		
		if(novoPedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagBoleto = (PagamentoComBoleto) novoPedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagBoleto, novoPedido.getInstante());
		}
		
		novoPedido = repository.save(novoPedido);
		pagamentoRepository.save(novoPedido.getPagamento());
		
		for(ItemPedido itemPedido : novoPedido.getItens()) {
				itemPedido.setDesconto(0.0);
				itemPedido.setPreco(produtoService.read(itemPedido.getProduto().getId()).getPreco());
				itemPedido.setPedido(novoPedido);
		}
		itemPedidoRepository.saveAll(novoPedido.getItens());
		return novoPedido;
	}
}
