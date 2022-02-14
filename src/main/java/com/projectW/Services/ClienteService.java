package com.projectW.Services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.projectW.DTO.ClienteDTO;
import com.projectW.DTO.ClienteNewDTO;
import com.projectW.Services.exceptions.DataIntegrityViolationException;
import com.projectW.domain.Cidade;
import com.projectW.domain.Cliente;
import com.projectW.domain.Endereco;
import com.projectW.domain.enums.TipoCliente;
import com.projectW.repositories.CidadeRepository;
import com.projectW.repositories.ClienteRepository;
import com.projectW.repositories.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository cliRepository;
	
	@Autowired
	private CidadeRepository cidRepository;
	
	@Autowired
	private EnderecoRepository endRepository;
	
	 public Cliente find(Integer id) {
		Optional<Cliente> obj = cliRepository.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: " 
		+ id + ", Tipo: " + Cliente.class.getName(), null));
		
	}
	 
	 public Cliente insert(Cliente obj) {
		 obj.setId(null);//garante q sera inserido apenas novo objeto
		 obj = cliRepository.save(obj);
		 endRepository.saveAll(obj.getEnderecos());
		 return obj;
	 }

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);		
		return cliRepository.save(newObj);
	}
	
	//metodo auxiliar para atualizar apenas dados nome e email
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	 
	public void delete(Integer id) {
		find(id);
		try {
			cliRepository.deleteById(id);			
		}catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Nao e possivel excluir Cliente que possui pedidos associados.");
		}
		
	}

	public List<Cliente> findAll() {
		return cliRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		//definicao de retorno de consulta (divisao por pagina)
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cliRepository.findAll(pageRequest);
	}
	
	//método auxiliar
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(),
				           null, null);		 
	}
	
	//método auxiliar
		public Cliente fromDTO(ClienteNewDTO objDTO) {
			Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(),
					                  objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
			Optional<Cidade> cid = cidRepository.findById(objDTO.getCidadeId());
			Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
					                    objDTO.getBairro(), objDTO.getCep(), cli, cid.get());
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDTO.getTelefone());
			if(objDTO.getTelefone2() != null) cli.getTelefones().add(objDTO.getTelefone2());
			if(objDTO.getTelefone3() != null) cli.getTelefones().add(objDTO.getTelefone3());
			
			return cli;
		 		        
		}

	
	
	
}


