/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafo;

import java.util.LinkedList;

/**
 *
 * @author Serenna
 */
public class TADGrafo {

    private int [][] mat = null;
    private String nome;
    private int quantVertx = 0;
    private int quantEdges = 0;
    private int geraIDedge = 1;
    private int geraIDVertex = 1;
    private int primeiroVertex = 0;
    private int ultimoVertex = 0;
    private LinkedList lstDeletados = null; //gerenciar os nodes deletados
    
    public TADGrafo(String nome){
        mat = new int[10][10];
        this.nome = nome;
        
        lstDeletados = new LinkedList();
    }
    
    /**/
    public void printmat(){
        for(int i=primeiroVertex; i<ultimoVertex; i++){
            if(lstDeletados.contains(i)){
                for(int k=0;k<mat[0].length; k++){
                    System.out.println(String.format("%d",mat[i][k]));
                }
            System.out.println();    
            }
        }
    }
    
    public int numVertices(){
        return quantVertx;
    }
    
    public int numEdges(){
        return quantEdges;
    }
    
    public boolean valido(int v){
        return((v >= primeiroVertex) && (v<=ultimoVertex) && !(lstDeletados.contains(v)));
    }
    
    public Integer getEdge(int u, int v){
        if(valido(u) && valido(v)){
            return mat[u][v];
        }
        else{
            return null;
        }
    }
    
    public int[] endVertices(int e){
        for(int i =primeiroVertex;i<=ultimoVertex;i++){
            if(valido(i)){
                for(int k=primeiroVertex;k<=ultimoVertex;k++){
                    if(mat[i][k] == e){
                        int[] v = new int[2];
                        v[0] = i;
                        v[1] = k;
                        return v;
                    }
                }
            }
        }
        
        return null;
    }
    
    public Integer opposite(int v, int e){
        if(valido(v)){
            for(int i=primeiroVertex; i<=ultimoVertex; i++){
                if(mat[v][i]==e){
                    return i;
                }
            return null;    
            }
        }
        else{
            return null;
        }
        
        return null;
    }
    
    public int outDegree(int v){
        int grau = 0;
        for(int i=primeiroVertex; i<=ultimoVertex; i++){
            if(mat[v][i] != 0 && valido(v)){
                grau++;
            }
        }
        return grau;
    }
    
    public Integer insereVertex(){
        quantVertx++;
        int id;
        
        if(lstDeletados.size() > 0){
            id = (int) lstDeletados.removeFirst();
        }
        else{
            id = geraIDVertex++;
        }
        return id;
    }
    
    public int insereEdge(int u, int v){
        if(valido(u) && valido(v)){
            mat[u][v] = geraIDedge++;
            quantEdges++;
            return mat[u][v];
            
        }
        return -1;
    }
    
    public void removeEdge(int e){
        for(int i=primeiroVertex; i<=ultimoVertex; i++){
            for(int k=primeiroVertex; k<=ultimoVertex; k++){
                if(mat[i][k] == e){
                    mat[i][k] = 0;
                    quantEdges--;
                    
                }
            }
        }
    }
    
    public Integer removeVertex(int v){
        if(valido(v)){
            lstDeletados.add(v);
            
            if(v == primeiroVertex){
                for(int i=primeiroVertex+1; i<=ultimoVertex;i++){
                    if(!lstDeletados.contains(i)){
                        primeiroVertex = i;
                        break;
                    }
                    
                }
            }
            
            if(v == ultimoVertex){
                for(int i=ultimoVertex-1; i>=primeiroVertex; i--){
                    if(!lstDeletados.contains(i)){
                        ultimoVertex = i;
                        break;
                    }
                }
            }
            
            quantVertx--;
            return v;    
        }
        else{
            return null;
        }
    }

    
}
