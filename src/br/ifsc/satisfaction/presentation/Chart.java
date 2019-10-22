package br.ifsc.satisfaction.presentation;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.ifsc.satisfaction.entity.Avaliation;
import br.ifsc.satisfaction.entity.Subject;
import br.ifsc.satisfaction.repository.GenericRepository;

public class Chart extends JFrame {
	private static final long serialVersionUID = 6294689542092367723L;
	private JFreeChart chart;
	private int great = 0, good = 0, regular = 0, fair = 0, poor = 0;
	private Subject subject;
	
	public Chart(String title, Subject subject) {
		super(title);
		this.subject = subject;
		init();
	}
	
	private void init() {
		getValues();
		PieDataset dataset = createDataset();
		chart = ChartFactory.createPieChart("Pie Chart Example", dataset, true, true, false);
		PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} : ({2})",
				new DecimalFormat("0"), new DecimalFormat("0%"));
		((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
		setColors();
	}
	
	private void getValues() {
		List<Avaliation> avaliations = GenericRepository.getAll(Avaliation.class, " subject_id = " + Long.toString(subject.getId()));
		for (Avaliation avaliation : avaliations) {
			if (avaliation.getRate() == 1) great++;
			if (avaliation.getRate() == 2) good++;
			if (avaliation.getRate() == 3) regular++;
			if (avaliation.getRate() == 4) fair++;
			if (avaliation.getRate() == 5) poor++;
		}
	}
	
	private void setColors() {
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionPaint("Ótimo", Color.GREEN);
		plot.setSectionPaint("Bom", Color.YELLOW);
		plot.setSectionPaint("Regular", Color.ORANGE);
		plot.setSectionPaint("Ruim", new Color(0xff1a1a));
		plot.setSectionPaint("Péssimo", Color.RED);
	}

	private PieDataset createDataset() {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Ótimo", great);
		dataset.setValue("Bom", good);
		dataset.setValue("Regular", regular);
		dataset.setValue("Ruim", fair);
		dataset.setValue("Péssimo", poor);
		return dataset;
	}

	public void Show() {
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	

}