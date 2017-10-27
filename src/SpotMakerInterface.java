// Created by Austin Patel
// 10/21/2017

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SpotMakerInterface extends JFrame {


    public static void main(String[] args) {
        new SpotMakerInterface();
    }

    public SpotMakerInterface() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("Parking Lot Space Detector");

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.setLayout(new BorderLayout());

        SpotPanel spotPanel = new SpotPanel();
        System.out.println(spotPanel.getWidth() + ", " + spotPanel.getHeight());
        spotPanel.setBounds(0,0, 1000, 800);
        add(spotPanel);
        layeredPane.add(spotPanel, BorderLayout.CENTER);

        try {
            ImageIcon image = new ImageIcon(ImageIO.read(new File("res/sample parking.png")));
//            add(new JLabel(image), BorderLayout.EAST);
            JLabel imageLabel = new JLabel(image);
            imageLabel.setVisible(true);
            imageLabel.setBounds(0,0, 1000, 800);
            add(imageLabel);
            layeredPane.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(new JButton("test"), BorderLayout.SOUTH);

        setVisible(true);
    }
}
