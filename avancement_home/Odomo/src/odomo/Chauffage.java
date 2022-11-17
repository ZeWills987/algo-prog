package odomo;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.Scanner;
import static odomo.Chauffage.creneauVide;

/**
 * Gestion de la partie Chauffage.
 */
class Chauffage {

    /**
     * Température souhaitée en mode économique.
     */
    static int temperEco;

    /**
     * Température souhaitée en mode normal.
     */
    static int temperNormal;

    /**
     * Premier créneau du jour en mode normal.
     */
    static int[][] creneau1;

    /**
     * Deuxième créneau du jour en mode normal.
     */
    static int[][] creneau2;

    /**
     * Initialiser les données de chauffage.
     */
    static void initialiser() {
        temperNormal = 19;
        temperEco = 16;
        creneau1 = new int[][]{{6, 9}, {6, 9}, {6, 22}, {6, 9}, {6, 9}, {7, 23}, {7, 23}};
        creneau2 = new int[][]{{17, 22}, {17, 22}, {1, 0}, {17, 22}, {17, 22}, {1, 0}, {1, 0}};
    }

    /**
     * Matrice des créneaux en mode normal, pour l'histogramme.
     *
     * @return la matrice des créneaux
     */
    static boolean[][] matriceCreneaux() {
        boolean[][] matrice = new boolean[8][24];
        for (int i = 0; i < matrice.length - 1; i++) {
            for (int y = 0; y < matrice[0].length; y++) {
                if (dansCreneauNormal(i, y)) {
                    matrice[i][y] = true;
                } else {
                    matrice[i][y] = false;
                }
            }

        }
        return matrice;
    }

    /**
     * Indique si une heure donnée (d'un jour donné) est dans un créneau en mode
     * normal.
     *
     * @param jour le jour concerné (0 = lundi)
     * @param heure l'heure concernée
     * @return true si l'heure est en mode normal, false si mode économique
     */
    static boolean dansCreneauNormal(int jour, int heure) {
        return (creneau1[jour][0] <= heure && heure <= creneau1[jour][1])
                || (creneau2[jour][0] <= heure && heure <= creneau2[jour][1]);
    }

    /**
     * Indique la température souhaitée sur un horaire donné.
     *
     * @param jour le jour considéré (0 = lundi)
     * @param heure l'heure considérée
     * @return la température souhaitée à cet horaire
     */
    static int temperatureSouhaitee(int jour, int heure) {
        int temp;
        if (dansCreneauNormal(jour, heure)) {
            temp = temperNormal;
        } else {
            temp = temperEco;
        }

        return temp;
    }

    /**
     * Procédure de saisie des créneaux de chauffage.numeroJour
     */
    static void saisieCreneaux() {
        System.out.println("Saisie des créneaux en mode normal d'un jour donné");
        System.out.println("  par exemple : mar;7;20 pour un seul créneau le mardi, de 7h à 20h");
        System.out.println("                jeu;6;8;18;21 pour deux créneaux le jeudi, de 6h à 8h et de 18h à 21h");
        Scanner sc = new Scanner(System.in);
        String saisie;
        boolean saisieCorrecte;
        do {
            System.out.print("Créneaux d'un jour : ");
            saisie = sc.next();
            saisieCorrecte = traitementSaisieCreneaux(saisie);
        } while (!saisieCorrecte);
    }

