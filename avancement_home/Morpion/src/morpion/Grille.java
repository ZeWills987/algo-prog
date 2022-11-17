package morpion;

/**
 * Grille (carrée) du jeu de morpion.
 */
class Grille {

    /**
     * Taille d'un côté de la grille.
     */
    int taille;

    /**
     * Contenu de la grille.
     */
    int[][] contenu;

    /**
     * Constructeur.
     *
     * @param laTaille taille de la grille
     */
    Grille(int laTaille) {
        taille = laTaille;
        contenu = new int[taille][taille];
        // mettre 0 dans toutes les cases
        int lig, col;
        for (lig = 0; lig < taille; lig++) {
            for (col = 0; col < taille; col++) {
                contenu[lig][col] = 0;
            }
        }
    }

    /**
     * Récupérer le contenu d'une case, depuis ses coordonnées.
     *
     * @param grille la grille dont on veut récupérer un contenu
     * @param c coordonnées de la case
     * @return contenu de la case
     */
    static int contenuCase(Grille grille, Coordonnees c) {
        return grille.contenu[c.ligne][c.colonne];
    }

    /**
     * Changer le contenu d'une case.
     *
     * @param g la grille dont on veut changer un contenu de case
     * @param c coordonnées de la case
     * @param nouvelleValeur nouvelle valeur de la case
     */
    static void changerContenuCase(Grille g, Coordonnees c, int nouvelleValeur) {
        g.contenu[c.ligne][c.colonne] = nouvelleValeur;
    }

    /**
     * Changer tout le contenu d'une grille.
     *
     * @param g la grille dont on veut changer tout le contenu
     * @param nouveauContenu nouveau contenu de la grille
     */
    static void changerContenuGrille(Grille g, int[][] nouveauContenu) {
        g.contenu = nouveauContenu;
    }
    
    /**
     * Indique si une case est vide.
     *
     * @param g la grille considérée
     * @param coord les coordonnées de la case
     * @return vrai ssi la case est vide
     */
    static boolean caseVide(Grille g, Coordonnees coord) {
        return contenuCase(g, coord) == 0;
    }

    /**
     * Nombre de cases voisines d'une case, et appartenant à un joueur.
     *
     * @param g la grille considérée
     * @param joueur le joueur considéré
     * @param c les coordonnées de la case dont on considère les voisins
     * @return le nombre de cases voisines appartenant au joueur
     */
    static int nbVoisinsAppartenantAuJoueur(Grille g, int joueur, Coordonnees c) {
        int nbVoisAmis = 0;
        if(contenuCase(g,c)==joueur){
            Coordonnees[] tab = Coordonnees.voisines(c,g.taille);
            for (int i =0;i<tab.length;i++){
                if(contenuCase(g,tab[i])==joueur){
                    nbVoisAmis+=1;
                }
            }
        }
        return nbVoisAmis;
    }

    static boolean isLigneComplete(Grille g, int lig) {
        Coordonnees premiereCaseLigne = new Coordonnees(lig, 0);
        boolean ligneComplete = true;
        int col = 1;
        while (ligneComplete && col < g.taille) {
            Coordonnees caseLigne = new Coordonnees(lig, col);
            if (contenuCase(g, caseLigne) != contenuCase(g, premiereCaseLigne)
                    || caseVide(g, premiereCaseLigne)
                    || caseVide(g, caseLigne)) {
                ligneComplete = false;
            }
            col++;
        }
        return ligneComplete;
    }

    static boolean isColonneComplete(Grille g, int col) {
        Coordonnees premiereCaseCol = new Coordonnees(0, col);
        boolean colonneComplete = true;
        int lig = 1;
        while (colonneComplete && lig < g.taille) {
            Coordonnees caseCol = new Coordonnees(lig, col);
            if (contenuCase(g, caseCol) != contenuCase(g, premiereCaseCol)
                    || caseVide(g, caseCol)
                    || caseVide(g, premiereCaseCol)) {
                colonneComplete = false;
            }
            lig++;
        }
        return colonneComplete;
    }

    static boolean isDiagonaleD(Grille g) {
        Coordonnees hautGauche = new Coordonnees(0, 0);
        boolean diag = true;
        int i = 1;
        while (diag && i < g.taille) {
            Coordonnees caseDiag = new Coordonnees(i, i);
            if (contenuCase(g, caseDiag) != contenuCase(g, hautGauche)
                    || caseVide(g, caseDiag)) {
                diag = false;
            }
            i++;
        }
        return diag;
    }

    static boolean isDiagonaleI(Grille g) {
        Coordonnees basGauche = new Coordonnees(0, g.taille - 1);
        boolean diag = true;
        int i = 1;
        while (diag && i < g.taille) {
            Coordonnees caseDiag = new Coordonnees(i, g.taille - i - 1);
            if (contenuCase(g, caseDiag) != contenuCase(g, basGauche)
                    || caseVide(g, caseDiag)) {
                diag = false;
            }
            i++;
        }
        return diag;
    }

    static boolean diagonaleComplete(Grille g, Coordonnees coord) {
        int lig = coord.ligne;
        int col = coord.colonne;
        if (col == lig && col != 0 && col != g.taille - 1) {
            return (isDiagonaleD(g) && isDiagonaleI(g));
        } else if (col == lig) {
            return isDiagonaleD(g); // test diagonale directe
        } else if (col == g.taille - 1 - lig) {
            return isDiagonaleI(g); // test diagonale indirecte
        } else {
            return false; // lig,col pas sur une diagonale
        }
    }

    /**
     * Afficher une ligne de séparation.
     */
    static void afficherLigneSep(Grille g) {
        System.out.print(" ");
        for (int i = 0; i < g.taille - 1; i++) {
            System.out.print("---+");
        }
        System.out.println("---");
    }

    /**
     * Afficher une ligne de la grille.
     * 
     * @param lig numéro de la ligne à afficher
     */
    static void afficherLigne(Grille g, int lig) {
        System.out.print(" ");
        for (int col = 0; col < g.taille - 1; col++) {
            System.out.print(" " + carac(g.contenu[lig][col]) + " |");
        }
        System.out.println(" " + carac(g.contenu[lig][g.taille - 1]));
    }

    /**
     * Afficher toute la grille.
     */
    static void afficher(Grille g) {
        System.out.println(" ");
        for (int lig = 0; lig < g.taille - 1; lig++) {
            afficherLigne(g, lig);
            afficherLigneSep(g);
        }
        afficherLigne(g, g.taille - 1);
        System.out.println("");
    }

    /**
     * Caractère utilisé pour afficher le contenu d'une case.
     *
     * @param n le contenu de la grille dans cette case
     * @return le caractère à afficher pour cette case
     */
    static char carac(int n) {
        char c;
        switch (n) {
            case 0:
                c = ' ';
                break;
            case 1:
                c = 'X';
                break;
            case 2:
                c = 'O';
                break;
            case 3:
                c = '*';
                break;
            default:
                c = 'E'; // erreur = carac pas dispo
                break;
        }
        return c;
    }
}
