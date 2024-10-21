package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class FloconKoch extends JPanel { // Hérite de JPanel

    private double depth;

    // Constructeur de la classe
    public FloconKoch(double depth) {
        this.depth = depth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Appeler la méthode de la superclasse
        Graphics2D g2d = (Graphics2D) g; // Utiliser Graphics2D pour un meilleur rendu
        g2d.setColor(Color.BLUE); // Définir la couleur de dessin
        g2d.setStroke(new BasicStroke(1)); // Définir l'épaisseur du trait

        double height = getHeight();
        double width = getWidth();

        // Calculer les points du triangle équilatéral
        Point2D p1 = new Point2D.Double((double) width / 2 - 150, (double) height / 2 + 100);
        Point2D p2 = new Point2D.Double((double) width / 2 + 150, (double) height / 2 + 100);
        Point2D p3 = new Point2D.Double((double) width / 2, (double) height / 2 - 173);

        // Dessiner les trois côtés du flocon de Koch
        drawKochCurve(g2d, depth, (double) p1.getX(), (double) p1.getY(), (double) p2.getX(), (int) p2.getY());
        drawKochCurve(g2d, depth, (double) p2.getX(), (double) p2.getY(), (double) p3.getX(), (int) p3.getY());
        drawKochCurve(g2d, depth, (double) p3.getX(), (double) p3.getY(), (double) p1.getX(), (int) p1.getY());
    }

    private void drawKochCurve(Graphics2D g2d, double depth, double x1, double y1, double x2, double y2) {
        if (depth == 0) {
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        } else {
            double deltaX = (x2 - x1) / 3;
            double deltaY = (y2 - y1) / 3;

            // Calculer les coordonnées pour le point A
            double xA = x1 + deltaX;
            double yA = y1 + deltaY;

            // Calculer les coordonnées pour le point C
            double xC = x2 - deltaX;
            double yC = y2 - deltaY;

            // Calculer les coordonnées pour le point B
            double angleRadians = Math.toRadians(60);
            double xB = (double) (xA + deltaX * Math.cos(angleRadians) - deltaY * Math.sin(angleRadians));
            double yB = (double) (yA + deltaX * Math.sin(angleRadians) + deltaY * Math.cos(angleRadians));

            // Récursion
            drawKochCurve(g2d, depth - 1, x1, y1, xA, yA);
            drawKochCurve(g2d, depth - 1, xA, yA, xB, yB);
            drawKochCurve(g2d, depth - 1, xB, yB, xC, yC);
            drawKochCurve(g2d, depth - 1, xC, yC, x2, y2);
        }
    }

    public static void main(String[] args) {
        int depth = 1; // Profondeur de la courbe de Koch

        JFrame frame = new JFrame("Flocon de Koch");
        FloconKoch kochFlake = new FloconKoch(depth); // Nom de l'instance modifié pour respecter les conventions
        frame.add(kochFlake);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
