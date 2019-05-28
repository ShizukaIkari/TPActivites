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
    private Object key;
    private Object dado;
    private long cash_hash; 
    
    public TDicItem(Object chave, Object valor) {
        this.key = chave;
        this.dado = valor;
    }
    
    public Object getKey() {
        return key;
    }
    
    public Object getDado() {
        return dado;
    }
    
    public void setKey(Object key) {
        this.key = key;
    }
    
    public void setDado(Object dado) {
        this.dado = dado;
    }

    public long getCash_hash() {
        return cash_hash;
    }

    public void setCash_hash(long cash_hash) {
        this.cash_hash = cash_hash;
    }
    
}
