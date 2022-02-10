package com.projectW;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projectW.domain.Categoria;
import com.projectW.domain.Cidade;
import com.projectW.domain.Cliente;
import com.projectW.domain.Endereco;
import com.projectW.domain.Estado;
import com.projectW.domain.ItemPedido;
import com.projectW.domain.Pagamento;
import com.projectW.domain.PagamentoComBoleto;
import com.projectW.domain.PagamentoComCartao;
import com.projectW.domain.Pedido;
import com.projectW.domain.Produto;
import com.projectW.domain.enums.EstadoPagamento;
import com.projectW.domain.enums.TipoCliente;
import com.projectW.repositories.CategoriaRepository;
import com.projectW.repositories.CidadeRepository;
import com.projectW.repositories.ClienteRepository;
import com.projectW.repositories.EnderecoRepository;
import com.projectW.repositories.EstadoRepository;
import com.projectW.repositories.ItemPedidoRepository;
import com.projectW.repositories.PagamentoRepository;
import com.projectW.repositories.PedidoRepository;
import com.projectW.repositories.ProdutoRepository;


@SpringBootApplication
public class ProjectWApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository catRepository;
	
	@Autowired
	private ProdutoRepository prodRepository;
	
	@Autowired
	private CidadeRepository cidRepository;
	
	@Autowired
	private EstadoRepository estRepository;
	
	@Autowired
	private ClienteRepository cliRepository;
	
	@Autowired
	private EnderecoRepository endRepository;
	
	@Autowired
	private PedidoRepository pedRepository;
	
	@Autowired
	private PagamentoRepository pagRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectWApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "TI");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Ferramentas");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Livros");
		Categoria cat6 = new Categoria(null, "Roupas");
		Categoria cat7 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat8 = new Categoria(null, "Decoracao");
		Categoria cat9 = new Categoria(null, "Outlet");
		
		Produto p1 = new Produto(null, "Monitor", 1000);
		Produto p2 = new Produto(null, "Impressora", 900);
		Produto p3 = new Produto(null, "Mouse", 80);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p1.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p1.getCategorias().addAll(Arrays.asList(cat1));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5,
				                            cat6, cat7, cat8, cat9));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Rio Grande do Sul");
		Estado est2 = new Estado(null, "Santa Catarina");
		
		Cidade cid1 = new Cidade(null, "Porto Alegre", est1);
		Cidade cid2 = new Cidade(null, "Florianopolis", est2);
		Cidade cid3 = new Cidade(null, "Garopaba", est2);
		Cidade cid4 = new Cidade(null, "Lajeado", est1);
		
		estRepository.saveAll(Arrays.asList(est1, est2));
		cidRepository.saveAll(Arrays.asList(cid1, cid2, cid3, cid4)); 
		
		Cliente cli1 = new Cliente(null, "Teste", "teste@teste", "243234234234", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12312312312", "a123123123"));
		
		Endereco e1 = new Endereco(null, "Assis Brasil", "300", "Apto 200", "Sarandi", "12312312", cli1, cid1);
		Endereco e2 = new Endereco(null, "Assis Brasil", "300", "Apto 12", "Centro", "123123123", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		//Cliente cli2 = new Cliente(null, "Teste", "teste@teste", "243234234234", TipoCliente.PESSOAFISICA);
		//Cliente cli3 = new Cliente(null, "Teste", "teste@teste", "243234234234", TipoCliente.PESSOAFISICA);
		
		cliRepository.saveAll(Arrays.asList(cli1));
		endRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/01/2022 11:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("30/01/2022 10:30"), cli1, e2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.FINALIZADO, ped1, 6); 
		ped1.setPagamento(pgto1);
		
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("01/02/2021 00:00"), null);
		ped2.setPagamento(pgto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedRepository.saveAll(Arrays.asList(ped1, ped2));
		
		pagRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2.000);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p3, 100.00, 2, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip1));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
		
		
		
	}

}
