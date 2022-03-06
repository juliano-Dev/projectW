package com.projectW.Services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.projectW.Services.exceptions.DataIntegrityViolationException;
import com.projectW.domain.Categoria;
import com.projectW.domain.Produto;
import com.projectW.repositories.CategoriaRepository;
import com.projectW.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository prodRepository;
	
	@Autowired
	private CategoriaRepository catRepository;
	
	
	 public Produto find(Integer id) {
		Optional<Produto> obj = prodRepository.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: " 
		+ id + ", Tipo: " + Produto.class.getName(), null));
		//(id, "ID Nao encontrado"));
	}
	 
	public Page<Produto> findByName(String descricao, List<Integer> ids, Integer page, 
			Integer linesPerPage, String orderBy, String direction){
		//definicao de retorno de consulta (divisao por pagina)
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepository.findAllById(ids);
		return prodRepository.findDistinctByDescricaoContainingAndCategoriasIn(descricao, categorias, pageRequest);
	}
	 
	public Produto insert(Produto obj) {
		 obj.setId(null);//garante q sera inserido apenas novo objeto
		 return prodRepository.save(obj);
	 }

	public Produto update(Produto obj) {
		Produto newObj = find(obj.getId());
		updateData(newObj, obj);		
		return prodRepository.save(newObj);
	}
	 
	//metodo auxiliar para atualizar apenas descricao
		private void updateData(Produto newObj, Produto obj) {
			newObj.setDescricao(obj.getDescricao());
	}
		
	public void delete(Integer id) {
		find(id);
		try {
			prodRepository.deleteById(id);			
		}catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nao e possivel excluir uma produtoegoria que possui produtos associados.");
		}
		
	}

	public List<Produto> findAll() {
		return prodRepository.findAll();
	}

	public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//definicao de retorno de consulta (divisao por pagina)
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return prodRepository.findAll(pageRequest);
	}
	
	//método auxiliar
	//public Produto fromDTO(ProdutoDTO objDTO) {
	//	return new Produto(objDTO.getId(), objDTO.getDescricao());
	//}


	
}


