/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.graphe;

/**
 *
 * @author qartigala
 */
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;

public class Graphe{
    public static void main(String[] args){
        Topology tp = new Topology();
        new JViewer(tp);
        tp.start();
    }
}
