/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2grafo.tadgrafo;

/**
 *
 * @author Serenna
 */
public class Vertex {
    private int id;
    private String label;   //Nome do vértice escolhido pelo usuário
    private Object dado;    //Dado guardado pelo vértice
    
    public Vertex(String label, Object dado){
        this.label = label;
        this.dado = dado;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getDado() {
        return dado;
    }

    public void setDado(Object dado) {
        this.dado = dado;
    }
}
