package br.ifsc.satisfaction.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.ifsc.satisfaction.annotation.Visible;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Avaliation extends EntityBase implements FormEntity {
	
	@Visible(required = true)
	private int rate;
	private String suggestion;

	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "subject_id")
	private Subject subject;
}
