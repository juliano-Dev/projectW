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

import com.projectW.DTO.ClienteDTO;
import com.projectW.DTO.ClienteNewDTO;
import com.projectW.Services.ClienteService;
import com.projectW.domain.Cliente;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService cliService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL no metodo find
		Cliente obj = cliService.find(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
		Cliente obj = cliService.fromDTO(objDTO); 
		obj = cliService.insert(obj);
		//informa url do item inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
		
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = cliService.fromDTO(objDTO);
		obj.setId(id);
		obj = cliService.update(obj);
		ResponseEntity.noContent().build();
		return null;
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBy(@PathVariable Integer id){
		cliService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity <List<ClienteDTO>> findAll(){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL na funcao find
		List<Cliente> list = cliService.findAll();//busca lista de categorias
		List<ClienteDTO> listDTO = list.stream().map(obj -> //stream percorre a lista. map executa uma operacao para cada elemento. converte lista em objs DTO
		new ClienteDTO(obj)).collect(Collectors.toList());//reverte de DTO para lista novamente
		return ResponseEntity.ok().body(listDTO);		
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity <Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0")
			Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")
			Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")
			String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")
			String direction){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL na funcao find
		Page<Cliente> list = cliService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);	
		
	}
	
	
}
