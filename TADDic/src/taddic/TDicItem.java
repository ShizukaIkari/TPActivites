/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taddic;

/**
 *
 * @author Serenna
 */
public class TDicItem {
    private Object chave;
    private Object valor;
    
    public TDicItem(Object chave, Object valor) {
        this.chave = chave;
        this.valor = valor;
    }
    
    public Object getChave() {
        return chave;
    }
    
    public Object getValor() {
        return valor;
    }
    
    public void setChave(Object chave) {
        this.chave = chave;
    }
    
    public void setValor(Object valor) {
        this.valor = valor;
    }
}
