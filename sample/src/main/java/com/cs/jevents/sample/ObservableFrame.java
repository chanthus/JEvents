package com.cs.jevents.sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ObservableFrame extends JFrame {

    private Model model;

    public ObservableFrame(Model mdl) {
        this.model = mdl;

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
