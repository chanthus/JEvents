package com.cs.jevents.sample;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObserverFrame extends JFrame implements TextChanged {

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
        label.setText("Waiting for observer...");
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
