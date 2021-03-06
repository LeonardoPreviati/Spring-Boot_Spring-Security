package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DPUserDTO {
	
	@NotBlank(message = "Necessário inserir o nome do usuário!!!")
	private String name;
	
	@NotBlank(message = "Necessário inserir um email válido")
	private String email;
	
	@NotBlank(message = "Necessário inserir a senha!!!")
	private String password;
	
	public DPUserDTO() {
		
	}
	
	public DPUserDTO(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
