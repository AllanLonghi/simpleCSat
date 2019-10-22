package br.ifsc.satisfaction.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;

import br.ifsc.satisfaction.entity.Avaliation;
import br.ifsc.satisfaction.entity.AvaliationSession;
import br.ifsc.satisfaction.entity.Subject;
import br.ifsc.satisfaction.repository.GenericRepository;

public class Survey {

	private JFrame frame;
	private Subject subject;
	private List<Avaliation> avaliations;
	private AvaliationSession avaliationSession;
	private AvaliationSession confirmationAvaliationSession;
	JDialog dialog;

	public Survey(Subject subject) {
		this.subject = subject;
		avaliations = new ArrayList<Avaliation>();
		avaliationSession = new AvaliationSession();
		confirmationAvaliationSession = new AvaliationSession();
		setSurveySession();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		frame.setBounds(100, 100, 1057, 414);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBackground(new Color(0x7c8c94));
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 0, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.EAST, panel, 1057, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, frame.getContentPane());
		panel.setBackground(new Color(51, 102, 153));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("Descrição:");
		label.setBounds(10, 30, 73, 14);
		panel.add(label);

		JLabel label_2 = new JLabel("Palestra:");
		label_2.setBounds(10, 10, 73, 14);
		panel.add(label_2);

		JLabel label_palestra = new JLabel(subject.getName());
		label_palestra.setBounds(91, 10, 984, 14);
		panel.add(label_palestra);

		JLabel label_description = new JLabel(subject.getDescription());
		label_description.setBounds(93, 30, 981, 14);
		panel.add(label_description);

		JLabel label_1 = new JLabel("Como você se sente com relação a palestra que acabou de assistir?");
		label_1.setBounds(10, 7, 669, 23);
		label_1.setFont(new Font("Arial Black", Font.BOLD, 16));
		panel_1.add(label_1);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAvaliation(5);
			}
		});
		button.setIcon(new ImageIcon(Survey.class.getResource("/images/ruim.png")));
		button.setBounds(10, 41, 200, 197);
		panel_1.add(button);

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAvaliation(4);
			}
		});
		button_1.setIcon(new ImageIcon(Survey.class.getResource("/images/ruim2.png")));
		button_1.setBounds(217, 41, 200, 197);
		panel_1.add(button_1);

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAvaliation(3);
			}
		});
		button_2.setIcon(new ImageIcon(Survey.class.getResource("/images/regular.png")));
		button_2.setBounds(427, 41, 200, 197);
		panel_1.add(button_2);

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAvaliation(2);
			}
		});
		button_3.setIcon(new ImageIcon(Survey.class.getResource("/images/bom.png")));
		button_3.setBounds(637, 41, 200, 197);
		panel_1.add(button_3);

		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAvaliation(1);
			}
		});
		button_4.setIcon(new ImageIcon(Survey.class.getResource("/images/otimo.png")));
		button_4.setBounds(847, 41, 200, 197);
		panel_1.add(button_4);

		JLabel label_3 = new JLabel("");
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -123, SpringLayout.NORTH, label_3);
		springLayout.putConstraint(SpringLayout.NORTH, label_3, 198, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, label_3, 984, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(label_3);

		JButton btnEncerrar = new JButton("Encerrar");
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, -55, SpringLayout.NORTH, btnEncerrar);
		springLayout.putConstraint(SpringLayout.NORTH, btnEncerrar, -34, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnEncerrar, -110, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnEncerrar, -11, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnEncerrar, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnEncerrar);
		btnEncerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmSessionEnd();
			}
		});
		
		JOptionPane pane = new JOptionPane("Obrigado pela participação!", JOptionPane.INFORMATION_MESSAGE);
        dialog = pane.createDialog(null, "Obrigado!");
        dialog.setModal(false);
	}

	private void setSurveySession() {
		AutoForm auto = new AutoForm(avaliationSession);
		auto.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if (!avaliationSession.getUser().equals("")) {
					frame.setVisible(true);
				}
			}
		});
		auto.Show();
	}

	private void confirmSessionEnd() {
		frame.setEnabled(false);
		AutoForm auto = new AutoForm(confirmationAvaliationSession, false);
		auto.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if ((avaliationSession.getPassword().equals(confirmationAvaliationSession.getPassword())
						&& avaliationSession.getUser().equals(confirmationAvaliationSession.getUser()))
						|| confirmationAvaliationSession.getPassword().equals("admin")
								&& confirmationAvaliationSession.getUser().equals("admin")) {
					endAvaliationSession();
				} else {
					frame.setEnabled(true);
				}
			}
		});
		auto.Show();
	}
	
	private void setAvaliation(int rate) {
		Avaliation avaliation = new Avaliation();
		avaliation.setRate(rate);
		avaliation.setSubject(subject);
		avaliations.add(avaliation);
		showThanx();
	}

	private void saveAllAvaliations() {
		for (Avaliation avaliation : avaliations) {
			GenericRepository.salvar(avaliation);
		}
	}

	private void endAvaliationSession() {
		saveAllAvaliations();
		new SubjectAdministration().Show();
		frame.dispose();
	}

	private void showThanx() {
		dialog.setVisible(true);

		Timer timer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
		timer.start();
	}
}
