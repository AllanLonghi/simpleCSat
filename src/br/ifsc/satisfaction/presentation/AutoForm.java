package br.ifsc.satisfaction.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import br.ifsc.satisfaction.annotation.FormSpecs;
import br.ifsc.satisfaction.annotation.Visible;
import br.ifsc.satisfaction.entity.FormEntity;
import br.ifsc.satisfaction.presentation.component.GeneratedComponent;
import br.ifsc.satisfaction.presentation.component.GeneratedField;
import br.ifsc.satisfaction.presentation.component.GeneratedPanel;
import br.ifsc.satisfaction.presentation.component.GeneratedTextArea;
import br.ifsc.satisfaction.repository.GenericRepository;
import lombok.Getter;

@Getter
public class AutoForm extends JFrame {
	private static final long serialVersionUID = 1L;

	private FormEntity formEntity;
	private boolean saveEntity;
	private boolean undecorated = true;

	public AutoForm(FormEntity formEntity) {
		initialize();
		this.formEntity = formEntity;
		saveEntity = true;
	}

	public AutoForm(FormEntity formEntity, boolean saveEntity) {
		undecorated = true;
		initialize();
		this.formEntity = formEntity;
		this.saveEntity = saveEntity;
	}

	public void Show() {
		processFields();
		setVisible(true);
	}

	private void processFields() {
		Class<?> entityClass = formEntity.getClass();
		if (formEntity.getClass().isAnnotationPresent(FormSpecs.class)) {
			for (Field field : entityClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(Visible.class)) {
					Visible annot = field.getAnnotation(Visible.class);
					if ((annot.type() != LocalDateTime.class) && !annot.setDateToNow()) {
						generateVisual(field, annot);
					}
				}
			}

			String btnName = formEntity.getClass().getAnnotation(FormSpecs.class).actionName();
			addButton(btnName);
			setSize(formEntity.getClass().getAnnotation(FormSpecs.class).width(),
					formEntity.getClass().getAnnotation(FormSpecs.class).height());
			setTitle(formEntity.getClass().getAnnotation(FormSpecs.class).title());
		}
	}

	private void addButton(String btnName) {
		JButton btn = new JButton(btnName);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (formValid()) {
					setValuesFromComponentToEntity();
					setAutomaticValuesForLocalDateTime();
					if (saveEntity)
						GenericRepository.salvar(formEntity);
					dispose();
				}
			}
		});
		getContentPane().add(getPanelForFields(new ArrayList<Component>() {
			private static final long serialVersionUID = 1L;

			{
				getContentPane().add(btn);
			}
		}));
	}

	private void generateVisual(Field field, Visible visible) {
		JLabel label = null;
		Component fieldComponent = null;
		List<Component> compToFrame = new ArrayList<Component>();
		if (visible.type() == GeneratedField.class) {
			fieldComponent = new GeneratedField(visible.size(), getPrefixed(field, "get"), getPrefixed(field, "set"),
					visible.required());
		} else if (visible.type() == GeneratedTextArea.class) {
			fieldComponent = new GeneratedTextArea(visible.height(), visible.width(), getPrefixed(field, "get"),
					getPrefixed(field, "set"), visible.required());
			fieldComponent = new JScrollPane(fieldComponent);
		}

		if (!visible.label().trim().equalsIgnoreCase("")) {
			label = new JLabel(visible.label() + (visible.required() ? "*:" : ":"));
			label.setLabelFor(fieldComponent);
			compToFrame.add(label);
		}

		compToFrame.add(fieldComponent);
		getContentPane().add(getPanelForFields(compToFrame));
	}

	protected void setValuesFromComponentToEntity() {
		String setter = "", value = "";
		for (Component component : getAllComponents(this)) {
			try {
				if (component instanceof GeneratedField) {
					GeneratedField fieldComp = (GeneratedField) component;
					setter = fieldComp.getSetter();
					value = fieldComp.getText();
				} else if (component instanceof GeneratedTextArea) {
					GeneratedTextArea fieldComp = (GeneratedTextArea) component;
					setter = fieldComp.getSetter();
					value = fieldComp.getText();
				}
				if (!setter.equals("")) {
					Method method = formEntity.getClass().getMethod(setter, String.class);
					method.invoke(formEntity, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void setAutomaticValuesForLocalDateTime() {
		for (Field field : formEntity.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Visible.class) && field.getAnnotation(Visible.class).setDateToNow()) {
				try {
					Method method = formEntity.getClass().getMethod(getPrefixed(field, "set"), LocalDateTime.class);
					method.invoke(formEntity, LocalDateTime.now());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected boolean formValid() {
		boolean valid = true;
		for (Component component : getAllComponents(this)) {
			if (component instanceof GeneratedComponent) {
				GeneratedComponent fieldComp = (GeneratedComponent) component;
				if (fieldComp.getText().trim().equals("") && fieldComp.isRequired()) {
					Border border = BorderFactory.createLineBorder(Color.RED, 2);
					fieldComp.setBorder(border);
					valid = false;
				} else {
					fieldComp.setBorder(null);
				}
			}
		}
		return valid;
	}

	private void initialize() {
		setType(Type.POPUP);
		setAlwaysOnTop(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setUndecorated(undecorated);
		getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
	}

	private String getPrefixed(Field field, String prefix) {
		return prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	}

	private List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}

	private JPanel getPanelForFields(List<Component> compToFrame) {
		GeneratedPanel panel = new GeneratedPanel();
		panel.setBorder(new EmptyBorder(8, 8, 8, 8));
		GridLayout layout = new GridLayout(2, 0, 5, 5);
		layout.setHgap(0);
		panel.setLayout(layout);
		for (Component component : compToFrame) {
			panel.add(component);
		}
		return panel;
	}

}
