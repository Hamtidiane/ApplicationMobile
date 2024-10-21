package org.example;
import javax.swing.*;
import java.awt.*;

public class CourbeDragon extends JPanel {
    private int depth; // Profondeur de récursion pour la courbe

    public CourbeDragon(int depth) {
        this.depth = depth; // Initialiser la profondeur
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Appeler la méthode de la superclasse
        Graphics2D g2d = (Graphics2D) g; // Utiliser Graphics2D pour un meilleur rendu
        g2d.setColor(Color.RED); // Définir la couleur de dessin
        g2d.setStroke(new BasicStroke(2)); // Définir l'épaisseur du trait

        // Calculer les points de départ et de fin
        int width = getWidth(); // Largeur du panneau
        int height = getHeight(); // Hauteur du panneau
        int x1 = width / 4; // Point de départ x
        int y1 = height / 2; // Point de départ y
        int x2 = 3 * width / 4; // Point d'arrivée x
        int y2 = height / 2; // Point d'arrivée y

        // Dessiner la courbe du dragon
        drawDragonCurve(g2d, depth, x1, y1, x2, y2, true);
    }

    // Méthode récursive pour dessiner la courbe du dragon
    private void drawDragonCurve(Graphics2D g2d, int depth, int x1, int y1, int x2, int y2, boolean left) {
        if (depth == 0) {
            g2d.drawLine(x1, y1, x2, y2); // Dessiner une ligne si profondeur 0
        } else {
            int newX, newY;
            if (left){
                newX = (x1 + x2 +y1 - y2) / 2;
                newY = (x2 - x1 + y1 +y2) / 2;
            }else{
                newX = (x1 + x2 - y1 + y2) / 2;
                newY = (x1 - x2 + y1 +y2) / 2;
            }

            // Appel récursif pour les deux segments
            drawDragonCurve(g2d, depth - 1, x1, y1, newX, newY, true);
            drawDragonCurve(g2d, depth - 1, newX, newY, x2, y2, false);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Courbe du Dragon Fractale");
        int depth = 17; // Profondeur de la courbe

        // Créer une instance de CourbeDragon, qui est un JPanel
        CourbeDragon dragonCurvePanel = new CourbeDragon(depth);
        frame.add(dragonCurvePanel); // Ajouter le JPanel au JFrame

        // Configurer le JFrame
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centrer le JFrame
        frame.setVisible(true); // Afficher le JFrame
    }
}
