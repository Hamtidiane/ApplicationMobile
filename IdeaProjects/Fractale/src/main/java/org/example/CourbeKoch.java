package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class CourbeKoch extends JPanel { // Hérite de JPanel

    private int depth;

    // Constructeur de la classe
    public CourbeKoch(int depth) {
        this.depth = depth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Appeler la méthode de la superclasse
        Graphics2D g2d = (Graphics2D) g; // Utiliser Graphics2D pour un meilleur rendu
        g2d.setColor(Color.BLUE); // Définir la couleur de dessin
        g2d.setStroke(new BasicStroke(2)); // Définir l'épaisseur du trait

        int translationX = 100;
        // Initialiser les points de la ligne
        Point2D p1 = new Point2D.Double(50,  200);
        Point2D p2 = new Point2D.Double(400,  200);

        // Dessiner la courbe de Koch
        drawKochCurve(g2d, depth, (int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
    }

    private void drawKochCurve(Graphics2D g2d, int depth, int x1, int y1, int x2, int y2) {
        if (depth == 0) {
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        } else {
            int deltaX = (x2 - x1) / 3;
            int deltaY = (y2 - y1) / 3;

            // Calculer les coordonnées pour le point A
            int xA = x1 + deltaX;
            int yA = y1 + deltaY;

            // Calculer les coordonnées pour le point C
            int xC = x2 - deltaX;
            int yC = y2 - deltaY;

            // Calculer les coordonnées pour le point B
            // Convertir 60 degrés en radians pour les calculs trigonométriques
            double angleRadians = Math.toRadians(60);
            int xB = (int) (xA + deltaX * Math.cos(angleRadians) - deltaY * Math.sin(angleRadians));
            int yB = (int) (yA + deltaX * Math.sin(angleRadians) + deltaY * Math.cos(angleRadians));


            // Récursion
            drawKochCurve(g2d, depth - 1, x1, y1, xA, yA);
            drawKochCurve(g2d, depth - 1, xA, yA, xB, yB);
            drawKochCurve(g2d, depth - 1, xB, yB, xC, yC);
            drawKochCurve(g2d, depth - 1, xC, yC, x2, y2);
        }
    }

    public static void main(String[] args) {
        int depth = 3; // Profondeur de la courbe de Koch

        JFrame frame = new JFrame("Courbe de Koch");
        CourbeKoch kochCurve = new CourbeKoch(depth); // Nom de l'instance modifié pour respecter les conventions
        frame.add(kochCurve);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