    /**
     * Traite la saisie de créneaux par l'utilisateur.
     *
     * @param saisie la saisie de l'utilisateur
     * @return true ssi la saisie a été correcte
     */
    static boolean traitementSaisieCreneaux(String saisie) {
        boolean correct = saisie != null;
        String[] champs = null;
        if (correct) {
            champs = saisie.split(";");
            correct &= champs.length == 3 || champs.length == 5;
            if (!correct) {
                System.err.println("Format incorrect : 3 ou 5 champs separes "
                        + "par des points-virgules sont attendus, "
                        + champs.length + " ont été saisis.");
            }
        }
        if (correct) {
            correct &= Odomo.numeroJour(champs[0]) >= 0;
            if (!correct) {
                System.err.println("Nom de jour incorrect : " + champs[0] + ".");
            }
        }
        int creneau1debut = -1;
        if (correct) {
            creneau1debut = heureCreneau(champs[1]);
            correct = creneau1debut >= 0;
        }
        int creneau1fin = -1;
        if (correct) {
            creneau1fin = heureCreneau(champs[2]);
            correct = creneau1fin >= 0;
        }
        if (correct) {
            correct &= (creneau1debut <= creneau1fin) || (creneau1debut == 1 && creneau1fin == 0);
            if (!correct) {
                System.err.println("Créneau incorrect : l'heure de début doit "
                        + "précéder (ou égaler) l'heure de fin "
                        + "(ou choisir le créneau 1h-0h pour un créneau vide).");
            }
        }
        int creneau2debut = -1;
        int creneau2fin = -1;
        if (correct && champs.length == 5) {
            creneau2debut = heureCreneau(champs[3]);
            correct = creneau2debut >= 0;
            if (correct) {
                creneau2fin = heureCreneau(champs[4]);
                correct = creneau2fin >= 0;
            }
            if (correct) {
                correct &= (creneau2debut <= creneau2fin) || (creneau2debut == 1 && creneau2fin == 0);
                if (!correct) {
                    System.err.println("Créneau incorrect : l'heure de début doit "
                            + "précéder (ou égaler) l'heure de fin "
                            + "(ou choisir le créneau 1h-0h pour un créneau vide).");
                }
            }
        }
        if (correct) {
            int numJour = Odomo.numeroJour(champs[0]);
            Chauffage.creneau1[numJour][0] = creneau1debut;
            Chauffage.creneau1[numJour][1] = creneau1fin;
            if (champs.length == 5) {
                Chauffage.creneau2[numJour][0] = creneau2debut;
                Chauffage.creneau2[numJour][1] = creneau2fin;
            } else {
                Chauffage.creneau2[numJour][0] = 1;
                Chauffage.creneau2[numJour][1] = 0;
            }
        }
        return correct;
    }

    /**
     * Récupère l'heure d'un créneau donné sous forme de chaîne.
     *
     * @param chaineHeure l'heure sous forme de chaîne
     * @return l'heure sous forme d'entier (-1 si incorrecte)
     */
    static int heureCreneau(String chaineHeure) {
        int heure;
        try {
            heure = Integer.parseInt(chaineHeure);
        } catch (NumberFormatException e) {
            System.err.println("L'heure de créneau n'est pas un entier : " + chaineHeure);
            heure = -1;
        }
        if (heure > 23) {
            System.err.println("L'heure doit être comprise entre 0 et 23 "
                    + "(inclus), au lieu de : " + chaineHeure + ".");
            heure = -1;
        }
        return heure;
    }

    /**
     * Vérifie si un créneau est vide.
     *
     * @param creneau le créneau en question
     * @return si vrai le créneau est vide
     */
    static boolean creneauVide(int[] creneau) {
        return creneau[0] == 1 && creneau[1] == 0;
    }

    /**
     * Vérifie si les créneaux sont fusionable.
     *
     * @param c1 le créneau concidérée
     * @param c2 le créneau concidérée
     * @return si les créneaux sont fusionable
     */
    static boolean creneauFusionnable(int[] c1, int[] c2) {
        boolean fusion = false;
        int min;
        int max;
        if (!creneauVide(c1) && creneauVide(c2)) {
            fusion = false;
            
        } else if (creneauVide(c1) && !creneauVide(c2)) {
            fusion = true;
        } else {
            if (min(c1[0], c1[1]) < min(c2[0], c2[1])) {
                max = max(c1[0], c1[1]);
                min = min(c2[0], c2[1]);
            } else {
                max = max(c2[0], c2[1]);
                min = min(c1[0], c1[1]);
            }
            if (max + 1 < min) {
                fusion = false;
            }
        }
        /*
        if (creneauVide(c1) && creneauVide(c2)) {
            fusion = false;
        }
        if (c2[1] + 1 < c2[0]) {
            fusion = false;
        }*/

        return fusion;
    }

    /**
     * Fusione les créneaux.
     *
     * @param c1 le créneau concidérée
     * @param c2 le créneau concidérée
     */
    static void fusionnerCreneau(int[] c1, int[] c2) {
        c1[0] = min(c1[0], c2[0]);
        c1[1] = max(c1[1], c2[1]);
        c2[0] = 1;
        c2[1] = 0;
    }

    /**
     * Fusion des créneaux qui s'intersectent (ou se jouxtent).
     */
    static void nettoyageCreneaux() {
        for (int i = 0; i < creneau1.length; i++) {
            if (creneauFusionnable(creneau1[i], creneau2[i])) {
                fusionnerCreneau(creneau1[i], creneau2[i]);
            }
        }
    }
}
