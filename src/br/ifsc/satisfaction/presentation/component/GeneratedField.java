package br.ifsc.satisfaction.presentation.component;

import javax.swing.JTextField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratedField extends JTextField implements GeneratedComponent {
	private static final long serialVersionUID = 1L;

	private String getter;
	private String setter;
	private boolean required;

	public GeneratedField(int size, String getter, String setter, boolean required) {
		super(size);
		this.getter = getter;
		this.setter = setter;
		this.required = required;
	}

}
