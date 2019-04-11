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
    
    public long fHash2(String k){
        long soma = 0;
        for(int exp = 0; exp<k.length(); exp++){
            soma+=(int)k.charAt(exp)^exp;
        }
        return soma;
    }
    
    /**/
    public void insertItem(String k, RegDados e){
        RegDados aux = findElement(k);
        if(aux == null){
            long hashCode = hashFunction(k);
            int index = (int) hashCode % vetList.length;

            vetList[index].add(e);
            qtdEntradas++;
        }else{ 
            aux.setWen(e.getWen());
        }
    }
    /*Recebe a chave, converte para o código hash correspondente e faz a busca,
    retornando se achar*/
    public RegDados findElement(String k){
        long hashCode = hashFunction(k);
        int index = (int) hashCode % vetList.length;
        /*Acessa a lista no índice da chave
        Percorre até achar a chave em si e retorna o elemento*/
        int posLst = 0;
        while(posLst < vetList[index].size()){
            if(((RegDados)vetList[index].get(posLst)).getWpt().equals(k)){
                return (RegDados)vetList[index].get(posLst);
            }
            posLst++;
        }
        return null;
    }
    
    public LinkedList keys(){
        LinkedList chaves = new LinkedList();
        for(int i=0; i<this.getSizeList() ;i++){
            int posLst = 0;
            while(posLst < vetList[i].size()){
                chaves.add(((RegDados)vetList[i].get(posLst)).getWpt());
                posLst++;
            }
        }
        return chaves;
    }
    
     
    public LinkedList elements(){
        LinkedList elems = new LinkedList();
        for(int i=0; i<this.getSizeList() ;i++){
            int posLst = 0;
            while(posLst < vetList[i].size()){
                elems.add(((RegDados)vetList[i].get(posLst)).getWen());
                posLst++;
            }
        }
        return elems;
    }
    
    public RegDados removeElement(String k){
         RegDados aux = findElement(k);
         if(aux == null){
             return null;
         } else {
             long codHash = hashFunction(k);
             int index = (int)codHash %vetList.length;
             
             /*Acessa a lista no índice da chave
             Percorre até achar a chave em si e deleta o elemento*/
             int posLst = 0;
             while(posLst < vetList[index].size() && 
                     ((RegDados)vetList[index].get(posLst)).getWpt().equals(k)){
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
