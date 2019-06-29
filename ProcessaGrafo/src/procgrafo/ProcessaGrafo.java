/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgrafo;

import static java.lang.System.exit;
import java.util.Arrays;
import java.util.LinkedList;
import taddic.TADDicChain;
import tadgrafo.Edge;
import tadgrafo.TADGrafoD;
import tadgrafo.Vertex;

/**
 *
 * @author Serenna
 */
public class ProcessaGrafo {
    public TADGrafoD g = null;
    
    public ProcessaGrafo(TADGrafoD g){
        this.g = g;
    }
    
    //Breadth search first 
    public LinkedList<Vertex> bfs(String origem) {
        LinkedList<Vertex> fila = new LinkedList<>();
        LinkedList<Vertex> saida = new LinkedList<>();

        fila.add(g.getVertex(origem));
        //Quando todos os vértices forem visitados, a fila estará vazia
        while (!fila.isEmpty()) {
            //Desenfila o primeiro elemento da fila 
            Vertex head = fila.remove();
            //lista com vértices apontados pelo vertice desenfilado
            LinkedList<Vertex> vizinhos = g.outAdjacenteVertices(head.getLabel());
            System.out.println(vizinhos.size()+"tamanho viz");
            //Se houver algum vértice adjacente
            if (!vizinhos.isEmpty()) {
                //Verifica se o vértice já foi visitado
                if (!saida.contains(head)) {
                    saida.add(head);
                }
                //Para cada vizinho, verifica se ele já foi visitado
                //se não, adiciona-se na fila para visitar 
                for (Vertex destino : vizinhos) {
                    if (!saida.contains(destino)) {
                        fila.add(destino);
                    }
                }
            } else {
                //Se não houver nenhum vértice adjacente e não estiver na saida, adiciona
                if (!saida.contains(head)) {
                    saida.add(head);
                }
            }
        }

        return saida;

    }

    //Depth first search, parecido mas usando pilha
    public LinkedList<Vertex> dfs(String origem) {
        LinkedList<Vertex> pVisitados = new LinkedList<>();
        LinkedList<Vertex> saida = new LinkedList<>();
        //Vertice inicial visitado, adiciona na 'pilha' de visitados
        pVisitados.add(g.getVertex(origem));
        //Quando todos forem visitados, pilha vazia
        while (!pVisitados.isEmpty()) {
            //último vértice a ser empilhado
            Vertex topoPilha = pVisitados.pollLast();
            //Lista com os vértices alcançaveis a partir do vértice desempilhado
            LinkedList<Vertex> vizinhos = g.outAdjacenteVertices(topoPilha.getLabel());
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
                if (!saida.contains(topoPilha)) {
                    saida.add(topoPilha);
                }
            }
        }

        return saida;
    }
    
    public DSDijkstra cmDijkstra(String origem){
        
        DSDijkstra answer = new DSDijkstra(null, null);
        return answer;
    }
    
    public DSDijkstra cmBFord(String origem){
        DSDijkstra answer = new DSDijkstra(null, null);
        return answer;
    }
    
    //Custo das arestas estarão no campo dado
    public DSFloydW cmFWwarshall(){
        int numVertices = g.numVertices();
        int[][] dist = new int[numVertices][numVertices];
        TADDicChain dicLabel = new TADDicChain(null);
        LinkedList<Edge> weights = g.edges();
        LinkedList<Vertex> vrtx = g.vertices();
        //preenchendo a matriz de adjacencia auxiliar com infinitos
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        
        // Construção da matriz de pesos/custos, potencial risco de  outofbound 
        for (int i=0;i<weights.size();i++) {
            if(weights.get(i) != null){
                Vertex[] origDest = g.endVertices(weights.get(i).getLabel());
                if(origDest!=null){
                    //dist [idVerticeOrigem][idVerticeDestino] = pesoAresta
                    dist[origDest[0].getId()-1][origDest[1].getId()-1] = (Integer) weights.get(i).getDado();
                    dicLabel.insertItem(origDest[0].getLabel(), origDest[0].getId()-1);
                    dicLabel.insertItem(origDest[1].getLabel(), origDest[1].getId()-1);
                    
                }else{
                    exit(1);
                    System.out.println("Aresta sem endvertices");
                }
            }
        }
        //array pra gerenciar o caminho, substituir por DS
        int[][] next = new int[numVertices][numVertices];
        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next.length; j++) {
                if (i != j) {
                    next[i][j] = j;
                }
            }
        }
        //Calculando os caminhos mínimos
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if(dist[i][k] != Integer.MAX_VALUE && dist[k][j]!= Integer.MAX_VALUE)
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            if(i!=j)
                                dist[i][j] = dist[i][k] + dist[k][j];
                            else
                                dist[i][j] = 0;
                        }
                }
            }
        }

        DSFloydW ds = new DSFloydW(dist,dicLabel);
        return ds;
    }
}
