package jevents.sample;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ObservableFrame extends JFrame {

	public ObservableFrame(final Model model) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 200);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JTextArea textArea = new JTextArea("Some text...");
		textArea.setBounds(10, 11, 260, 140);
		contentPane.add(textArea);

		JButton textChangeBut = new JButton("Update Text");
		textChangeBut.setBounds(280, 56, 156, 40);
		contentPane.add(textChangeBut);

		textChangeBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setText(textArea.getText());// Set model text
			}
		});
	}
}
