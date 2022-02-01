package com.projectW;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projectW.domain.Categoria;
import com.projectW.domain.Cidade;
import com.projectW.domain.Estado;
import com.projectW.domain.Produto;
import com.projectW.repositories.CategoriaRepository;
import com.projectW.repositories.CidadeRepository;
import com.projectW.repositories.EstadoRepository;
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
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectWApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Categoria cat1 = new Categoria(null, "TI");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Monitor", 1000);
		Produto p2 = new Produto(null, "Impressora", 900);
		Produto p3 = new Produto(null, "Mouse", 80);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p1.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p1.getCategorias().addAll(Arrays.asList(cat1));
		
		catRepository.saveAll(Arrays.asList(cat1, cat2));
		prodRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Rio Grande do Sul");
		Estado est2 = new Estado(null, "Santa Catarina");
		
		Cidade cid1 = new Cidade(null, "Porto Alegre", est1);
		Cidade cid2 = new Cidade(null, "Florianopolis", est2);
		Cidade cid3 = new Cidade(null, "Garopaba", est2);
		Cidade cid4 = new Cidade(null, "Lajeado", est1);
		
		estRepository.saveAll(Arrays.asList(est1, est2));
		cidRepository.saveAll(Arrays.asList(cid1, cid2, cid3, cid4)); 
		
		
		
		
		
	}

}
