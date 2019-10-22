package br.ifsc.satisfaction.presentation.component;

import javax.swing.JTextArea;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneratedTextArea extends JTextArea implements GeneratedComponent {
	private static final long serialVersionUID = 1L;

	private String getter;
	private String setter;
	private boolean required;

	public GeneratedTextArea(int width, int height, String getter, String setter, boolean required) {
		super(width, height);
		this.getter = getter;
		this.setter = setter;
		this.required = required;
	}
}
