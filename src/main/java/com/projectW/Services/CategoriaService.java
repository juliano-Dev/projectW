package com.projectW.Services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.projectW.DTO.CategoriaDTO;
import com.projectW.Services.exceptions.DataIntegrityViolationException;
import com.projectW.domain.Categoria;
import com.projectW.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository catRepository;
	
	
	 public Categoria find(Integer id) {
		Optional<Categoria> obj = catRepository.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: " 
		+ id + ", Tipo: " + Categoria.class.getName(), null));
		//(id, "ID Nao encontrado"));
	}
	 
	 
	public Categoria insert(Categoria obj) {
		 obj.setId(null);//garante q sera inserido apenas novo objeto
		 return catRepository.save(obj);
	 }

	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);		
		return catRepository.save(newObj);
	}
	 
	//metodo auxiliar para atualizar apenas descricao
		private void updateData(Categoria newObj, Categoria obj) {
			newObj.setDescricao(obj.getDescricao());
	}
		
	public void delete(Integer id) {
		find(id);
		try {
			catRepository.deleteById(id);			
		}catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nao e possivel excluir uma categoria que possui produtos associados.");
		}
		
	}

	public List<Categoria> findAll() {
		return catRepository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//definicao de retorno de consulta (divisao por pagina)
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return catRepository.findAll(pageRequest);
	}
	
	//método auxiliar
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getDescricao());
	}


	
}


