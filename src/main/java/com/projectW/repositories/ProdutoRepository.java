package com.projectW.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectW.domain.Categoria;
import com.projectW.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	//@Transactional(readOnly=true)
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.descricao LIKE %:descricao% AND cat IN :categorias")
	//Page<Produto> search(@Param("descricao") String descricao, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	Page<Produto> findDistinctByDescricaoContainingAndCategoriasIn(String descricao, List<Categoria> categorias, Pageable pageRequest);
}
