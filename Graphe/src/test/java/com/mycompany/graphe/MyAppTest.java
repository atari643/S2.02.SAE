/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
/**
 * Paquet com.mycompany.graphe dans lequel se retrouve la classe qui va etre testé, ici MyApp.
 */
package com.mycompany.graphe;

import com.mycompany.graphe.MyApp;
import io.jbotsim.core.Link;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Test de la classe MyApp et de ces différentes méthodes.
 *
 * @author fchapoulliep
 */
public class MyAppTest {

    /**
     * Test de la méthode ActionResetLink, de la classe MyApp, qui change la
     * largeur de toutes les arêtes du graphe à 1.
     */
    @Test
    public void testActionResetLink() {
        // On crée une topologie
        Topology topoClear = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet2 = new Node();
        Node sommet3 = new Node();
        Node sommet4 = new Node();
        Node sommet5 = new Node();
        Node sommet6 = new Node();
        Node sommet7 = new Node();

        // On place ces sommets dans le graphe
        topoClear.addNode(100, 100, sommet0);
        topoClear.addNode(500, 100, sommet1);
        topoClear.addNode(600, 100, sommet2);
        topoClear.addNode(700, 100, sommet3);
        topoClear.addNode(800, 100, sommet4);
        topoClear.addNode(900, 100, sommet5);
        topoClear.addNode(1000, 100, sommet6);
        topoClear.addNode(1100, 100, sommet7);

        // On crée des liens entre différents sommets
        topoClear.addLink(new Link(sommet0, sommet1));
        topoClear.addLink(new Link(sommet0, sommet3));
        topoClear.addLink(new Link(sommet0, sommet5));
        topoClear.addLink(new Link(sommet0, sommet7));
        topoClear.addLink(new Link(sommet0, sommet7));
        topoClear.addLink(new Link(sommet2, sommet6));

        // On change la largeur des arêtes reliant un sommet à un autre à 4, pour vérifier ensuite si la methode ActionResetLink fonctionne bien
        for (Link l : topoClear.getLinks()) {
            l.setWidth(4);
        }

        // On verifie maintenant si la largeur des nodes est remis a 1 en utilisant la methode ActionResetLink
        // Avant cela on creer une nouvelle application et on lui attribue la topologie réalisée pour utiliser la méthode ActionResetLink()
        MyApp m = new MyApp();
        m.tp = topoClear;
        m.actionResetLink();

        // On vérifie si la largeur des arêtes est maintenant de 1
        for (Link l : topoClear.getLinks()) {
            assertEquals(1, (int) l.getWidth());
        }
    }

