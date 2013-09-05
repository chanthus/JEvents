package jevents.sample;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jws.WebParam.Mode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ObserverFrame extends JFrame implements ITextChanged {

	private Model model;
	private JLabel label;

	public ObserverFrame(Model mdl) {
		this.model = mdl;

		// Register to receive event
		model.textChanged.addListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 200);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("Waitng for observer...");
		label.setBounds(10, 11, 260, 140);
		contentPane.add(label);

		JButton clearBut = new JButton("Clear");
		clearBut.setBounds(280, 56, 156, 40);
		contentPane.add(clearBut);

		clearBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText("");
			}
		});
	}

	@Override
	public void textChanged(String text) {
		label.setText(text);
	}
}
