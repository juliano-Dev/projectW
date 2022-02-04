package com.projectW.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projectW.Services.ClienteService;
import com.projectW.domain.Cliente;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService cliService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		//ResponseEntity retorna codigo HTTP
		//PathVariable usa o id da URL na funcao find
		Cliente obj = cliService.buscar(id);
		return ResponseEntity.ok().body(obj);		
	}
	

}
