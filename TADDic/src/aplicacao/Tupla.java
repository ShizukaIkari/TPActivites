/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.io.Serializable;

/**
 *
 * @author Serenna
 */
public class Tupla implements Serializable{
    private int linhaElem;
    private int colunElem;
    
    public Tupla(){}
    
    public Tupla(int l, int c){
        this.linhaElem = l;
        this.colunElem = c;
    }
    
    public int getLinhaElem() {
        return linhaElem;
    }

    public void setLinhaElem(int linhaElem) {
        this.linhaElem = linhaElem;
    }

    public int getColunElem() {
        return colunElem;
    }

    public void setColunElem(int colunElem) {
        this.colunElem = colunElem;
    }
    
}
