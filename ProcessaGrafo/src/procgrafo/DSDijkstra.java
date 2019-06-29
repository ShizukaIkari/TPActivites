/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgrafo;

import java.util.LinkedList;
import tadgrafo.Vertex;

/**
 *
 * @author Serenna
 */
public class DSDijkstra extends DataSet{
    private int [] vet_custos = null;
    private String[] vet_antec;
    
    /*Constructor chamado ao final do método cmDijkstra da classe ProcessaGrafo.
    Ao criar um objeto DSDijkstra, cmDijkstra passa o resultado construído pela 
    execução do algoritmo de Dijkstra (idem Bellamn-Ford). Ou seja, o vetor de 
    custos e o vetor de antecessores.*/
    public DSDijkstra (int[] vet_custos, String[] vet_antec){
        this.vet_antec = vet_antec;
        this.vet_custos = vet_custos;
    }
    @Override
    public int custo(String origem, String destino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Vertex> caminho(String origem, String destino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
