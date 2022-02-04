package com.projectW.domain.enums;

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Fisica"),//seguranca enums
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	private int cod;
	private String descricao;
	
	//construtor enum eh privado
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null) {return null;}
		
		for(TipoCliente c : TipoCliente.values()) {
			if(cod.equals(c.getCod())) {return c;}
		}
		
		throw new IllegalArgumentException("Id invalido: "+ cod);
		
	}
	
}
