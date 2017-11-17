// Created by Austin Patel
// 10/21/2017

package com.amazonaws.samples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class SpotPanel extends JPanel {

    private int startX, startY, curX, curY;
    private ArrayList<ParkingSpot> spots = new ArrayList<>();
    private BufferedImage parkingImage, parkingImage_gray;
    private boolean valid;
    private SpotMakerInterface frame;
    private int selectedIndex = -1;

    public SpotPanel(SpotMakerInterface frame) {
        this.frame = frame;

        setLayout(new BorderLayout());
        int threshold = 120;

        parkingImage = S3Sample.getImage();
        parkingImage_gray = ImageOperations.Threshold(parkingImage, threshold);

        Timer timer = new Timer();

        SpotPanel outside = this;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                parkingImage = S3Sample.getImage();
                parkingImage_gray = ImageOperations.Threshold(parkingImage, threshold);
                outside.repaint();
                System.out.println("pulled new image");
            }
        }, 5*1000, 5*1000);

        initMouseListeners();
    }

    public void deleteSpot(int index) {
        spots.remove(index);
        frame.refresh();
        repaint();
    }

    private void initMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                valid = true;

                frame.resetSelection();
                selectedIndex = -1;

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (e.getX() <= parkingImage.getWidth() && e.getY() <= getHeight() &&
                        e.getX() >= 0 && e.getY() >= 0) {
                    spots.add(new ParkingSpot(startX, startY, e.getX(), e.getY()));
                    frame.refresh();
                }

                valid = false;
                repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                repaint();
                curX = e.getX();
                curY = e.getY();
            }
        });
    }

    public void update() {
        repaint();
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        repaint();
    }

    public int getWidth() {
        return parkingImage.getWidth(null) * 2;
    }

    public int getHeight() {
        return parkingImage.getHeight(null);
    }

    public ArrayList<ParkingSpot> getSpots() {
        return spots;
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;

        g.drawImage(parkingImage, 0, 0, null);
        System.out.println("Grey " + parkingImage_gray.getWidth());
//        g.drawImage(parkingImage_gray, 400, 0, null);

        g.setStroke(new BasicStroke(4));

        ParkingSpot curSpot = new ParkingSpot(startX, startY, curX, curY);
        if (valid) {
            g.setColor(Color.MAGENTA);
            g.drawRect(curSpot.getX(), curSpot.getY(), curSpot.getW(), curSpot.getH());
        }

        for (int i = 0; i < spots.size(); i++) {
            ParkingSpot spot = spots.get(i);

            if (i == selectedIndex)
                g.setColor(Color.BLUE);
            else {
                int total_space = spot.getW() * spot.getH();
                int filled_area = 0;

                System.out.println(spot.getX() + ", " + spot.getY() + "    " + spot.getW() + ", " + spot.getH());

                for (int w = 0; w < spot.getW(); w++)
                    for (int h = 0; h < spot.getH(); h++) {
                        int rgb = parkingImage_gray.getRGB(spot.getX() + w, spot.getY() + h);
                        int red = (rgb >> 16) & 0xFF;
                        int green = (rgb >> 8) & 0xFF;
                        int blue = (rgb & 0xFF);
                        int gray = (red + green + blue) / 3;

//                        System.out.println(gray);

                        if (gray == 0)
                            filled_area += 1;
                    }

                double percent_filled = (double) filled_area / (double) total_space;

                System.out.println(percent_filled);

                if (percent_filled > 0.2)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(Color.RED);
            }

            g.drawRect(spot.getX(), spot.getY(), spot.getW(), spot.getH());
        }
    }
}
