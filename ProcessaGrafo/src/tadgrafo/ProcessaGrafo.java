/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafo;

import java.util.LinkedList;

/**
 *
 * @author Serenna
 */
public class ProcessaGrafo {
    private TADGrafoD graph;
    private LinkedList<Vertex> vxGraph;
    private LinkedList<Edge> esGraph;

    public ProcessaGrafo(TADGrafoD grafo) {
        this.graph = grafo;
        this.vxGraph = this.graph.vertices();
        this.esGraph = this.graph.edges();
    }
    
    //Breadth search first 
    public LinkedList<Vertex> bfs(String vLabel) {
        LinkedList<Vertex> fila = new LinkedList<>();
        LinkedList<Vertex> saida = new LinkedList<>();
        
        fila.add(graph.getVertex(vLabel));
        //Quando todos os vértices forem visitados, a fila estará vazia
        while(!fila.isEmpty()) {
            //Desenfila o primeiro elemento da fila 
            Vertex head = fila.remove();
            //lista com vértices apontados pelo vertice desenfilado
            LinkedList<Vertex> vizinhos = graph.outAdjacenteVertices(head.getLabel());
            //Se houver algum vértice adjacente
            if(!vizinhos.isEmpty()) {
                //Verifica se o vértice já foi visitado
                if(!saida.contains(head)) {
                    saida.add(head);
                }
                //Para cada vizinho, verifica se ele já foi visitado
                //se não, adiciona-se na fila para visitar 
                for(Vertex destino : vizinhos) {
                    if(!saida.contains(destino)) {
                        fila.add(destino);
                    }
                }
            }
            else {
                //Se não houver nenhum vértice adjacente e não estiver na saida, adiciona
                if(!saida.contains(head))
                    saida.add(head);
            }
        }
        
        return saida;
        
    }
    
    //Depth first search, parecido mas usando pilha
    public LinkedList<Vertex> dfs(String vLabel) {
        LinkedList<Vertex> pVisitados = new LinkedList<>();
        LinkedList<Vertex> saida = new LinkedList<>();
        //Vertice inicial visitado, adiciona na 'pilha' de visitados
        pVisitados.add(graph.getVertex(vLabel));
        //Quando todos forem visitados, pilha vazia
        while (!pVisitados.isEmpty()) {
            //último vértice a ser empilhado
            Vertex topoPilha = pVisitados.pollLast();
            //Lista com os vértices alcançaveis a partir do vértice desempilhado
            LinkedList<Vertex> vizinhos = graph.outAdjacenteVertices(topoPilha.getLabel());
            //Caso tenha vértices adjacentes
            if (!vizinhos.isEmpty()) {
                //Marca o topo da pilha como visitado
                if (!saida.contains(topoPilha)) {
                    saida.add(topoPilha);
                }
                //Para cada vértice alcançavel, empilha-os marcando como visitados
                for (Vertex destino : vizinhos) {
                    if (!saida.contains(destino)) {
                        pVisitados.add(destino);
                    }
                }
            } else { //se não tiver vertices adjacentes e ainda não estiver na saída
                if (!saida.contains(topoPilha)) 
                    saida.add(topoPilha);
            }
        }

        return saida;
    }
}
