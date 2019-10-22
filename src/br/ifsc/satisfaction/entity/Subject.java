package br.ifsc.satisfaction.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

import br.ifsc.satisfaction.annotation.FormSpecs;
import br.ifsc.satisfaction.annotation.Visible;
import br.ifsc.satisfaction.presentation.component.GeneratedTextArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "Subject")
@FormSpecs(title = "Palestra", actionName = "Confirmar")
public class Subject extends EntityBase implements FormEntity {
	
	public Subject() {
		
	}
	
	@Visible(label = "Nome", required = true)
	private String name;

	@Visible(type = LocalDateTime.class, setDateToNow = true)
	private LocalDateTime updatedDate;

	@Visible(label = "Descrição", required = true, type = GeneratedTextArea.class, width = 20)
	private String description;

	public Object[] toObjectArray() {
		return new Object[] {getName(), getDescription(), getUpdatedDate().format(DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm")), getId()};
	}
}
