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
public abstract class DataSet {
    public abstract int custo(String origem, String destino);
    public abstract LinkedList<Vertex> caminho(String origem, String destino);
}
