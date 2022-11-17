package morpion;

import java.util.Arrays;
import static morpion.Direction.tout_directions;

/**
 * Coordonnées d'une case du plateau.
 */
class Coordonnees {

    /**
     * Numéro de la ligne.
     */
    int ligne;

    /**
     * Numéro de la colonne.
     */
    int colonne;

    /**
     * Constructeur prenant les numéros de ligne/colonne en paramètre.
     *
     * @param numLigne numéro de la ligne
     * @param numColonne numéro de la colonne
     */
    Coordonnees(int numLigne, int numColonne) {
        ligne = numLigne;
        colonne = numColonne;
    }

    /**
     * Renvoie les coordonnées de la case suivante, en suivant une direction
     * donnée.
     *
     * @param d la direction à suivre
     * @return les coordonnées de la case suivante
     */
    static Coordonnees suivante(Coordonnees c, Direction d) {
        return new Coordonnees(c.ligne - 1 + Direction.mvtVertic(d),
                c.colonne - 1 + Direction.mvtHoriz(d));
    }

    /**
     * Indique si ces coordonnées sont dans le plateau.
     *
     * @param coord coordonnées à tester
     * @param taille taille du plateau (carré)
     * @return vrai ssi ces coordonnées sont dans le plateau
     */
    static boolean estDansPlateau(Coordonnees coord, int taille) {
        return coord.colonne < taille && coord.colonne >= 0 && coord.ligne < taille && coord.ligne >= 0;
    }

    /**
     * Retourne si c'est une coordonnée du coin du tableau.
     *
     * @param c la coordonnées donnée
     * @param taille la taille donnée
     * @return si c'est coordonnées du coin du tableau
     */
    static boolean caseCoin(Coordonnees c, int taille) {
        boolean good = false;
        taille = taille - 1;
        Coordonnees[] tab = {new Coordonnees(0, 0), new Coordonnees(0, taille),
            new Coordonnees(taille, 0), new Coordonnees(taille, taille)};
        for (Coordonnees tab1 : tab) {
            if (tab1.equals(c) && good == false) {
                good = true;
            }
        }
        return good;
    }

    /**
     * Retourne si une coordonnées est au bord d'un tableau et pas un côté.
     *
     * @param c la coordonnées donnée
     * @param taille la taille donnée
     * @return si c'est coordonnées du bord du tableau
     */
    static boolean caseBord(Coordonnees c, int taille) {
        boolean good = false;
        taille = taille - 1;
        if ((c.ligne > 0 && c.ligne < taille && c.colonne == 0)
                || (c.colonne > 0 && c.colonne < taille && c.ligne == 0)
                || (c.ligne > 0 && c.ligne < taille && c.colonne == taille)
                || (c.colonne > 0 && c.colonne < taille && c.ligne == taille)) {
            good = true;
        }
        return good;
    }

    /**
     * Retourne les coordonnées de toutes les cases voisines.
     *
     * @param coord coordonnées de la case considérée
     * @param taille taille du plateau (carré)
     * @return les coordonnées de toutes les cases voisines
     */
    static Coordonnees[] voisines(Coordonnees coord, int taille) {
        Coordonnees[] voisines = new Coordonnees[8];
        Direction[] direct = tout_directions();
        int nbVoisines = 0;
        if (caseCoin(coord, taille)) {
            nbVoisines = 3;
        }
        else if (caseBord(coord, taille)) {
            nbVoisines = 5;
        } else {
            nbVoisines = 8;
        }
        for (int i = 0; i <= nbVoisines; i++) {
            for (Direction direct1 : direct) {
                Coordonnees après = suivante(coord, direct1);
                if (estDansPlateau(après, taille)) {
                    voisines[i] = suivante(coord, direct1);
                }
            }
        }
        return Arrays.copyOf(voisines, nbVoisines);
    }

    @Override
    public String toString() {
        return "(" + ligne + "," + colonne + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordonnees other = (Coordonnees) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.ligne;
        hash = 53 * hash + this.colonne;
        return hash;
    }

}