    /**
     * Test de la methode breadthFirstSearch, de la classe MyApp, qui effectue
     * le parcours en largeur d'un graphe.
     */
    @Test
    public void testBreadthFirstSearch() {
        // On crée une topologie
        Topology topBreadthFirstSearch = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet2 = new Node();
        Node sommet3 = new Node();
        Node sommet4 = new Node();
        Node sommet5 = new Node();
        Node sommet6 = new Node();
        Node sommet7 = new Node();
        Node sommet8 = new Node();
        Node sommet9 = new Node();
        Node sommet10 = new Node();
        Node sommet11 = new Node();
        Node sommet12 = new Node();

        // On empeche la creation automatique de lien entre les sommets que l'on va crée juste après.
        topBreadthFirstSearch.setCommunicationRange(0);

        // On place ces sommets dans le graphe
        topBreadthFirstSearch.addNode(100, 100, sommet0);
        topBreadthFirstSearch.addNode(500, 250, sommet1);
        topBreadthFirstSearch.addNode(600, 225, sommet2);
        topBreadthFirstSearch.addNode(700, 124, sommet3);
        topBreadthFirstSearch.addNode(800, 125, sommet4);
        topBreadthFirstSearch.addNode(900, 212, sommet5);
        topBreadthFirstSearch.addNode(1000, 374, sommet6);
        topBreadthFirstSearch.addNode(1100, 385, sommet7);
        topBreadthFirstSearch.addNode(1200, 218, sommet8);
        topBreadthFirstSearch.addNode(1300, 1002, sommet9);
        topBreadthFirstSearch.addNode(1400, 804, sommet10);
        topBreadthFirstSearch.addNode(1500, 907, sommet11);
        topBreadthFirstSearch.addNode(1600, 865, sommet12);

        // On crée des liens entre différents sommets qui menent vers notre destination, ici cela sera sommet6.
        topBreadthFirstSearch.addLink(new Link(sommet0, sommet3));
        topBreadthFirstSearch.addLink(new Link(sommet3, sommet5));
        topBreadthFirstSearch.addLink(new Link(sommet5, sommet2));
        topBreadthFirstSearch.addLink(new Link(sommet2, sommet6));
        topBreadthFirstSearch.addLink(new Link(sommet0, sommet7));
        topBreadthFirstSearch.addLink(new Link(sommet7, sommet8));
        topBreadthFirstSearch.addLink(new Link(sommet8, sommet9));
        topBreadthFirstSearch.addLink(new Link(sommet10, sommet11));
        topBreadthFirstSearch.addLink(new Link(sommet11, sommet12));

        // On affiche les liens entre les différents sommets pour verifier si nos modifications ont bien été prise en compte.
        for (Link l : topBreadthFirstSearch.getLinks()) {
            System.out.println(l);
        }

        // On crée une nouvelle instance MyApp dans laquelle on associe sa topologie, une source et une destination.
        MyApp monAppli = new MyApp();
        monAppli.tp = topBreadthFirstSearch;
        monAppli.source = sommet0;
        monAppli.destination = sommet6;

        // Je crée un tableau associatif de (sommet, sommet) en utilisant la méthode breadthFirstSearch.
        Map<Node, Node> laListe = monAppli.breadthFirstSearch(topBreadthFirstSearch, sommet0);

        // Je crée un tableau associatif ordonnée dans lequel j'ajoute manuellement les sommets et leurs predecesseurs.
        // La clé est le successeur, la valeur son predecesseur.
        // je comparerais alors ce taleau associatif avec notre methode.
        Map<Node, Node> aTester = new HashMap<>();
        aTester.put(sommet0, sommet0);
        aTester.put(sommet1, null);
        aTester.put(sommet2, sommet5);
        aTester.put(sommet3, sommet0);
        aTester.put(sommet4, null);
        aTester.put(sommet5, sommet3);
        aTester.put(sommet6, sommet2);
        aTester.put(sommet7, sommet0);
        aTester.put(sommet8, sommet7);
        aTester.put(sommet9, sommet8);
        aTester.put(sommet10, null);
        aTester.put(sommet11, null);
        aTester.put(sommet12, null);

        // On compare maintenant les deux listes pour verifier si la liste que j'ai réalisé manuellement donne la meme chose que la liste issue de la méthode.
        assertEquals(laListe, aTester);

        // On essaye avec un nouveau graphe, pour cela on vide la topologie.
        topBreadthFirstSearch.clear();
        topBreadthFirstSearch.clearLinks();

        // On place de nouveaux sommets dans le graphe
        topBreadthFirstSearch.addNode(100, 100, sommet0);
        topBreadthFirstSearch.addNode(500, 250, sommet1);
        topBreadthFirstSearch.addNode(600, 225, sommet2);
        topBreadthFirstSearch.addNode(700, 124, sommet3);
        topBreadthFirstSearch.addNode(800, 125, sommet4);
        topBreadthFirstSearch.addNode(900, 212, sommet5);
        topBreadthFirstSearch.addNode(1000, 374, sommet6);
        topBreadthFirstSearch.addNode(1100, 385, sommet7);

        // On crée à nouveau des liens.
        topBreadthFirstSearch.addLink(new Link(sommet2, sommet1));
        topBreadthFirstSearch.addLink(new Link(sommet1, sommet3));
        topBreadthFirstSearch.addLink(new Link(sommet2, sommet3));
        topBreadthFirstSearch.addLink(new Link(sommet6, sommet7));
        topBreadthFirstSearch.addLink(new Link(sommet3, sommet6));

        // J'indique que la nouvelle source de monAppli est sommet2
        monAppli.source = sommet2;

        // J'utilise la méthode breadthFirstSearch sur une liste que je vais comparer avec celle réaliser par ma personne.
        laListe = monAppli.breadthFirstSearch(topBreadthFirstSearch, sommet2);

        // Je vide aTester pour le changer juste après avec le parcours de l'algorithme (fait manuellement).
        aTester.clear();

        // Je remplis le tableau associatif à la main.
        // Il faut savoir que les liens que j'ai crée ne sont pas forcément inscrit dans le tableau, car la destination aura deja été atteinte.
        aTester.put(sommet2, sommet2);
        aTester.put(sommet1, sommet2);
        aTester.put(sommet3, sommet2);
        aTester.put(sommet0, null);
        aTester.put(sommet4, null);
        aTester.put(sommet5, null);
        aTester.put(sommet6, sommet3);
        aTester.put(sommet7, sommet6);

        // On compare maintenant les deux listes pour verifier si la liste que j'ai réalisé manuellement donne la meme chose que la liste issue de la méthode.
        assertEquals(laListe, aTester);

    }

