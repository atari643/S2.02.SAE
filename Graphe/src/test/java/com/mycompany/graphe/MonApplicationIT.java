/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.graphe;

import io.jbotsim.core.Link;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author qartigala
 */
public class MonApplicationIT {
    @Test
    public void testParcoursEnLargeur() {
        Topology graphe = new Topology();
        Node n1= new Node();
        Node n2= new Node();
        Node n3= new Node();
        Node n4= new Node();
        graphe.addNode(n1);
        graphe.addNode(n2);
        graphe.addNode(n3);
        graphe.addNode(n4);
        Link link = new Link(n1, n2);
        Link link2 = new Link(n1, n3);
        Link link3 = new Link(n1, n4);
        graphe.addLink(link);
        graphe.addLink(link2);
        graphe.addLink(link3);
        MyApp monApp=new MyApp();
        Map<Node,Node> nodes = new HashMap<>();
        nodes=monApp.breadthFirstSearch(graphe, n1);
        assertEquals(n1, nodes.get(n1));
        assertEquals(n1, nodes.get(n2));
        assertEquals(n1, nodes.get(n3));
        assertEquals(n1, nodes.get(n4));
    }
    
}
