/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taddic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 *
 * @author Serenna
 */
public class TADdic {

    private LinkedList vetList[] = null;
    private double fatorCarga = 0.75; //usado para descobrir numero de registros no dic
    private int qtdEntradas = 0;
    private HashEngine he = null;
    private boolean achou = false;
        
    /*Construtores*/
    public TADdic(int qtdIniEntradas){
        int tam = (int)(qtdIniEntradas/fatorCarga);
        vetList = new LinkedList[tam];
        
        for (int i = 0; i<tam;i++){
            vetList[i] = new LinkedList<RegDados>();
        }
        he = new HashEngDefault();
    }
    
    public TADdic(){
        int tam = 100;
        vetList = new LinkedList[tam];
        
        for (int i = 0; i<tam;i++){
            vetList[i] = new LinkedList<TDicItem>();
        }
        he = new HashEngDefault();
    }
    public TADdic(HashEngine eng){
        int tam = 100;
        vetList = new LinkedList[tam];
        
        for (int i = 0; i<tam;i++){
            vetList[i] = new LinkedList<TDicItem>();
        }
        he = eng;
    }
    
    /*Função responsável por transformar a chave em um número inteiro
    Transforma a chave em vetor de bytes e os soma
    */
    private long hashFunction(Object k){
        long soma = 0;
        
        ByteArrayOutputStream bus = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] vetBytes = null;
               
        try{
            out = new ObjectOutputStream(bus);
            out.writeObject(k);
            out.flush();
            vetBytes = bus.toByteArray();
        }catch(IOException e) {
            e.printStackTrace();
        }
        try {
            bus.close();
        } catch (IOException ex) {
            //ignore
        }
        for(int i=0; i<vetBytes.length;i++){
            soma += (int)vetBytes[i];
        }
        return soma;
    }
    
    public boolean isEmpty(){
        return qtdEntradas == 0;
    }
    /*Retorna o tamanho das listas do vetor?*/
    public int getSizeList(){
        return vetList.length;
    }
    /*Retorna o número de itens no dicionário*/
    public int size(){
        return qtdEntradas;
    }
    
    public long hashFuncPol(String k){
        long soma = 0;
        for(int exp = 0; exp<k.length(); exp++){
            soma+=(int)k.charAt(exp)^exp;
        }
        return soma;
    }
    
    private int lenMaiorList(){
        int maior = 0;
        for (int i = 0; i < vetList.length; i++) {
            if(vetList[i] != null){
                if(vetList[i].size() > maior)
                    maior = vetList[i].size();
            }
        }
        return maior;
    }
    /**/
    public void insertItem(Object k, Object e){
        Object aux = findElement(k);
        long hashCode = he.hashFunction(k);
        int index = (int) hashCode % vetList.length;
        if(aux == null){
            vetList[index].add(new TDicItem(k, e));
            qtdEntradas++;
        }else{ 
            int pos = buscaDItem(vetList[index],k);
            if(pos != -1)
                ((TDicItem)(vetList[index].get(pos))).setValor(e);
        }
    }
    
    public int buscaDItem(LinkedList lst, Object k){
        int pos = 0;
        
        while(pos < lst.size()){
            if(((TDicItem)(lst.get(pos))).getChave().equals(k))
                return pos;
            pos++;
        }
        return -1;
    }
    /*Recebe a chave, converte para o código hash correspondente e faz a busca,
    retornando se achar*/
    public Object findElement(Object k){
        long hashCode = he.hashFunction(k);
        int index = (int) hashCode % vetList.length;
        /*Acessa a lista no índice da chave
        Percorre até achar a chave em si e retorna o elemento*/
        int posLst = 0;
        while(posLst < vetList[index].size()){
            if(((TDicItem)vetList[index].get(posLst)).getChave().equals(k)){
                achou = true;
                return vetList[index].get(posLst);
            }
            posLst++;
        }
        return null;
    }
    
    public LinkedList keys(){
        LinkedList chaves = new LinkedList();
        if(!isEmpty()){
            for(int i=0; i<getSizeList() ;i++)
                if(vetList[i].size() > 0){
                    int posLst = 0;
                    while (posLst < vetList[i].size()) {
                        chaves.add(((TDicItem) vetList[i].get(posLst)).getChave());
                        posLst++;
                    }
                } 
            
        }
        return chaves;
    }
    
     
    public LinkedList<TDicItem> elements(){
        LinkedList<TDicItem> elems = new LinkedList<TDicItem>();
        
        if(!isEmpty()){
            
        }
        for(int i=0; i<this.getSizeList() ;i++){
            int posLst = 0;
            while(posLst < vetList[i].size()){
                elems.add(((TDicItem)vetList[i].get(posLst)));
                posLst++;
            }
        }
        return elems;
    }
    //Dúvida
    public Object removeElement(Object k){
         Object aux = findElement(k);
         if(aux == null){
             return null;
         } else {
             long codHash = hashFunction(k);
             int index = (int)codHash %vetList.length;
             
             /*Acessa a lista no índice da chave
             Percorre até achar a chave em si e deleta o elemento*/
             int posLst = 0;
             while(posLst < vetList[index].size()){
                 posLst++;
             }
             vetList[index].remove(posLst-1);
             qtdEntradas--;
             return aux;
         } 
     }
    
    public int[] getColisions(){
        int[] numCol = new int[this.size()];
        
        for(int i = 0; i<this.size();i++){
            numCol[i] = vetList[i].size();
        }
        return numCol;
    }
    
    public String exibeDiagrama(int[] dic){
        String nomeArqCSV = new String();
        
        return nomeArqCSV;
    }
     
      
    public static void main(String[] args){
        TADdic dic = new TADdic(8);
        dic.insertItem("Teclas", new RegDados("Teclas", "Keys"));
        dic.insertItem("Palavra", new RegDados("Palavra", "Word"));
            
        dic.insertItem("Eu", new RegDados("Eu", "I"));

        System.out.println(dic.keys());
        System.out.println(dic.elements());
        dic.removeElement("Eu");
        System.out.println(dic.keys());
        System.out.println(dic.elements());
        System.out.println(dic.size());
            
    }
}