    /**
     * Test de la méthode extractWay, de la classe MyApp, qui permet d'obtenir
     * une liste présentant le plus court chemin d'un sommet "source" vers un
     * sommet "destination"
     */
    @Test
    public void testExtractWay() {
        // On crée une topologie
        Topology topExtraction = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet2 = new Node();
        Node sommet3 = new Node();
        Node sommet5 = new Node();
        Node sommet6 = new Node();
        Node sommet7 = new Node();
        Node sommet8 = new Node();
        Node sommet9 = new Node();
        Node sommet10 = new Node();
        Node sommet11 = new Node();
        Node sommet12 = new Node();

        // On empeche la creation automatique de lien entre les sommets que l'on va crée juste après.
        topExtraction.setCommunicationRange(0);

        // On place ces sommets dans le graphe
        topExtraction.addNode(100, 100, sommet0);
        topExtraction.addNode(500, 250, sommet1);
        topExtraction.addNode(600, 225, sommet2);
        topExtraction.addNode(700, 124, sommet3);
        topExtraction.addNode(900, 212, sommet5);
        topExtraction.addNode(1000, 374, sommet6);
        topExtraction.addNode(1100, 385, sommet7);
        topExtraction.addNode(1200, 218, sommet8);
        topExtraction.addNode(1300, 1002, sommet9);
        topExtraction.addNode(1400, 804, sommet10);
        topExtraction.addNode(1500, 907, sommet11);
        topExtraction.addNode(1600, 865, sommet12);

        // On crée des liens entre différents sommets qui menent vers notre destination, ici notre destination sera sommet6
        topExtraction.addLink(new Link(sommet0, sommet3));
        topExtraction.addLink(new Link(sommet3, sommet5));
        topExtraction.addLink(new Link(sommet5, sommet2));
        topExtraction.addLink(new Link(sommet2, sommet6));

        // On crée un autre chemin, mais cette fois plus long que le precedent.
        topExtraction.addLink(new Link(sommet0, sommet7));
        topExtraction.addLink(new Link(sommet7, sommet8));
        topExtraction.addLink(new Link(sommet8, sommet9));
        topExtraction.addLink(new Link(sommet10, sommet11));
        topExtraction.addLink(new Link(sommet11, sommet12));

        // On affiche les liens entre les différents sommets pour verifier si nos modifications ont bien été prise en compte
        for (Link l : topExtraction.getLinks()) {
            System.out.println(l);
        }

        // On crée une nouvelle instance MyApp dans laquelle on associe sa topologie, une source et une destination (respectivement sommet0 et sommet6)
        MyApp monAppli = new MyApp();
        monAppli.tp = topExtraction;
        monAppli.source = sommet0;
        monAppli.destination = sommet6;

        // Je crée un tableau associatif de (sommet, sommet) en utilisant la méthode breadthFirstSearch testé precedemment.
        // Je monAppli'en sers ensuite pour l'appliquer à la methode extractWay(qui necessite le tableau du parcours en largeur)
        Map<Node, Node> parents = monAppli.breadthFirstSearch(topExtraction, sommet0);
        ArrayList<Node> laListe = monAppli.extractWay(parents, sommet0, sommet6);

        // Je crée une liste dans laquelle j'ajoute manuellement les noeuds qui formerait le plus court chemin,
        // je comparerais alors cette liste avec notre methode
        // je pense à creer deux chemins differents vers la destination pour verifier si la methode prend plus le chemin le plus court
        ArrayList<Node> aTester = new ArrayList<>();
        aTester.add(sommet0);
        aTester.add(sommet3);
        aTester.add(sommet5);
        aTester.add(sommet2);
        aTester.add(sommet6);

        // On compare maintenant si la méthode donne bien la meme lsite que celle que j'ai réalisé manuellement, auquel cas la methode fonctionne donc bien
        assertEquals(laListe, aTester);

        // On essaye avec un nouveau graphe, pour cela on vide la topologie avec les methodes natives de celle-ci.
        topExtraction.clear();
        topExtraction.clearLinks();

        // On place de nouveaux sommets dans le graphe dans lequel des sommets se trouvent dans le meme axe x mais l'axe Y du sommet sommet2 est plus proche de la
        // destination qui sera ici sommet3 (pour autant, la méthode n'utilisant pas la distance par rapport à la destination, le sommet sommet1 sera choisi)
        topExtraction.addNode(100, 100, sommet0);
        topExtraction.addNode(500, 50, sommet1);
        topExtraction.addNode(500, 145, sommet2);
        topExtraction.addNode(700, 125, sommet3);

        // On crée à nouveau des liens
        topExtraction.addLink(new Link(sommet0, sommet1));
        topExtraction.addLink(new Link(sommet1, sommet3));
        topExtraction.addLink(new Link(sommet1, sommet2));
        topExtraction.addLink(new Link(sommet2, sommet3));

        //J'indique que la nouvelle destination de ma classe est sommet0 et sommet3
        monAppli.source = sommet0;
        monAppli.destination = sommet3;

        // J'adapte donc les méthodes extractWay et parcours en largeur à notre nouveau graphe.
        parents = monAppli.breadthFirstSearch(topExtraction, sommet0);
        laListe = monAppli.extractWay(parents, sommet0, sommet3);

        // Je vide aTester pour le changer juste après
        aTester.removeAll(aTester);

        // Je remplis manuellement aTester pour verifier que la liste de sommet faite par la fonction et manuellement sont les memes
        // Meme si le sommet sommet1 est moins proche du sommet sommet3 que sommet2, c'est celui-ci qui sera parcouru car on parcourt les successeurs dans l'ordre, on passera donc par sommet1.
        aTester.add(sommet0);
        aTester.add(sommet1);
        aTester.add(sommet3);

        // On compare maintenant si la méthode donne bien la meme liste que celle que j'ai réalisé manuellement, auquel cas la methode fonctionne donc bien
        assertEquals(laListe, aTester);

    }

