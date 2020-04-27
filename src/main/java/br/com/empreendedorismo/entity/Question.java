package br.com.empreendedorismo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Question {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "question_id")
	private Integer id;
	private String description;
}
