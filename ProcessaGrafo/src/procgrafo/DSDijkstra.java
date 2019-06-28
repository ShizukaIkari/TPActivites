/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgrafo;

import java.util.LinkedList;
import taddic.TADDicChain;
import tadgrafo.Vertex;

/**
 *
 * @author Serenna
 */
public class DSDijkstra extends DataSet{
    private int [][] mat_custos = null;
    TADDicChain dic_vertex_label_int = new TADDicChain(null);
    @Override
    public float custo(String origem, String destino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Vertex> caminho(String origem, String destino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