    /**
     * Test de la méthode compare, de la classe MyApp, qui compare la distance
     * de deux sommets à la destination.
     */
    @Test
    public void testCompare() {
        // On crée une topologie
        Topology topComparer = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet4 = new Node();
        Node sommet9 = new Node();
        Node sommet12 = new Node();

        // On place ces sommets dans le graphe
        topComparer.addNode(100, 100, sommet0);
        topComparer.addNode(800, 125, sommet4);
        topComparer.addNode(1300, 1002, sommet9);
        topComparer.addNode(1600, 865, sommet12);

        // On crée une instance MyApp dans laquelle on associe une source et une destination (respectivement sommet 1 et sommet12) pour pouvoir utiliser les méthodes de celle-ci
        MyApp monAppli = new MyApp();
        monAppli.tp = topComparer;
        monAppli.source = sommet1;
        monAppli.destination = sommet12;

        // On vérifie maintenant la distance séparant les différents sommets en utilisant la méthode quality
        // On calcul la distance entre le sommet9 et la destination (sommet12)
        double distanceSommet9aDestination;
        distanceSommet9aDestination = monAppli.quality(sommet9);

        // On calcul la distance entre le sommet0 et la destination (sommet12)
        double distanceSommet0aDestination;
        distanceSommet0aDestination = monAppli.quality(sommet0);

        // On compare maintenant ces deux distances, j'effectue dans un premier temps le calcul à la main, puis je le compare avec la methode compare()
        // QUI RETOURNE UN ENTIER !
        assertEquals(monAppli.compare(sommet9, sommet0), (int) (distanceSommet9aDestination - distanceSommet0aDestination));

        // On recommence avec deux autres distance, dont l'une est la distance de la destination vers la destination (sommet12 à sommet12)
        // Distance du sommet12 qui est la destination vers lui meme
        double distanceSommet12aDestination;
        distanceSommet12aDestination = monAppli.quality(sommet12);

        // Distance du sommet4 à la destination
        double distanceSommet4aDestination;
        distanceSommet4aDestination = monAppli.quality(sommet4);

        // On compare maintenant ces deux distances, j'effectue dans un premier temps le calcul à la main, puis je le compare avec la methode compare()
        // QUI RETOURNE UN ENTIER !
        assertEquals(monAppli.compare(sommet4, sommet12), (int) (distanceSommet4aDestination - distanceSommet12aDestination));
        // Cela est donc sensé donné distanceSommet4aDestination car distanceSommet12aDestination est 0
        assertEquals(monAppli.compare(sommet4, sommet12), (int) (distanceSommet4aDestination));

    }

