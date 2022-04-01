package com.projetoestudo.curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projetoestudo.curso.domain.Categoria;
import com.projetoestudo.curso.domain.Cidade;
import com.projetoestudo.curso.domain.Cliente;
import com.projetoestudo.curso.domain.Endereco;
import com.projetoestudo.curso.domain.Estado;
import com.projetoestudo.curso.domain.Pagamento;
import com.projetoestudo.curso.domain.PagamentoComBoleto;
import com.projetoestudo.curso.domain.PagamentoComCartao;
import com.projetoestudo.curso.domain.Pedido;
import com.projetoestudo.curso.domain.Produto;
import com.projetoestudo.curso.domain.enums.EstadoPagamento;
import com.projetoestudo.curso.domain.enums.TipoCliente;
import com.projetoestudo.curso.repositories.CategoriaRepository;
import com.projetoestudo.curso.repositories.CidadeRepository;
import com.projetoestudo.curso.repositories.ClienteRepository;
import com.projetoestudo.curso.repositories.EnderecoRepository;
import com.projetoestudo.curso.repositories.EstadoRepository;
import com.projetoestudo.curso.repositories.PagamentoRepository;
import com.projetoestudo.curso.repositories.PedidoRepository;
import com.projetoestudo.curso.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
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
		Cidade c3 = new Cidade(null, "Campinas", est2);
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "03059000131", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("11321131313", "31122323123"));

		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		// Endereco end3 = new Endereco(null, "Avenida Matos", "105", "Sala 800",
		// "Centro", "38777012", );
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido pedido = new Pedido(null, simpleDateFormat.parse("30/03/2022 16:50"), cli1, end1);
		Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("30/03/2022 16:51"), cli1, end2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido, 6);
		pedido.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PEDENTE, pedido2,
				simpleDateFormat.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagto2);
		cli1.getPedidos().addAll(Arrays.asList(pedido, pedido2));

		pedidoRepository.saveAll(Arrays.asList(pedido, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));


	}

}
