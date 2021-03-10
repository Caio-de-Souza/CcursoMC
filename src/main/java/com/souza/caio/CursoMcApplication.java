package com.souza.caio;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.souza.caio.domain.Categoria;
import com.souza.caio.domain.Cidade;
import com.souza.caio.domain.Cliente;
import com.souza.caio.domain.Endereco;
import com.souza.caio.domain.Estado;
import com.souza.caio.domain.ItemPedido;
import com.souza.caio.domain.Pagamento;
import com.souza.caio.domain.PagamentoComBoleto;
import com.souza.caio.domain.PagamentoComCartao;
import com.souza.caio.domain.Pedido;
import com.souza.caio.domain.Produto;
import com.souza.caio.domain.enums.EstadoPagamento;
import com.souza.caio.domain.enums.TipoCliente;
import com.souza.caio.repositories.CategoriaRepository;
import com.souza.caio.repositories.CidadeRepository;
import com.souza.caio.repositories.ClienteRepository;
import com.souza.caio.repositories.EnderecoRepository;
import com.souza.caio.repositories.EstadoRepository;
import com.souza.caio.repositories.ItemPedidoRepository;
import com.souza.caio.repositories.PagamentoRepository;
import com.souza.caio.repositories.PedidoRepository;
import com.souza.caio.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner{

	@Autowired //TUTORIAL: Instanciada automaticamente
	private CategoriaRepository categoriaRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private ProdutoRepository produtoRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private EstadoRepository estadoRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private CidadeRepository cidadeRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private ClienteRepository clienteRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private EnderecoRepository enderecoRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private PedidoRepository pedidoRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private PagamentoRepository pagamentoRepository;
	
	@Autowired //TUTORIAL: Instanciada automaticamente
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas" ,est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(
				null, 
				"Maria Silva", 
				"maria@gmail.com", 
				"36375912377", 
				TipoCliente.PESSOA_FISICA
		);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(
				null,
				"Rua Flores",
				"300",
				"Apto 303",
				"Jardim",
				"38220834",
				cli1,
				c1
		);
		
		Endereco e2 = new Endereco(
				null,
				"Avenida Matos",
				"105",
				"Sala 800",
				"Centro",
				"387770112",
				cli1,
				c2
		);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/01/2021 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/01/2021 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/03/2021 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}