    /**
     * Test de la méthode displayWay, de la classe MyApp et qui permet
     * d'afficher le chemin correspondant a la liste présente dans ces
     * parametres.
     */
    @Test
    public void testDisplayWay() {
        // On crée une topologie
        Topology topAffichageDuChemin = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet2 = new Node();
        Node sommet3 = new Node();
        Node sommet4 = new Node();
        Node sommet5 = new Node();
        Node sommet6 = new Node();
        Node sommet7 = new Node();
        Node sommet8 = new Node();
        Node sommet9 = new Node();
        Node sommet10 = new Node();
        Node sommet11 = new Node();
        Node sommet12 = new Node();

        // On empeche la creation automatique de lien entre les sommets que l'on va crée juste après.
        topAffichageDuChemin.setCommunicationRange(0);

        // On place ces sommets dans le graphe
        topAffichageDuChemin.addNode(100, 100, sommet0);
        topAffichageDuChemin.addNode(500, 250, sommet1);
        topAffichageDuChemin.addNode(600, 225, sommet2);
        topAffichageDuChemin.addNode(700, 124, sommet3);
        topAffichageDuChemin.addNode(800, 125, sommet4);
        topAffichageDuChemin.addNode(900, 212, sommet5);
        topAffichageDuChemin.addNode(1000, 374, sommet6);
        topAffichageDuChemin.addNode(1100, 385, sommet7);
        topAffichageDuChemin.addNode(1200, 218, sommet8);
        topAffichageDuChemin.addNode(1300, 1002, sommet9);
        topAffichageDuChemin.addNode(1400, 804, sommet10);
        topAffichageDuChemin.addNode(1500, 907, sommet11);
        topAffichageDuChemin.addNode(1600, 865, sommet12);

        // On crée des liens entre différents sommets qui menent vers notre destination, ici cela sera sommet4
        topAffichageDuChemin.addLink(new Link(sommet0, sommet3));
        topAffichageDuChemin.addLink(new Link(sommet3, sommet5));
        topAffichageDuChemin.addLink(new Link(sommet5, sommet2));
        topAffichageDuChemin.addLink(new Link(sommet2, sommet6));
        topAffichageDuChemin.addLink(new Link(sommet0, sommet7));
        topAffichageDuChemin.addLink(new Link(sommet7, sommet8));
        topAffichageDuChemin.addLink(new Link(sommet8, sommet9));
        topAffichageDuChemin.addLink(new Link(sommet10, sommet11));
        topAffichageDuChemin.addLink(new Link(sommet11, sommet12));

        // On affiche les liens entre les différents sommets pour verifier si nos modifications ont bien été prise en compte
        for (Link l : topAffichageDuChemin.getLinks()) {
            System.out.println(l);
        }

        // On crée une nouvelle instance MyApp dans laquelle on associe sa topologie, une source et une destination
        MyApp monAppli = new MyApp();
        monAppli.tp = topAffichageDuChemin;
        monAppli.source = sommet0;
        monAppli.destination = sommet4;

        // Je crée un tableau associatif de (sommet, sommet) en utilisant la méthode breadthFirstSearch testé precedemment.
        // Je monAppli'en sers ensuite pour l'appliquer à la methode extractWay(qui necessite le tableau du parcours en largeur) testé auparavant.
        Map<Node, Node> parents = monAppli.breadthFirstSearch(topAffichageDuChemin, sommet0);
        ArrayList<Node> laListe = monAppli.extractWay(parents, sommet0, sommet4);

        // Je mets la largeur de tous les liens de la topologie à 1, pour ensuite utiliser la méthode que l'on veut tester.
        monAppli.actionResetLink();

        // J'applique la méthode displayWay à la topologie
        monAppli.displayWay(laListe);

        // Je parcours les liens de la topologie, je verifie si ce lien est bien un lien présent dans celui du plus court chemin, puis je vérifie si la largeur est donc de 4.
        for (Link l : topAffichageDuChemin.getLinks()) {
            for (int i = 0; i < laListe.size() - 2; i++) {
                if (l == laListe.get(i).getCommonLinkWith(laListe.get(i + 1))) {
                    assertEquals(4, (int) l.getWidth());
                }
            }
        }
    }

