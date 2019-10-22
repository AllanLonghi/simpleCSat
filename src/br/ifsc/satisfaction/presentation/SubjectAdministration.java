package br.ifsc.satisfaction.presentation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import br.ifsc.satisfaction.entity.Subject;
import br.ifsc.satisfaction.repository.GenericRepository;

public class SubjectAdministration {

	private JFrame frame;
	private List<Subject> subjects;
	private JTable table;

	public SubjectAdministration() {
		initialize();
		populateList();
	}

	public void Show() {
		frame.setVisible(true);
	}

	public void populateList() {
		subjects = GenericRepository.getAll(Subject.class);
		Object[][] rowData = new Object[subjects.size()][];
		for (int i = 0; i < subjects.size(); i++) {
			rowData[i] = subjects.get(i).toObjectArray();
		}
		String[] columnNames = { "Palestra", "Descrição", "Data Inclusão" };
		DefaultTableModel model = new DefaultTableModel(rowData, columnNames);
		table.setModel(model);
		model.fireTableDataChanged();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 760, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AutoForm(new Subject()).Show();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNovo, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNovo, -228, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnNovo);

		JButton btnIniciarSesso = new JButton("Iniciar Pesquisa");
		springLayout.putConstraint(SpringLayout.NORTH, btnIniciarSesso, 6, SpringLayout.SOUTH, btnNovo);
		springLayout.putConstraint(SpringLayout.WEST, btnNovo, -147, SpringLayout.EAST, btnIniciarSesso);
		springLayout.putConstraint(SpringLayout.EAST, btnNovo, 0, SpringLayout.EAST, btnIniciarSesso);
		springLayout.putConstraint(SpringLayout.WEST, btnIniciarSesso, 587, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnIniciarSesso, -10, SpringLayout.EAST, frame.getContentPane());
		btnIniciarSesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					new Survey(subjects.get(table.getSelectedRow()));
					frame.dispose();
				}else {
					selectRowOptionPanel();
				}
			}
		});
		frame.getContentPane().add(btnIniciarSesso);

		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 251, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -6, SpringLayout.WEST, btnNovo);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setBackground(Color.GRAY);
		scrollPane.setViewportView(table);

		JButton btnAtualizar = new JButton("Atualizar");
		springLayout.putConstraint(SpringLayout.WEST, btnAtualizar, 6, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, btnAtualizar, -10, SpringLayout.EAST, frame.getContentPane());
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				populateList();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnAtualizar, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnAtualizar);
		
		JButton btnNewButton = new JButton("Gráfico");
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 6, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnIniciarSesso, -6, SpringLayout.NORTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 68, SpringLayout.NORTH, frame.getContentPane());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					new Chart("Gráfico", subjects.get(table.getSelectedRow())).Show();
				}else {
					selectRowOptionPanel();
				}
			}
		});
		frame.getContentPane().add(btnNewButton);
	}
	
	public void selectRowOptionPanel() {
		JOptionPane.showMessageDialog(frame, "Selecione uma palestra!");
	}
}
