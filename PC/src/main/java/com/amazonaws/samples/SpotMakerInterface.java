package com.amazonaws.samples;// Created by Austin Patel
// 10/21/2017

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SpotMakerInterface extends JFrame {

    private DefaultListModel<String> model;
    private SpotPanel spotPanel;
    private JList<String> spotsJList;
    private JButton deleteSpotButton;

    public static void main(String[] args) {
        S3Sample.init();
        new SpotMakerInterface();
    }

    public SpotMakerInterface() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 800);

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setTitle("Parking Lot Space Detector");

        try {
            Image icon = new ImageIcon(ImageIO.read(new File("res/icon.png"))).getImage();
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Drawing spots and parking feed
        spotPanel = new SpotPanel(this);
        System.out.println(spotPanel.getWidth() + ", " + spotPanel.getHeight());
        add(spotPanel);


        JPanel managerPanel = new JPanel();
        managerPanel.setLayout(new BorderLayout());

        // Parking spot manager
        model = new DefaultListModel<>();
        spotsJList = new JList<>(model);
        spotsJList.setBackground(getBackground());
        spotsJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                spotPanel.setSelectedIndex(spotsJList.getSelectedIndex());

                if (spotsJList.getSelectedIndex() != -1)
                    deleteSpotButton.setEnabled(true);
            }
        });
        managerPanel.add(spotsJList, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        // Delete Parking Spots
        deleteSpotButton = new JButton("Delete Spot");
        deleteSpotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spotPanel.deleteSpot(spotsJList.getSelectedIndex());

                if (model.getSize() == 0)
                    deleteSpotButton.setEnabled(false);
            }
        });
        deleteSpotButton.setEnabled(false);
        buttonPanel.add(deleteSpotButton, BorderLayout.NORTH);

        // upload spots button
        JButton uploadButton = new JButton("Upload spots");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    S3Sample.upload_parking_spots(spotPanel.getSpots());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(uploadButton, BorderLayout.SOUTH);

        managerPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(managerPanel, BorderLayout.EAST);

//        JSlider slider = new JSlider(JSlider.HORIZONTAL,
//                0, 250, 120);
//        slider.setMajorTickSpacing(25);
//        slider.setMinorTickSpacing(5);
//        slider.setPaintTicks(true);
//        slider.setPaintLabels(true);
//        add(slider, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void refresh() {
        model.clear();
        for (int i = 0; i < spotPanel.getSpots().size(); i ++)
            model.addElement("Spot #" + (i + 1));
    }

    public void resetSelection() {
        spotsJList.setSelectedIndex(-1);
    }
}
