package morpion;

import java.util.Scanner;

/**
 * Classe pour le jeu du morpion.
 */
class Morpion {

    /**
     * Scanner pour lire sur l'entrée standard.
     */
    static Scanner sc = new Scanner(System.in);

    /**
     * Fonction principale.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {

        Grille grille = new Grille(3);
        boolean gagne, fini;
        int nbCoups, joueur;
        int[] scores = {0, 0};

        afficherJeu(grille, scores);

        nbCoups = 0;
        joueur = 2;
        do {
            joueur = joueurSuivant(joueur);
            System.out.println("Joueur " + joueur + " (" + Grille.carac(joueur) + ")");
            Coordonnees coord = saisirCoordonnees(grille);
            poserJeton(coord, joueur, grille);
            mettreAJourScore(joueur, scores, coord, grille);
            nbCoups++;
            afficherJeu(grille, scores);
            gagne = estGagne(coord, grille);
            fini = estFini(nbCoups, grille.taille);

        } while (!gagne && !fini);

        if (gagne) {
            System.out.println("Victoire du joueur " + joueur
                    + ", qui marque " + scores[joueur - 1] + " points.");
        } else {
            System.out.println("Partie Nulle");
        }
    }

    /**
     * Récupérer des coordonnées saisies par le joueur.
     *
     * @param grille la grille du jeu
     * @return les coordonnées saisies
     */
    static Coordonnees saisirCoordonnees(Grille grille) {
        Coordonnees coord;
        boolean continuer;
        do {
            System.out.println("Entrez votre position (ligne puis colonne) :");
            int ligne = lireCoordonnee();
            int colonne = lireCoordonnee();
            coord = new Coordonnees(ligne, colonne);
            continuer = !Coordonnees.estDansPlateau(coord, grille.taille)
                    || !Grille.caseVide(grille, coord);
        } while (continuer);
        return coord;
    }

    /**
     * Lire une coordonnée saisie par l'utilisateur. Vous n'avez pas à
     * comprendre ce code.
     *
     * @return l'entier saisi
     */
    static int lireCoordonnee() {
        int entierSaisi = 0;
        boolean saisieCorrecte = false;
        do {
            try {
                entierSaisi = sc.nextInt();
                saisieCorrecte = (entierSaisi >= 0 && entierSaisi <= 2);
            } catch (java.util.InputMismatchException e) {
                System.err.println("Saisie incorrecte, saisissez un entier.");
                sc.next();
            }
        } while (!saisieCorrecte);
        return entierSaisi;
    }

    /**
     * Numéro du joueur suivant.
     *
     * @param joueur un numéro de joueur
     * @return le numéro du joueur suivant
     */
    static int joueurSuivant(int joueur) {
        return (joueur % 2 + 1);
    }

    /**
     * Poser un jeton sur une case.
     *
     * @param coord coordonnées de la case
     * @param joueur numéro du joueur
     * @param grille grille du jeu
     */
    static void poserJeton(Coordonnees coord, int joueur, Grille grille) {
        Grille.changerContenuCase(grille, coord, joueur);
    }

    /**
     * Indique si la partie est gagnée.
     *
     * @param coord coordonnées de la case jouée en dernier
     * @param grille grille du jeu
     * @return vrai ssi le joueur venant de jouer a gagné
     */
    static boolean estGagne(Coordonnees coord, Grille grille) {
        int lig = coord.ligne;
        int col = coord.colonne;
        return (Grille.isLigneComplete(grille, lig)
                || Grille.isColonneComplete(grille, col)
                || Grille.diagonaleComplete(grille, coord));
    }

    /**
     * Indique si la grille est remplie, c'est-à-dire si le nombre de coups est
     * égal au nombre de cases.
     *
     * @param n nombre de coups joués
     * @param taille taille de la grille
     * @return vrai ssi la grille est remplie
     */
    static boolean estFini(int n, int taille) {
        return (n == taille * taille);
    }

    /**
     * Affichage du jeu : la grille et les scores.
     *
     * @param grille la grille du jeu
     * @param scores les scores des joueurs
     */
    static void afficherJeu(Grille grille, int[] scores) {
        Grille.afficher(grille);
        System.out.println("Scores : [ Joueur 1 (X) = " + scores[0]
                + " points ] [ Joueur 2 (O) = " + scores[1] + " points ]");
    }

    /**
     * Mise à jour des scores, suite à l'action d'un joueur.
     *
     * @param joueur le joueur venant de jouer
     * @param scores tableau des scores des joueurs
     * @param coord coordonnées de la case jouée par le joueur
     * @param grille grille du jeu
     */
    static void mettreAJourScore(int joueur, int[] scores, Coordonnees coord,
            Grille grille) {
        scores[joueur - 1] += scoreCase(coord, grille);
    }

    /**
     * Score d'une case, lorsqu'un joueur vient de jouer sur cette case,
     * c'est-à-dire 0 si la case est vide, sinon 1 + nbVoisinsAmis -
     * nbVoisinsEnnemis.
     *
     * @param c les coordonnées de la case
     * @param grille la grille de jeu
     * @return le score de cette case
     */
    static int scoreCase(Coordonnees c, Grille grille) {
        int score = 0;
        if (!Grille.caseVide(grille, c)) {
            int numJoueur = Grille.contenuCase(grille, c);
            int autreJoueur = joueurSuivant(numJoueur);
            score = 1
                    + Grille.nbVoisinsAppartenantAuJoueur(grille, numJoueur, c)
                    - Grille.nbVoisinsAppartenantAuJoueur(grille, autreJoueur, c);
        }
        return score;
    }
}
