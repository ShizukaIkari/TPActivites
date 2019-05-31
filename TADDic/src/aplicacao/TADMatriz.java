/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import hashFunctions.HashSAX;
import taddic.TADDicChain;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Serenna
 */
public class TADMatriz {
    
    private int lins;
    private int cols;
    private TADDicChain dados;
    /***************
    !! Decisões de projeto !! 
    * A matriz não armazena zeros segundo especificação. 
    * Chaves (i,j) dentro do intervalo da matriz são válidas, valor delas é 0.
    * Ao tentar adicionar um zero na matriz, o valor guardado é null.
    * O valor mínimo de linhas e colunas são 0.
    * A primeira coluna/linha é 0.
     ****************/
    
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

    /*Funções especificação*/
    //Construtor, cria TADMatriz com linhas e colunas passadas
    public TADMatriz(int linhas, int colunas){
        this.lins = linhas;
        this.cols = colunas;
        this.dados = new TADDicChain(null);
    }
    
    //Retorna elemento na posição i,j (caso seja uma posição válida)
    public Float getElem(int i, int j){
        Float elem = null;
        
        if(isPosValid(i, j)){
            String chave = i+","+j;
            elem =(Float)this.dados.findElement(chave);
            //Se retorno for nulo, ou a chave não foi encontrada ou valor encontrado é nulo
            //Nos dois casos, o valor equivalente ali é zero
            if (elem == null){
                elem = 0F;
            }
        }
        
        return elem;
    }
    //Armazena valor na posição (i,j) da matriz, se a posição for válida
    public Float setElem(int i, int j, Float valor){
        if(isPosValid(i, j)){
            String chave = i+","+j;
            
            if(valor!=0F)
                dados.insertItem(chave, valor);
            else
                dados.insertItem(chave, null);
        }
        return valor;     
    }
   
    //Soma matriz atual com matriz passada, caso seja possível
    public TADMatriz soma(TADMatriz m){
        TADMatriz newMatrix = null;
        if(this.lins == m.getLins() && this.cols == m.getCols()){
            newMatrix = new TADMatriz(lins, cols);
            for(int i = 0; i < newMatrix.lins; i++)
                for(int j = 0; j < newMatrix.cols; j++ )
                    newMatrix.setElem(i, j, this.getElem(i, j) + m.getElem(i, j));
        }
        return newMatrix;
    }

    /*Multiplica elementos matriz por k */
    public void vezesK(float k){
        //Percorre todas as chaves
        LinkedList<String> posicoes = dados.keys();
        for (String chave : posicoes) {
            
            int i = Integer.parseInt(chave.substring(0, 1));
            int j = Integer.parseInt(chave.substring(2, 3));
            Float valor = getElem(i,j);
            this.setElem(i,j, valor*k);
        }
    }
    
    /*Multiplica a matriz pela matriz m se possível
    retorna uma terceira matriz com o resultado da multiplicação. */
    public TADMatriz multi(TADMatriz m){
        TADMatriz nMatriz = null;
        //Condição necessária para uma matriz ser multiplicável
        if(this.quantColunas() == m.quantLinhas()){
            //Matriz resultante terá num de linhas da matriz corrente e de colunas da matriz multiplicada
            nMatriz =new TADMatriz(this.quantLinhas(), m.quantColunas());
            //Percorre linhas da matriz corrente
            for (int i = 1; i <= this.quantLinhas(); i++) {
                //Percorre colunas da matriz a ser multiplicada
                for (int j = 1; j <= m.quantColunas(); j++) {
                    //Elementos da coluna da matriz atual, para fazer calculo
                    for (int k = 1; k <= this.quantColunas(); k++) {
                        nMatriz.setElem(i, j, (nMatriz.getElem(i, j) + (this.getElem(i, k) * m.getElem(k, j))));
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
    public static TADMatriz carrega(String nome_arq) throws FileNotFoundException{
        File arq = new File (nome_arq);
        Scanner s = new Scanner(arq);
        String linha;
        LinkedList<String> lst = new LinkedList();
        int linhas = 0;
        int colunas = 0;
        //Percorre documento todo
        while(s.hasNextLine()) {
            linhas++;   //Núm de linhas para instanciar matriz
            linha = s.nextLine(); //Pega conteúdo da linha atual
            String[] vet = linha.split(" ");    //Split separa os valores
            //Pegando os valores, ignorando os espaços excessivos
            for (int i = 0; i < vet.length; i++) 
                if (!vet[i].isEmpty())
                    lst.addLast(vet[i]);
            //Na primeira linha processada, lst tem o mesmo size que as colunas
            if (linhas == 1) {
                colunas = lst.size();
            }
        }
        TADMatriz matriz = new TADMatriz(linhas, colunas);
        int posLst = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Float num = Float.parseFloat(lst.get(posLst));
                matriz.setElem(i, j, num);
                posLst++;
            }
        }
        return matriz;
    }

    /*Salva uma matriz em um arquivo texto de nome nome_arq. 
    Retorna o nome do arquivo usado para salvar a matriz.
    */
    public String salva(String nome_arq) throws IOException{
        File resultado = new File(nome_arq);
        FileWriter fw = new FileWriter(resultado);
        PrintWriter gravarArq = new PrintWriter(fw);
        
        //Escrevendo a matriz de acordo com a especificação
        for (int i = 0; i < this.quantLinhas(); i++) {
            for (int j = 0; j < this.quantColunas(); j++) {
                gravarArq.print(this.getElem(i, j) + " ");
            }
            gravarArq.println();

        }
        fw.close();
        return nome_arq;
    }
    
    /*Funções auxiliares*/
    
    //Checa se posições passadas estão dentro dos limites da matris
    private boolean isPosValid(int i, int j){
        return ((i >= 0 && i<this.quantLinhas()) && (j >= 0 && j<this.quantColunas()));
    }
    
    //Retorna lista com elementos da diagonal principal da matriz (se esta for matriz quadrada)
    public LinkedList diagPrincipal(){
        LinkedList diag = null;
        if (quantLinhas() == quantColunas()){
             diag = new LinkedList();
            for (int i = 0;i<quantLinhas();i++)
                for(int j = 0; j<quantColunas();j++)
                    if (i==j)
                        diag.add(this.getElem(i,j));
        }
        else
            System.out.println("Matriz não quadrada");
        return diag;
    }
    
    //Retorna lista com elementos da diagonal secundaria da matriz (se esta for matriz quadrada)
    public LinkedList diagSecundaria(){
        LinkedList diagS = null;
        if (quantLinhas() == quantColunas()){
            diagS = new LinkedList();
            int n = quantColunas()-1;
            for (int i = 0;i<quantLinhas();i++)
                for(int j = quantColunas()-1; j<=0;j--)
                    if (i+j == n)
                        diagS.add(this.getElem(i,j));
        }
        else
            System.out.println("Matriz não quadrada");
        return diagS;
    }
    
    public String toString(){
        String matriz = "[ ";
        for(int i=0; i<this.getLins();i++){
            for(int j=0;j<this.getCols();j++){
                matriz += this.getElem(i, j) + " ";
            }
            matriz+='\n';
        }
        return matriz+" ]";
    }
}
