package com.projectW.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projectW.DTO.CategoriaDTO;
import com.projectW.Services.CategoriaService;
import com.projectW.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService catService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL na funcao find
		Categoria obj = catService.find(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = catService.fromDTO(objDTO); 
		obj = catService.insert(obj);
		//informa url do item inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = catService.fromDTO(objDTO);
		obj.setId(id);
		obj = catService.update(obj);
		ResponseEntity.noContent().build();
		return null;
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBy(@PathVariable Integer id){
		catService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity <List<CategoriaDTO>> findAll(){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL na funcao find
		List<Categoria> list = catService.findAll();//busca lista de categorias
		List<CategoriaDTO> listDTO = list.stream().map(obj -> //stream percorre a lista. map executa uma operacao para cada elemento. converte lista em objs DTO
		new CategoriaDTO(obj)).collect(Collectors.toList());//reverte de DTO para lista novamente
		return ResponseEntity.ok().body(listDTO);		
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity <Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0")
			Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")
			Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="descricao")
			String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")
			String direction){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL na funcao find
		Page<Categoria> list = catService.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);	
		
	}
	
	
	
}
