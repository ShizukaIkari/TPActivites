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
public class DSFloydW extends DataSet{
    private int [][] mat_custos = null;
    private TADDicChain dic_vertex_label_int = new TADDicChain(null);
    /*Dicionario de strings contendo os labels dos vértices representados pelas linhas/colunas 
    da matriz de distâncias. */
    
    /*Constructor chamado ao final do método cmFWarshall da classe ProcessaGrafo.
    Ao criar um objeto DSFloydW, cmFWarshall passa o resultado construído pela 
    execução do algoritmo de Floyd-Warshall. Ou seja, uma matriz de distancias 
    entre todos os pares de vértices de G.
    */
    public DSFloydW(int [][] mat_custos, TADDicChain lbls){
        this.dic_vertex_label_int = lbls;
        this.mat_custos = mat_custos;
    }
    //Retorna o numero com o custo do caminho mínimo entre os vértices de labels origem e destino, respectivamente.
    @Override
    public int custo(String origem, String destino) {
        int idLine = (int) dic_vertex_label_int.findElement(origem);
        int idCol =(int) dic_vertex_label_int.findElement(destino);
        return mat_custos[idLine][idCol];
    }
    
    //Retorna a lista de objetos vértices encontrados durante o trajeto de caminho
    //mínimo entre o vértice origem e o vértice destino.
    @Override
    public LinkedList<Vertex> caminho(String origem, String destino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
