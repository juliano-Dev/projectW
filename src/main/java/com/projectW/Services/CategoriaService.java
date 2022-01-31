package com.projectW.Services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectW.domain.Categoria;
import com.projectW.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepository;
	
	
	 public Categoria buscar(Integer id) {
		Optional<Categoria> obj = catRepository.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: " 
		+ id + ", Tipo: " + Categoria.class.getName(), null));
		//(id, "ID Nao encontrado"));
	}
	 

	
	
	
}


