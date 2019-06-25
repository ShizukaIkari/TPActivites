/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2grafo.tadgrafo;

import java.util.LinkedList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 *
 * @author Serenna
 */
public class ToGStream {

    private Graph g;
    private boolean dirigido;
    private boolean vertexVisivel;
    private boolean edgeVisivel;

    public ToGStream(TADGrafoD g,boolean vertexVisivel, boolean edgeVisivel, boolean dirigido) {
		
        this.g = new SingleGraph(g.getNome());

        if(vertexVisivel) {
            LinkedList<Vertex> vertices = g.vertices();
            for (Vertex v : vertices) {
                Node no = this.g.addNode(v.getLabel());
                no.addAttribute("ui.label", v.getLabel());
            }
            if(edgeVisivel) {
                LinkedList<Edge> edges = g.edges();
                for (Edge e : edges) {
                    Vertex[] endV = g.endVertices(e.getLabel());
                    if(dirigido) {
                        org.graphstream.graph.Edge edge = this.g.addEdge(e.getLabel(), endV[0].getLabel(),endV[1].getLabel());
                        edge.addAttribute("ui.label", e.getLabel());
                    }
                    else {
                        org.graphstream.graph.Edge edge = this.g.addEdge(e.getLabel(), endV[0].getLabel(),endV[1].getLabel());
                        edge.addAttribute("ui.label", e.getLabel());
                    }
                }
            }
        }	
    }
    
    public void exibe() {
        this.g.display();
    }
}
