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
    private long cach_hash; 
    
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

    public long getCach_hash() {
        return cach_hash;
    }

    public void setCach_hash(long cach_hash) {
        this.cach_hash = cach_hash;
    }
    
}
