package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Labyrinthe extends JPanel {
    private final int largeur;
    private final int hauteur;
    private final int[][] grille;
    private final Random random = new Random();
    private static final int TAILLE_CELLULE = 20; // Taille de chaque cellule du labyrinthe

    // Coordonnées de l'entrée et de la sortie
    private final int entreeX = 0;
    private final int entreeY = 0;
    private final int sortieX;
    private final int sortieY;

    public Labyrinthe(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        grille = new int[largeur][hauteur];
        sortieX = largeur - 1; // La sortie est en bas à droite
        sortieY = hauteur - 1;
        genererLabyrinthe(entreeX, entreeY);
        creerEntreeEtSortie();
    }

    // Génération du labyrinthe avec l'algorithme DFS
    private void genererLabyrinthe(int x, int y) {
        int[] directions = {0, 1, 2, 3};
        shuffle(directions);

        for (int direction : directions) {
            int nx = x, ny = y;

            switch (direction) {
                case 0 -> ny = y - 2; // Haut
                case 1 -> ny = y + 2; // Bas
                case 2 -> nx = x - 2; // Gauche
                case 3 -> nx = x + 2; // Droite
            }

            if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur && grille[nx][ny] == 0) {
                grille[nx][ny] = 1; // Marquer le chemin
                grille[(x + nx) / 2][(y + ny) / 2] = 1; // Ouvrir le mur entre les deux
                genererLabyrinthe(nx, ny);
            }
        }
    }

    // Mélange aléatoire des directions
    private void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Création de l'entrée et de la sortie
    private void creerEntreeEtSortie() {
        grille[entreeX][entreeY] = 1; // Entrée
        grille[sortieX][sortieY] = 1; // Sortie
    }

    // Méthode pour dessiner le labyrinthe
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (grille[x][y] == 1) {
                    g.setColor(Color.WHITE); // Chemin
                } else {
                    g.setColor(Color.BLACK); // Mur
                }
                g.fillRect(x * TAILLE_CELLULE, y * TAILLE_CELLULE, TAILLE_CELLULE, TAILLE_CELLULE);
            }
        }

        // Dessiner l'entrée en vert
        g.setColor(Color.GREEN);
        g.fillRect(entreeX * TAILLE_CELLULE, entreeY * TAILLE_CELLULE, TAILLE_CELLULE, TAILLE_CELLULE);

        // Dessiner la sortie en rouge
        g.setColor(Color.RED);
        g.fillRect(sortieX * TAILLE_CELLULE, sortieY * TAILLE_CELLULE, TAILLE_CELLULE, TAILLE_CELLULE);
    }

    // Dimensions du labyrinthe pour le JFrame
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(largeur * TAILLE_CELLULE, hauteur * TAILLE_CELLULE);
    }

    public static void main(String[] args) {
        // Création du JFrame
        JFrame frame = new JFrame("Labyrinthe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Taille du labyrinthe
        int largeur = 21;
        int hauteur = 21;

        // Création du labyrinthe
        Labyrinthe labyrinthe = new Labyrinthe(largeur, hauteur);

        // Ajout du labyrinthe au JFrame
        frame.add(labyrinthe);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
