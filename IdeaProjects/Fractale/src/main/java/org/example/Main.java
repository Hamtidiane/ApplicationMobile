package org.example;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Créer une instance de JFrame
        JFrame frame = new JFrame("Ma première fenêtre JFrame");

        // Définir la taille de la fenêtre
        frame.setSize(400, 300);

        // Définir l'action de fermeture par défaut
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Rendre la fenêtre visible
        frame.setVisible(true);

    }
}
