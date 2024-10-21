package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class EpongeMenger extends JPanel {

    private int depth;

    public EpongeMenger(int depth) {
        this.depth = depth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // Taille initiale du cube
        int size = 243;
        int startX = getWidth() / 2 - size / 2;
        int startY = getHeight() / 2 - size / 2;

        // Dessiner l'éponge de Menger
        drawMengerSponge(g2d, depth, startX, startY, size);
    }

    private void drawMengerSponge(Graphics2D g2d, int depth, int x, int y, int size) {
        if (depth == 0) {
            // Dessiner un carré plein pour la profondeur 0
            g2d.fill(new Rectangle2D.Double(x, y, size, size));
        } else {
            int newSize = size / 3;

            // Parcourir les 9 positions
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Ne pas dessiner le cube central
                    if (i == 1 && j == 1) continue;

                    // Calculer les nouvelles coordonnées
                    int newX = x + i * newSize;
                    int newY = y + j * newSize;

                    // Appeler récursivement pour les cubes restants
                    drawMengerSponge(g2d, depth - 1, newX, newY, newSize);
                }
            }
        }
    }

    public static void main(String[] args) {
        int depth = 3; // Profondeur de l'éponge de Menger

        JFrame frame = new JFrame("Éponge de Menger");
        EpongeMenger mengerSponge = new EpongeMenger(depth);
        frame.add(mengerSponge);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
