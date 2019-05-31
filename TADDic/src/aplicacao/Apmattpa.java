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
            
            if(dado.length >= 1 && !bdMat.contains(dado[0])){
            //Tratando conteúdo do dado
                switch (dado[0]){
                    case "-":
                        try {
                            aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[1] + ".txt");
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        aux.vezesK(-1);
                        System.out.println("Matriz carregada * -1: " + dado[1]);
                        System.out.println(aux.toString());
                        
                        aux = resultado.soma(aux);
                        System.out.println("---------------- Subtraida ----------------");
                        System.out.println(aux.toString());
                        break;

                    case "+":
                        try {
                            aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[1] + ".txt");
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("Matriz carregada: " + dado[1]);
                        System.out.println(aux.toString());
                        aux = resultado.soma(aux);
                        System.out.println("================ Somada a resultado ----------------");
                        System.out.println(aux.toString());
                        break;

                    case "t":
                        aux = aux.transposta();
                        System.out.println("---------------- Transposta ----------------");
                        System.out.println(aux.toString());
                        break;

                    case "*":
                        if (bdMat.contains(dado[1])) { 
                            try {
                                aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[1] + ".txt");
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("Matriz carregada: " + dado[1]);
                            System.out.println(aux.toString());
                            System.out.println("Resultado: ");
                            System.out.println(resultado.toString());
                            aux = resultado.multi(aux);
                            System.out.println("---------------- Multiplicada ----------------");
                            System.out.println(aux.toString());
                        } else {
                            resultado.vezesK(Float.parseFloat(dado[1]));
                            aux = resultado;
                            System.out.println("---------------- VezesK ----------------");
                            System.out.println(aux.toString());

                        }
                        break;
                }//Fim switch
            } else { 
                try {
                    aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\TADDic\\src\\aplicacao\\bdmatrizes/" + dado[0] + ".txt");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Matriz carregada no else: " + dado[0]);
            }
            resultado = aux;
            System.out.println("Resultado: " + resultado.toString());
            
        } // Fim while arquivo

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
