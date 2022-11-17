package odomo;

import java.util.Random;

/**
 * Gestion de la partie Jardin.
 */
class Jardin {

    /**
     * Premier crénaux du jour.
     */
    static int[][] crénaux1;

    /**
     * Deuxième crénaux du jour.
     */
    static int[][] crénaux2;

    /**
     * Type de plante.
     */
    static String[] typePlante;

    /**
     * Les zones de plantations.
     */
    static String[] zone;

    static final int NB_ZONES = 8;

    /**
     * Intensité irrigation.
     */
    static int intensité;

    /**
     * Capacité d'irrigation.
     */
    static int capacité;

    /**
     * Liste de fleurs.
     */
    static String[] fleurs;

    /**
     * Liste de fruits.
     */
    static String[] fruits;

    /**
     * Liste légume.
     */
    static String[] légumes;

    /**
     * Circuit d'irrigation.
     */
    static int circuit;

    /**
     * Création de crénaux, de type de plante et de plante.
     */
    static void initialiser() {
        crénaux1 = new int[][]{{6, 9}, {6, 9}, {6, 22}, {6, 9}, {6, 9}, {7, 23}, {7, 23}};
        crénaux2 = new int[][]{{17, 22}, {17, 22}, {1, 0}, {17, 22}, {17, 22}, {1, 0}, {1, 0}};
        typePlante = new String[]{"fleur", "fruit", "légume"};
        fleurs = new String[]{"rose", "tulipe"};
        fruits = new String[]{"pomme", "bannane"};
        légumes = new String[]{"salade", "tomate"};
    }

    /**
     * Remplissage des zones.
     */
    static void initialisationZone() {
        Random random = new Random();
        int tp, plt;
        String type;
        // Plante 
        for (int i = 0; i < NB_ZONES; i++) {
            tp = random.nextInt(3 - 0 + 1) + 0;
            type = typePlante[tp];
            if (type.equals("fleur")) {
                plt = random.nextInt(3 - 0 + 1) + 0;
                zone[i] = fleurs[plt];
            }
            if (type.equals("fruit")) {
                plt = random.nextInt(3 - 0 + 1) + 0;
                zone[i] = fruits[plt];
            }
            if (type.equals("légume")) {
                plt = random.nextInt(3 - 0 + 1) + 0;
                zone[i] = légumes[plt];
            }
        }
        // Circuit
    }
    
    /**
     * Donne le type d'une plante.
     *
     * @param plante la plante donnée ex:rose
     * @return le type de plante
     */
    static String typePlante(String plante) {
        String typePlante = "";
        for (int i = 0; i < fruits.length; i++) {
            if (fleurs[i].equals(plante)) {
                typePlante = "fleurs";
            }
            if (fruits[i].equals(plante)) {
                typePlante = "fruit";
            }
            if (légumes[i].equals(plante)) {
                typePlante = "légumes";
            }
        }
        return typePlante;
    }

    /**
     * Donne le besoin en eau de chque type de plante.
     * 
     * @param type_plante le type de plante concernée 
     * @return la quantité d'eau nécéssaire
     */
    static int besoinPlante(String type_plante){
        Random random = new Random();
        int litres=0;
        //si fruits 2 et 3 litres
        if(type_plante.equals("fruit")){
            litres = random.nextInt(3 - 1 + 1) + 1;
        }
        //si légumes entre 0 et 1 litres 
        if(type_plante.equals("légume")){
            litres = random.nextInt(1 - 0 + 1) + 0;
        }
        //si fleurs entre 0 et 2 litres
        if(type_plante.equals("fleur")){
            litres = random.nextInt(2 - 0 + 1) + 0;
        }
        return litres;
    }
    
    /*
    static void arrosage(){
        //Chaud ==> beaucoup d'eau + 2 litres partout
        if(){
            
        }
        //Normale ==> quantité normale
        //Pluvieu ==> quantité null
    }*/
    

}
