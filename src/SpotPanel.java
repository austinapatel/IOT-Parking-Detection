// Created by Austin Patel
// 10/21/2017

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SpotPanel extends JComponent {

    private int startX, startY, curX, curY;
    private ArrayList<ParkingSpot> spots = new ArrayList<>();

    public SpotPanel() {
        setLayout(new BorderLayout());

        initMouseListeners();
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
                System.out.println("mousePressed " + startX + ", " + startY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouseReleased");
                spots.add(new ParkingSpot(startX, startY, e.getX(), e.getY()));
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
                System.out.println("mouseDragged");
                curX = e.getX();
                curY = e.getY();
            }
        });
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g = (Graphics2D) graphics;
        System.out.println("paintComponent");

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(4));
        ParkingSpot curSpot = new ParkingSpot(startX, startY, curX, curY);
        g.drawRect(curSpot.getX(), curSpot.getY(), curSpot.getW(), curSpot.getH());

        for (ParkingSpot spot : spots)
            g.drawRect(spot.getX(), spot.getY(), spot.getW(), spot.getH());
    }
}