    /**
     * Test de la méthode A, de la classe MyApp et qui effectue le parcours en
     * largueur d'un graphe en vérifiant la distance des sommets par rapport à
     * la destination.
     */
    @Test
    public void testA() {
        // On crée une topologie
        Topology topAstar = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet2 = new Node();
        Node sommet3 = new Node();
        Node sommet4 = new Node();
        Node sommet5 = new Node();
        Node sommet6 = new Node();
        Node sommet7 = new Node();
        Node sommet8 = new Node();
        Node sommet9 = new Node();
        Node sommet10 = new Node();
        Node sommet11 = new Node();
        Node sommet12 = new Node();

        // On empeche la creation automatique de lien entre les sommets que l'on va crée juste après.
        topAstar.setCommunicationRange(0);

        // On place ces sommets dans le graphe.
        topAstar.addNode(114, 102, sommet0);
        topAstar.addNode(517, 244, sommet1);
        topAstar.addNode(618, 232, sommet2);
        topAstar.addNode(708, 124, sommet3);
        topAstar.addNode(814, 125, sommet4);
        topAstar.addNode(924, 212, sommet5);
        topAstar.addNode(1032, 374, sommet6);
        topAstar.addNode(1116, 385, sommet7);
        topAstar.addNode(1236, 218, sommet8);
        topAstar.addNode(1345, 1002, sommet9);
        topAstar.addNode(1407, 804, sommet10);
        topAstar.addNode(1508, 907, sommet11);
        topAstar.addNode(1615, 865, sommet12);

        // On crée des liens entre différents sommets qui menent vers notre destination, ici cela sera sommet6.
        topAstar.addLink(new Link(sommet0, sommet3));
        topAstar.addLink(new Link(sommet3, sommet5));
        topAstar.addLink(new Link(sommet5, sommet2));
        topAstar.addLink(new Link(sommet2, sommet6));
        topAstar.addLink(new Link(sommet0, sommet7));
        topAstar.addLink(new Link(sommet7, sommet8));
        topAstar.addLink(new Link(sommet8, sommet9));
        topAstar.addLink(new Link(sommet10, sommet11));
        topAstar.addLink(new Link(sommet11, sommet12));

        // On affiche les liens entre les différents sommets pour verifier si nos modifications ont bien été prise en compte.
        for (Link l : topAstar.getLinks()) {
            System.out.println(l);
        }

        // On crée une instance MyApp dans laquelle on associe une topologie, une source et une destination.
        MyApp monAppli = new MyApp();
        monAppli.tp = topAstar;
        monAppli.source = sommet0;
        monAppli.destination = sommet6;

        // Je crée un tableau associatif de (sommet, sommet) en utilisant la méthode A, qui a en parametre notre topologie et la source qui est sommet0.
        Map<Node, Node> laListe = monAppli.A(topAstar, sommet0);

        // Je crée un tableau associatif ordonnée dans lequel j'ajoute manuellement les sommets et leurs predecesseurs .
        // La clé est le sucesseur, la valeur son predecesseur.
        // Je comparerais alors ce taleau associatif avec notre methode.
        HashMap<Node, Node> aTester = new HashMap<>();
        aTester.put(sommet0, sommet0);
        aTester.put(sommet2, sommet5);
        aTester.put(sommet3, sommet0);
        aTester.put(sommet5, sommet3);
        aTester.put(sommet6, sommet2);
        aTester.put(sommet7, sommet0);
        aTester.put(sommet8, sommet7);
        aTester.put(sommet9, sommet8);

        // On compare laListe donné par la méthode A et la liste que j'ai faites manuellement en respectant l'algorithme manuellement.
        assertEquals(laListe, aTester);

        // On essaye avec un nouveau graphe, pour cela on vide la topologie.
        topAstar.clear();
        topAstar.clearLinks();

        // On place de nouveaux sommets dans le graphe
        topAstar.addNode(124, 132, sommet0);
        topAstar.addNode(514, 268, sommet1);
        topAstar.addNode(618, 245, sommet2);
        topAstar.addNode(714, 123, sommet3);
        topAstar.addNode(802, 135, sommet4);
        topAstar.addNode(904, 212, sommet5);
        topAstar.addNode(1015, 374, sommet6);
        topAstar.addNode(1132, 385, sommet7);

        // On crée à nouveau des liens.
        topAstar.addLink(new Link(sommet2, sommet1));
        topAstar.addLink(new Link(sommet1, sommet3));
        topAstar.addLink(new Link(sommet2, sommet3));
        topAstar.addLink(new Link(sommet6, sommet7));
        topAstar.addLink(new Link(sommet3, sommet6));

        // J'indique que la nouvelle source de monAppli est sommet2 et sa destination est sommet7
        // Indiquer cela est important car l'algorithme s'arrete une fois la destination atteinte
        monAppli.source = sommet2;
        monAppli.destination = sommet7;

        // J'utilise la méthode breadthFirstSearch sur une liste que je vais comparer avec celle réaliser par ma personne.
        laListe = monAppli.A(topAstar, sommet2);

        // Je vide aTester pour le changer juste après avec le parcours de l'algorithme (fait manuellement).
        aTester.clear();

        // Je remplis le tableau associatif à la main.
        aTester.put(sommet2, sommet2);
        aTester.put(sommet1, sommet2);
        aTester.put(sommet3, sommet2);
        aTester.put(sommet6, sommet3);
        aTester.put(sommet7, sommet6);

        // On compare maintenant les deux listes pour verifier si la liste que j'ai réalisé manuellement donne la meme chose que la liste issue de la méthode.
        assertEquals(laListe, aTester);
    }

