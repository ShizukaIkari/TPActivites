/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchprojeto;

import java.util.LinkedList;

/**
 *
 * @author Serenna
 */
public class TADMatriz {
    
    private int lins;
    private int cols;
    private TADDicChain dados;
    private LinkedList<Tupla> posicoes; 
    
    /*Gets e sets automaticos*/
    public int getLins() {
        return lins;
    }

    public void setLins(int lins) {
        this.lins = lins;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public TADDicChain getDados() {
        return dados;
    }

    public void setDados(TADDicChain dados) {
        this.dados = dados;
    }

    public LinkedList<Tupla> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(LinkedList<Tupla> posicoes) {
        this.posicoes = posicoes;
    }
    
    /*Funções especificação*/
    //Construtor, cria TADMatriz com linhas e colunas passadas
    public TADMatriz(int linhas, int colunas){
        this.lins = linhas;
        this.cols = colunas;
        this.dados = new TADDicChain(null); //null faz o dicionário usar HashDefault
        this.posicoes = new LinkedList<>();
    }
    //Retorna elemento na posição i,j (caso seja uma posição válida)
    public Float getElem(int i, int j){
        Float elem = null;
        if(i<lins && j<cols){
            for (Tupla chave : posicoes) 
                if (chave.getLinhaElem()== i && chave.getColunElem()== j) 
                    elem =(Float)this.dados.findElement(chave);
        }
        return elem;
    }
    //Armazena valor na posição (i,j) da matriz, se a posição for válida
    public Float setElem(int i, int j, Float valor){
        if(i<quantLinhas() && j<quantColunas()){
            Tupla chave = this.findPosicao(i, j);
            if (chave == null) {
                chave = new Tupla(i, j);
                this.posicoes.add(chave);
            }
            if(valor!=0)
                dados.insertItem(chave, valor);
            else
                dados.insertItem(chave, null);
        }
        return valor;     
        
    }
    
    private Tupla findPosicao(int i, int j) {
        if (i<quantLinhas() && j<quantColunas()) {
            for (Tupla chave : posicoes) {
                if (chave.getLinhaElem()== i && chave.getColunElem()== j) {
                    return chave;
                }
            }
        }
        return null;
    }
    //Soma matriz atual com matriz passada, caso seja possível
    public TADMatriz soma(TADMatriz m){
        TADMatriz newMatrix = null;
        if(this.lins == m.getLins() && this.cols == m.getCols()){
            newMatrix = new TADMatriz(lins, cols);
            
        }
        return newMatrix;
    }

    /*Multiplica elementos matriz por k */
    public void vezesK(float k){
        for (Tupla chave : posicoes) {
            Float valor = (Float)this.dados.findElement(chave);
            this.dados.insertItem(chave, valor*k);
        }
    }
    
    /*Multiplica a matriz pela matriz m se possível
    retorna uma terceira matriz com o resultado da multiplicação. */
    public TADMatriz multi(TADMatriz m){
        TADMatriz nMatriz = null;
        if(this.quantColunas() == m.quantLinhas()){
            nMatriz =new TADMatriz(this.quantLinhas(), m.quantColunas());
            for (int i = 1; i <= this.quantLinhas(); i++) {
                for (int j = 1; j <= m.quantColunas(); j++) {
                    for (int k = 1; k <= this.quantColunas(); k++) {
                        nMatriz.setElem(i, j, nMatriz.getElem(i, j) + (this.getElem(i, k) * m.getElem(k, j)));
                    }
                }
            }
        }
        return nMatriz;
    }

    
    // retorna a quantidade de linhas da matriz de entrada.
    public int quantLinhas(){
        return this.lins;
    }

    //retorna a quantidade de colunas da matriz de entrada.
    public int quantColunas(){
        return this.cols;
    }
    /*Retorna uma nova matriz com a transposta da matriz corrente.*/
    public TADMatriz transposta(){
         TADMatriz matriz = new TADMatriz(cols,lins);
         
         for(int i=0;i<quantLinhas();i++){
            for(int j=0;j<quantColunas();j++){
                matriz.setElem(j, i, this.getElem(i, j));
            }
        }
        return matriz;
    }

    /*carrega uma matriz a partir de um arquivo texto de nome nome_arq. 
    Retorna a matriz equivalente a do arquivo*/
    public static TADMatriz carrega(String nome_arq){
        TADMatriz matriz = new TADMatriz(0, 0);
        return matriz;
    }

    
    public String salva(String nome_arq){
    }
    
}
