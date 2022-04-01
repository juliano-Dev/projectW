package com.projectW.Services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectW.domain.Pedido;
import com.projectW.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRepository;
	
	
	 public Pedido find(Integer id) {
		Optional<Pedido> obj = pedRepository.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: " 
		+ id + ", Tipo: " + Pedido.class.getName(), null));
		//(id, "ID Nao encontrado"));
	}
	
	 public List<Pedido> findAll() {
			return pedRepository.findAll();
		}
	 

	
	
	
}


