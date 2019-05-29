/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Serenna
 */

public class Apmattpa {
    public static void main(String[] args){
        TADMatriz resultado = null;
        TADMatriz aux = null;
	LinkedList<String> bdMat = new LinkedList();
	String linha;
        //Matrizes presentes no bd
	bdMat.add("J");
        bdMat.add("A");
        bdMat.add("H");
        bdMat.add("I");
        bdMat.add("M");
        bdMat.add("Q");
        
        File arquivo = new File("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdaritmat.csv");
	Scanner scanner = null;
        //Tratando exceção caso abrir scanner falhe
        try {
            scanner = new Scanner(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Percorrendo arquivo texto
	while (scanner.hasNext()) {
            linha = scanner.nextLine();
            String[] dado = linha.split(","); //Conteúdo da linha
            //Tratando conteúdo do dado
            for (int i = 0; i < dado.length; i++) {
                //Caso dado seja uma das matrizes
                if (bdMat.contains(dado[i])) {
                    try {
                        resultado = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[i] + ".txt");
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { //Operações com as matrizes
                    if (dado[i].equals("t")) {
                        aux = resultado.transposta();
                    }else if(dado[i].equals("*")){
                        if(bdMat.contains(dado[++i])){
                            String matriz = "C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[++i] + ".txt";
                            try {
                                aux = TADMatriz.carrega(matriz);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            aux = resultado.multi(aux);
                        }else{
                            resultado.vezesK(Float.parseFloat(dado[i]));
                        }
                            
                    } else{
                        String matriz = "C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[++i] + ".txt";
                        try {
                            aux = TADMatriz.carrega(matriz);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(dado[i].equals("+"))
                            aux = resultado.soma(aux);
                        else if(dado[i].equals("-")){
                            resultado.vezesK(-1);
                            aux = resultado.soma(aux);
                        }                   
                    }
                
                }
            }
            resultado = aux;
        }

        try {
            String resposta = resultado.salva("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\resultado.txt");
        } catch (IOException ex) {
            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
        }        
        System.out.println();
        System.out.println("Diagonal Principal: ");
        System.out.println(resultado.diagPrincipal());
        System.out.println();
        System.out.println("Diagonal Secundária: ");
        System.out.println(resultado.diagSecundaria());
    }
}
