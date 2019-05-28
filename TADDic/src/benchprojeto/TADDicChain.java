/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchprojeto;

import taddic.*;
import java.util.LinkedList;

/**
 *
 * @author Serenna
 */
public class TADDicChain {

    private LinkedList<TDicItem>[] vetBuckets = null;
    private double fator_de_carga = 0.75; //usado para descobrir numero de registros no dic
    private int quant_entradas = 0;
    private Hash_engine he = null;
    private boolean achou = false;
        
    /*Construtores*/
    public TADDicChain(int qtdEnt, Hash_engine he){
        int tam = (int)(qtdEnt/fator_de_carga);
        vetBuckets = new LinkedList[tam];
        
        for (int i = 0; i<tam;i++){
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
        if(he == null) {
            he = new HashEngineDefault();
        }
        else {
            this.he = he;
        }
    }
 
    public TADDicChain(Hash_engine he){
        int tam = 1024;
        vetBuckets = new LinkedList[tam];
        
        for (int i = 0; i<tam;i++){
            vetBuckets[i] = new LinkedList<TDicItem>();
        }
        if(he == null) {
            he = new HashEngineDefault();
        }
        else {
            this.he = he;
        }
    }
    
    public boolean isEmpty(){
        return quant_entradas == 0;
    }
    /*Retorna o tamanho das listas do vetor*/
    public int getSizeVetBuckets(){
        return vetBuckets.length;
    }
    
    /*Retorna o número de itens no dicionário*/
    public int size(){
        return quant_entradas;
    }
    
    /*Método para gerenciar colisões*/
    private int lenMaiorList(){
        int maior = 0;
        for (int i = 0; i < vetBuckets.length; i++) {
            if(vetBuckets[i] != null){
                if(vetBuckets[i].size() > maior)
                    maior = vetBuckets[i].size();
            }
        }
        return maior;
    }
    /*Função para inserir dados dentro do dicionário*/
    public void insertItem(Object k, Object e){
        //Para diminuir o número de colisões, aumenta o tamanho do dicionário
        if(lenMaiorList() >=  getSizeVetBuckets()* 0.3 ){
            redimensiona();
        }
        Object aux = findElement(k);
        long hashCode = he.hash_func(k);
        int index = index = (int) hashCode % vetBuckets.length;
        //Caso seja um elemento novo, calcula o hashCode e adiciona no dicionario
        if(NO_SUCH_KEY()){
            TDicItem item = new TDicItem(k, e);
            item.setCash_hash(hashCode);
            vetBuckets[index].add(item);
            quant_entradas++;
        }else{
            int pos = buscaDItem(vetBuckets[index],k);
            if(pos != -1)
                ((TDicItem)(vetBuckets[index].get(pos))).setDado(e);
        }
    }
    /*Método auxiliar para achar posição em dada lista*/
    private int buscaDItem(LinkedList lst, Object k){
        int pos = 0;
        
        while(pos < lst.size()){
            if(((TDicItem)(lst.get(pos))).getKey().equals(k))
                return pos;
            pos++;
        }
        return -1;
    }
    /*Recebe a chave, converte para o código hash correspondente e faz a busca,
    retornando se achar*/
    public Object findElement(Object k){
        long hashCode = he.hash_func(k);
        int index = (int) hashCode % vetBuckets.length;
        /*Acessa a lista no índice da chave
        Percorre até achar a chave em si e retorna o elemento*/
        int posLst = 0;
        while(posLst < vetBuckets[index].size()){
            if(((TDicItem)vetBuckets[index].get(posLst)).getKey().equals(k)){
                achou = true;
                return ((TDicItem)vetBuckets[index].get(posLst)).getDado();
            }
            posLst++;
        }
        achou = false;
        return null;
    }
   

    /*Default: false, é alterado quando findElement é usado*/
    public boolean NO_SUCH_KEY(){
        return !achou;
    }
    
    /*Return list with all keys*/
    public LinkedList keys(){
        LinkedList<TDicItem> itens = getItens(); 
        LinkedList chaves = null;
        
        /*It must not be empty*/
        if(!isEmpty()){
            chaves = new LinkedList();
            //Getting the keys from each item
            for(TDicItem it : itens){
                chaves.add(it.getKey());
            }
        }
        return chaves;
    }
    
    /*Return all objects inside the dictionary*/
    public LinkedList<Object> elements(){
        LinkedList<TDicItem> itens = getItens(); 
        LinkedList elems = null;
        
        /*It must not be empty*/
        if(!isEmpty()){
            elems = new LinkedList();
            //Getting data from each item
            for(TDicItem it : itens){
                elems.add(it.getDado());
            }
        }
        return elems;
    }
    
    public LinkedList getItens(){
        LinkedList<TDicItem> itens = null;
        if(!isEmpty()){
            itens = new LinkedList<>();
            
            for(int i=0; i<this.getSizeVetBuckets() ;i++){
                for(int j=0; j<vetBuckets[i].size();j++){
                    itens.add(vetBuckets[i].get(j));
                }
            }
        }
        return itens;
    }
    
    public Object removeElement(Object k){
         Object aux = findElement(k);
         
         if(NO_SUCH_KEY()){
             return null;
         } else {
            long cod_hash = he.hash_func(k);
            int index = (int) cod_hash % vetBuckets.length;
            /*Acessa a lista no índice da chave
             Percorre até achar a chave em si e deleta o elemento*/
             
             int posLst = buscaDItem(vetBuckets[index],k);
             if(posLst != -1){
                vetBuckets[index].remove(posLst);
                quant_entradas--;
             }
             return aux;
         } 
    }
    
    public Hash_engine getHashEngine(){
        return this.he;
    }
    
    public TADDicChain clone(){
        TADDicChain dicClone = new TADDicChain(he);
        for (int i =0;i<vetBuckets.length;i++)
            for (int k =0; k < vetBuckets[i].size();k++){
                Object chave = ((TDicItem) vetBuckets[i].get(k)).getKey();
                Object dado = ((TDicItem) vetBuckets[i].get(k)).getDado();
                dicClone.insertItem(chave, dado);
                
            }
        return dicClone;
    }
    /*Compara os dois dicionários elemento por elemento,*/
    public boolean equals(TADDicChain dic){
        if(he == dic.getHashEngine()){
            if(this.size() == dic.size())
                for(int i = 0; i < getSizeVetBuckets(); i++){
                    for (int j = 0; j < vetBuckets[i].size(); j++) {
                        Object key = ((TDicItem)(vetBuckets[i].get(j))).getKey();
                        Object val = ((TDicItem)(vetBuckets[i].get(j))).getDado();
                        Object clVal = dic.findElement(key);
                        
                        if(dic.NO_SUCH_KEY() || val !=clVal)
                            return false;
                    }                    
                }
        }
        return true;
    }
    private void redimensiona(){
        int newTam = (int) (1.5*vetBuckets.length);
        LinkedList[] nVetList = new LinkedList[newTam];
        
        for( int i = 0; i < newTam; i++) 
            nVetList[i] = new LinkedList<TDicItem>();
               
        for(int i = 0; i < getSizeVetBuckets(); i++) {
            if(vetBuckets[i] != null) {
                for(int j = 0; j <vetBuckets[i].size(); j++) {
                    Object aux = vetBuckets[i].get(j);
                    
                    long cod_hash = ((TDicItem)aux).getCash_hash();
                    int indice = (int)cod_hash % nVetList.length;
                    
                    nVetList[indice].add((TDicItem)aux);
                }
            }
        }
        vetBuckets = nVetList;
    }
    
    public int[] getVetColisoes(){
        int[] numCol = new int[this.size()];
        
        for(int i = 0; i<this.size();i++){
            numCol[i] = vetBuckets[i].size();
        }
        return numCol;
    }
}