    /**
     * Test de la méthode quality, de le classe MyApp, qui vérifie la distance
     * entre deux sommets.
     */
    @Test
    public void testQuality() {
        // On crée une topologie
        Topology topEstimation = new Topology();

        // On initialise des sommets
        Node sommet0 = new Node();
        Node sommet1 = new Node();
        Node sommet2 = new Node();
        Node sommet3 = new Node();
        Node sommet4 = new Node();
        Node sommet5 = new Node();
        Node sommet6 = new Node();
        Node sommet7 = new Node();
        Node sommet8 = new Node();
        Node sommet9 = new Node();
        Node sommet10 = new Node();
        Node sommet11 = new Node();
        Node sommet12 = new Node();

        // On place ces sommets dans le graphe
        topEstimation.addNode(100, 100, sommet0);
        topEstimation.addNode(500, 250, sommet1);
        topEstimation.addNode(600, 225, sommet2);
        topEstimation.addNode(700, 124, sommet3);
        topEstimation.addNode(800, 125, sommet4);
        topEstimation.addNode(900, 212, sommet5);
        topEstimation.addNode(1000, 374, sommet6);
        topEstimation.addNode(1100, 385, sommet7);
        topEstimation.addNode(1200, 218, sommet8);
        topEstimation.addNode(1300, 1002, sommet9);
        topEstimation.addNode(1400, 804, sommet10);
        topEstimation.addNode(1500, 907, sommet11);
        topEstimation.addNode(1600, 865, sommet12);

        // On crée une instance MyApp dans laquelle on associe une source et une destination (respectivement sommet3 et sommet7) pour pouvoir utiliser les méthodes
        MyApp monAppli = new MyApp();
        monAppli.tp = topEstimation;
        monAppli.source = sommet3;
        monAppli.destination = sommet7;

        // On vérifie maintenant la distance séparant les différents sommets en utilisant la méthode quality (que l'on veut verifier) et le calcul
        // de la distance réalisé à la main
        double distanceSommet9aDestination;
        distanceSommet9aDestination = monAppli.quality(sommet9);

        // On compare la methode avec le calcul de la distance (on trouve le resultat en calculant l'hypothénuse qui relie sommet7 à sommet9 sur un plan 2D)
        // On a donc distance**2 = (sommet9.X - sommet7.X)**2 soi (-200)**2 + (sommet9.Y + sommet7.Y)**2 soi (-617)**2
        // distance = sqrt((-200)**2 + (-617)**2) = 648.6
        assertEquals(distanceSommet9aDestination, 648.6, 0.1);

        // On compare la distance entre la destination et la destination, techniquement  cela devrait donner 0
        double distanceSommet7aDestination;
        distanceSommet7aDestination = monAppli.quality(sommet7);

        // On compare la methode avec le calcul manuel de la distance (0 logiquement)
        assertEquals(distanceSommet7aDestination, 0.0, 0.1);

        // On compare la distance entre le sommet0 et la destination (sommet7)
        double distanceSommet0aDestination;
        distanceSommet0aDestination = monAppli.quality(sommet0);

        // On compare la methode avec le calcul manuel de la distance
        // Distance = sqrt(1000**2 + 285**2) = 1039.8
        assertEquals(distanceSommet0aDestination, 1039.8, 0.1);
    }

}
