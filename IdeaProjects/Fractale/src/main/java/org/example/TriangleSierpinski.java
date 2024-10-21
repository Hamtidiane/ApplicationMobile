package org.example;

import javax.swing.*;
import java.awt.*;

public class TriangleSierpinski extends JPanel {

    private int depth;

    public TriangleSierpinski(int depth) {
        this.depth = depth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Coordonnées des sommets du triangle principal
        int width = getWidth();
        int height = getHeight();
        int[] xPoints = {width / 2, 50, width - 50};
        int[] yPoints = {50, height - 50, height - 50};

        // Dessiner le triangle de Sierpinski
        drawSierpinskiTriangle(g2d, depth, xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
    }

    private void drawSierpinskiTriangle(Graphics2D g2d, int depth, int x1, int y1, int x2, int y2, int x3, int y3) {
        if (depth == 0) {
            // Dessiner un triangle plein
            g2d.fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
        } else {
            // Calculer les milieux des côtés
            int x12 = (x1 + x2) / 2;
            int y12 = (y1 + y2) / 2;
            int x23 = (x2 + x3) / 2;
            int y23 = (y2 + y3) / 2;
            int x31 = (x3 + x1) / 2;
            int y31 = (y3 + y1) / 2;

            // Récursion pour les trois triangles restants
            drawSierpinskiTriangle(g2d, depth - 1, x1, y1, x12, y12, x31, y31);
            drawSierpinskiTriangle(g2d, depth - 1, x2, y2, x23, y23, x12, y12);
            drawSierpinskiTriangle(g2d, depth - 1, x3, y3, x31, y31, x23, y23);
        }
    }

    public static void main(String[] args) {
        int depth = 5; // Profondeur du triangle de Sierpinski

        JFrame frame = new JFrame("Triangle de Sierpinski");
        TriangleSierpinski sierpinskiTriangle = new TriangleSierpinski(depth);
        frame.add(sierpinskiTriangle);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
