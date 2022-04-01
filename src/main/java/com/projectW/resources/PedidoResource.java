package com.projectW.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projectW.Services.PedidoService;
import com.projectW.domain.Pedido;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		// ResponseEntity retorna codigo HTTP
		// PathVariable usa o id da URL na funcao find
		Pedido obj = pedService.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Pedido>> findAll() {
		// ResponseEntity retorna codigo HTTP
		// PathVariable usa o id da URL na funcao find
		// List<Categoria> list = catService.findAll();//busca lista de categorias
		// List<CategoriaDTO> listDTO = list.stream().map(obj -> //stream percorre a
		// lista. map executa uma operacao para cada elemento. converte lista em objs
		// DTO
		List<Pedido> list = pedService.findAll();
		return ResponseEntity.ok().body(list);
	}

}
