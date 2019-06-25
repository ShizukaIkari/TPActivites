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
public class Edge {
    private int id; //id único para identificar
    private String label; //label para usuario identificar
    private Object dado; //liberdade para colocar qualquer tipo de dado

    public Edge(String label, Object dado) {
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
