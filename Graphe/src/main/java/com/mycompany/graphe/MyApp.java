package com.mycompany.graphe;

import io.jbotsim.core.Color;
import io.jbotsim.core.Link;
import io.jbotsim.core.Topology;
import javax.swing.*;
import io.jbotsim.ui.JTopology;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import io.jbotsim.core.Node;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.core.event.TopologyListener;
import io.jbotsim.ui.icons.Icons;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * La classe est une application qui permet de représenter un graphe et de le
 * parcourir
 *
 * @author qartigala
 */
public class MyApp implements ActionListener, SelectionListener, TopologyListener, Comparator<Node> {

    Topology tp; // Objet qui contient le graphe
    JTopology jtp; // Composant graphique qui affiche le graphe
    Node source = null; // Le sommet source
    Node destination = null; //Le sommet destination
    boolean lock = false; //Défini si le lock est activé
    private JButton button3;

    /**
     * Fonction qui initialise le graphe et la partie graphique
     */
    public MyApp() {
        // Création du graphe
        tp = new Topology();
        tp.addSelectionListener(this);
        tp.addTopologyListener(this);
        // Création de l'interface graphique (ci-dessous)
        createInterfaceGraphics();
    }

    /**
     * Fonction qui génère l'interface
     */
    private void createInterfaceGraphics() {
        // Création d'une fenêtre
        JFrame window = new JFrame("Mon application");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du composant graphique qui affiche le graphe
        jtp = new JTopology(tp);
        window.add(jtp);

        // Création d'un bouton test
        JButton button = new JButton("Parcours en largeur");
        window.add(button, BorderLayout.NORTH);

        JButton button2 = new JButton("Reset");
        window.add(button2, BorderLayout.SOUTH);
        button3 = new JButton("Lock/Unlock");
        window.add(button3, BorderLayout.WEST);
        JButton button4 = new JButton("A*");
        window.add(button4, BorderLayout.EAST);
        // Abonnement aux évènements du bouton (clic, etc.)
        button.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);
        // Finalisation
        window.pack();
        window.setVisible(true);
    }

    /**
     * Permet de reset l'ensemble des liens dans le graphe
     */
    public void actionResetLink() {
        for (Link link : tp.getLinks()) {
            link.setWidth(1);

        }
    }

    /**
     * Action qui gère les évènements des boutons graphiques
     *
     * @param e le type d'évènement renvoyer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Parcours en largeur")) {
            if (this.source != null && this.destination != null && this.destination.hasNeighbors()) {
                actionResetLink();
                extractWay(breadthFirstSearch(tp, source), source, destination);
            }

        } else if (e.getActionCommand().equals("Reset")) {
            if (source != null && destination != null) {
                actionResetLink();
                this.source.setColor(null);
                this.destination.setColor(null);
                this.destination.setIcon(Icons.DEFAULT_NODE_ICON);
                this.source.setIcon(Icons.DEFAULT_NODE_ICON);
                this.source = null;
                this.destination = null;
            }

            JOptionPane.showMessageDialog(null, "Bouton cliqué: Message");
        } else if (e.getActionCommand().equals("Lock/Unlock") || e.getActionCommand().equals("Stop Lock")) {
            lock = !lock;
            if (lock) {
                button3.setText("Stop Lock");
            } else {
                button3.setText("Lock/Unlock");
            }

        } else if (e.getActionCommand().equals("A*")) {
            if (this.source != null && this.destination != null) {
                actionResetLink();
                A(tp, source);
            }

        }
    }

    public static void main(String[] args) {
        new MyApp();
    }

    /**
     * Fonction qui permet d'associé au attribut source et destination les
     * sommets selectionnés.
     *
     * @param selectedNode le sommet selection par la mollette de la souris
     */
    @Override
    public void onSelection(Node selectedNode) {
        if (lock != true) {
            if (source == null && selectedNode.getColor() != Color.red) {
                source = selectedNode;
                source.setIcon(Icons.DRONE);
            } else if (destination == null && selectedNode.getColor() != Color.red && selectedNode != source) {
                destination = selectedNode;
                destination.setIcon(Icons.FLAG);
            }
            if (destination != null) {
                if (!destination.hasNeighbors()) {
                    JOptionPane.showMessageDialog(null, "Votre destination est isolé");
                    destination.setIcon(Icons.DEFAULT_NODE_ICON);
                    destination = null;
                }
            }
        } else if (!selectedNode.equals(source) && !selectedNode.equals(destination) && selectedNode.getColor() != Color.red) {
            selectedNode.setColor(Color.red);
        } else if (!selectedNode.equals(source) && !selectedNode.equals(destination) && selectedNode.getColor().equals(Color.red)) {
            selectedNode.setColor(null);
        }
    }

    /**
     * Fonction qui effectue le parcours en largueur d'un graphe
     *
     * @param graph le graphe à parcourir
     * @param source le sommet source
     * @return la liste des Sommets enfants associé au sommet parents
     */
    public Map<Node, Node> breadthFirstSearch(Topology graph, Node source) {
        Map<Node, Node> listNodes = new HashMap<>();
        Node s = null;
        for (Node nodes : graph.getNodes()) {
            listNodes.put(nodes, null);
        }
        listNodes.put(source, source);
        Queue<Node> f = new LinkedBlockingQueue<>();
        f.add(source);
        boolean status = false;
        while (!f.isEmpty() && !status) {
            s = f.remove();
            if (notNeighborsLock(s)) {
                for (Node nodes : s.getNeighbors()) {
                    if (listNodes.getOrDefault(nodes, null) == null && !isLocked(nodes)) {
                        listNodes.put(nodes, s);
                        f.add(nodes);
                    }
                }
            } else {
                status = true;
            }
        }
        return listNodes;
    }

    /**
     * Fonction qui détermine si le chemin est accessible
     *
     * @param node un sommet
     * @return vrai si il y a au moins un voisin du sommet qui n'est pas lock
     * faux sinon
     */
    public boolean notNeighborsLock(Node node) {
        boolean status = false;
        List<Node> nodes = node.getNeighbors();
        int i = 0;
        while (i < nodes.size() && !status) {
            if (nodes.get(i).getColor() != Color.red || nodes.get(i).equals(destination) || nodes.get(i).equals(source)) {
                status = true;
            }
            i++;
        }
        return status;

    }

    /**
     * Fonction qui détermine si un sommet à des voisins accessibles
     *
     * @param node un sommet
     * @return vrai si il y a au moins un voisin du sommet qui n'est pas lock
     * faux sinon
     */
    public boolean notNeighbors(Node node) {
        boolean status = false;
        List<Node> nodes = node.getNeighbors();
        int i = 0;
        for (Node n : nodes) {
            if (n.getCommonLinkWith(node).getWidth() == 4) {
                i++;
            }
            if (i == node.getNeighbors().size()) {
                status = true;
            }
        }
        return status;

    }

    /**
     * Methode permettant d'afficher le plus court chemin sur le graphe
     *
     * @param shortWay Une liste de sommet qui indique le plus court chemin
     */
    public void displayWay(ArrayList<Node> shortWay) {
        for (int i = 0; i < shortWay.size() - 1; i++) {
            shortWay.get(i).getCommonLinkWith(shortWay.get(i + 1)).setWidth(4);
        }
    }

    /**
     * Fonction qui effectue le parcours en largueur d'un graphe
     *
     * @param graph le graphe à parcourir
     * @param source le sommet source
     * @return la liste des Sommets enfants associé au sommet parents
     */
    public Map<Node, Node> A(Topology graph, Node source) {
        Map<Node, Node> listNodes = new HashMap<>();
        Node s = null;
        for (Node nodes : source.getNeighbors()) {
            listNodes.put(nodes, null);
        }
        listNodes.put(source, source);
        Queue<Node> file = new PriorityBlockingQueue<>(200, this);
        file.add(source);
        boolean fin = false;
        while (!file.isEmpty() && !fin) {
            s = file.remove();
            if (notNeighborsLock(s) && s != source && !isLocked(s)) {
                listNodes.get(s).getCommonLinkWith(s).setWidth(4);
            }
            if (s != destination && !notNeighbors(s)) {
                if (!isLocked(s)) {
                    for (Node nodes : s.getNeighbors()) {
                        if (listNodes.getOrDefault(nodes, null) == null && !isLocked(nodes)) {
                            listNodes.put(nodes, s);
                            file.add(nodes);
                        }
                        if (listNodes.getOrDefault(nodes, null) == null && isLocked(nodes)) {
                            file.add(nodes);
                        }
                    }
                }
            } else {
                fin = true;
            }
        }
        return listNodes;
    }

    /**
     * Fonction qui permet de vérifier si le sommet est accessible
     *
     * @param node un sommet
     * @return retourne vrai si le sommet est inaccessible sinon faux
     */
    public boolean isLocked(Node node) {
        boolean status = false;
        if (node.getColor() == Color.red) {
            status = true;
        }
        return status;
    }

    @Override
    public void onNodeAdded(Node node) {
    }

    /**
     * Fonction qui permet de détecter la suppression d'un sommet
     *
     * @param node le sommet supprimer
     */
    @Override
    public void onNodeRemoved(Node node) {
        if (node.equals(source)) {
            this.source.setIcon(Icons.DEFAULT_NODE_ICON);
            this.source = null;

        } else if (node.equals(destination)) {
            this.destination.setIcon(Icons.DEFAULT_NODE_ICON);
            this.destination = null;
        }
    }

    /**
     * La fonction permet de connaitre le plus court chemin entre deux sommets
     *
     * @param largeur le parcours en largeur du graphe
     * @param source le sommet source
     * @param destination le sommet destination
     * @return la liste des sommets du chemin dans le bon ordre
     */
    public ArrayList<Node> extractWay(Map<Node, Node> largeur, Node source, Node destination) {
        ArrayList<Node> nodes = new ArrayList<>();
        Node s = destination;
        nodes.add(s);
        boolean status = false;
        if (!notNeighborsLock(s)) {
            status = true;
        }
        Node s1 = null;
        while (s != source && s != null && !status) {
            nodes.add(largeur.get(s));
            s1 = largeur.get(s);
            if (s1 != null) {
                s1.getCommonLinkWith(s).setWidth(4);
            }
            s = largeur.get(s);
            if (s != null) {
                if (!notNeighborsLock(s) && notNeighbors(s)) {
                    status = true;
                }
            } else {
                status = true;
                JOptionPane.showMessageDialog(null, "Votre destination est isolé");
            }
        }
        ArrayList<Node> reverseNodes = new ArrayList<>(nodes);
        Collections.reverse(reverseNodes);
        return reverseNodes;
    }

    /**
     * Fonction qui compare la distance entre deux sommets
     *
     * @param t Le premier sommet
     * @param t1 le second sommet
     * @return La différence entre les deux estimations
     */
    @Override
    public int compare(Node t, Node t1) {

        double i1 = quality(t);
        double i2 = quality(t1);
        return (int) (i1 - i2);
    }

    /**
     * La fonction qui permet de récuper la distance entre un sommet et la
     * destination
     *
     * @param nodes un sommet
     * @return la distance entre le sommet et la destination
     */
    public double quality(Node nodes) {
        return nodes.distance(destination);
    }

}
