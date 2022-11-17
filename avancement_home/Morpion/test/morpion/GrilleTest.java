/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package morpion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author William Tchang
 */
public class GrilleTest {

    @Test
    public void testNbVoisinsAppartenantAuJoueur() {
        int[][] contenu = {{2, 1, 1}, {2, 0, 0}, {2, 0, 0}};
        Grille grille = new Grille(3);
        Grille.changerContenuGrille(grille, contenu);
        assertEquals(2, Grille.nbVoisinsAppartenantAuJoueur(grille, 1, new Coordonnees(1, 1)));
        assertEquals(3, Grille.nbVoisinsAppartenantAuJoueur(grille, 2, new Coordonnees(1, 1)));
        assertEquals(0, Grille.nbVoisinsAppartenantAuJoueur(grille, 1, new Coordonnees(2, 2)));
        assertEquals(1, Grille.nbVoisinsAppartenantAuJoueur(grille, 2, new Coordonnees(0, 0)));
    }
}
