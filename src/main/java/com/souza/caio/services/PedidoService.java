package com.souza.caio.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souza.caio.domain.Cliente;
import com.souza.caio.domain.ItemPedido;
import com.souza.caio.domain.PagamentoComBoleto;
import com.souza.caio.domain.Pedido;
import com.souza.caio.domain.enums.EstadoPagamento;
import com.souza.caio.repositories.ItemPedidoRepository;
import com.souza.caio.repositories.PagamentoRepository;
import com.souza.caio.repositories.PedidoRepository;
import com.souza.caio.security.UserSS;
import com.souza.caio.services.exceptions.AuthorizationException;
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
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private ClienteService clienteService;
	
	//@Autowired //TUTORIAL: Instanciada automaticamente
	//private EmailService emailService;
	
	public Pedido read(Integer id){
		Optional<Pedido> pedidosEncontrados = repository.findById(id);
		return pedidosEncontrados.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido create( Pedido novoPedido) {
		novoPedido.setId(null);
		novoPedido.setInstante(new Date());
		novoPedido.setCliente(clienteService.read(novoPedido.getCliente().getId()));
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
				itemPedido.setProduto(produtoService.read(itemPedido.getProduto().getId()));
				itemPedido.setPreco(itemPedido.getProduto().getPreco());
				itemPedido.setPedido(novoPedido);
		}
		itemPedidoRepository.saveAll(novoPedido.getItens());
		//emailService.sendOrderConfirmationHtmlEmail(novoPedido);
		return novoPedido;
	}
	
	public Page<Pedido> readPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		
		if(user != null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.read(user.getId());	
		
		return repository.findByCliente(cliente, pageRequest);
	}

}
