package com.projectW.Services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectW.domain.Cliente;
import com.projectW.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepository;
	
	
	 public Cliente buscar(Integer id) {
		Optional<Cliente> obj = cliRepository.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: " 
		+ id + ", Tipo: " + Cliente.class.getName(), null));
		//(id, "ID Nao encontrado"));
	}
	 

	
	
	
}


