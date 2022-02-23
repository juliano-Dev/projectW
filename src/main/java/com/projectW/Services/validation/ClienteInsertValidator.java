package com.projectW.Services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.projectW.DTO.ClienteNewDTO;
import com.projectW.Services.validation.utils.Validator_CPF_CNPJ;
import com.projectW.domain.enums.TipoCliente;
import com.projectW.resources.exception.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{
	
	
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	
	@Override
	public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		//validações CPF e CNPJ
		if(objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !Validator_CPF_CNPJ.isValidCPF(objDTO.getCpfOuCnpj()))
			{list.add(new FieldMessage("cpfOuCnpj", "CPF invalido."));}
		if(objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !Validator_CPF_CNPJ.isValidCNPJ(objDTO.getCpfOuCnpj()))
			{list.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido."));}		
		
		
		for(FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}
	
	
	

}
