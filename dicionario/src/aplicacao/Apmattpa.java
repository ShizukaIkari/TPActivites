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
        String linha;
        //Matrizes presentes no bd, para verificação rápida
        LinkedList<String> bdMat = new LinkedList();
	bdMat.add("J");
        bdMat.add("A");
        bdMat.add("H");
        bdMat.add("I");
        bdMat.add("M");
        bdMat.add("Q");
        
        File arquivo = new File("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\dicionario\\src\\aplicacao\\bdaritmat.csv");
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
            //Se tiver 2 resultados ou se for o t
            if(dado.length >= 1 && !bdMat.contains(dado[0])){
            //Tratando conteúdo do dado
                switch (dado[0]){
                    case "-":
                        try {
                            aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\dicionario\\src\\aplicacao\\bdmatrizes/" + dado[1] + ".txt");
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //Truque de matematica
                        aux.vezesK(-1);
                        aux = resultado.soma(aux);
                        break;

                    case "+":
                        try {
                            aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\dicionario\\src\\aplicacao\\bdmatrizes/" + dado[1] + ".txt");
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        aux = resultado.soma(aux);
                        break;

                    case "t":
                        aux = aux.transposta();
                        break;

                    case "*":
                        if (bdMat.contains(dado[1])) { 
                            try {
                                aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\dicionario\\src\\aplicacao\\bdmatrizes/" + dado[1] + ".txt");
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            aux = resultado.multi(aux);
                        } else {
                            resultado.vezesK(Float.parseFloat(dado[1]));
                            aux = resultado;
                        }
                        break;
                }//Fim switch
            } else { 
                try {
                    aux = TADMatriz.carrega("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\dicionario\\src\\aplicacao\\bdmatrizes/" + dado[0] + ".txt");
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
            resultado = aux;    //transferindo resultado            
        } // Fim while arquivo
        
        //Salvando matriz final no arquivo pedido
        try {
            String resposta = resultado.salva("C:\\Users\\Serenna\\Documents\\GitHub\\TPActivites\\dicionario\\src\\aplicacao\\resultado.txt");
        } catch (IOException ex) {
            Logger.getLogger(Apmattpa.class.getName()).log(Level.SEVERE, null, ex);
        }        
        //Prints pedidos pela especificação
        System.out.println("Diagonal Principal: ");
        System.out.println(resultado.diagPrincipal());
        System.out.println("Diagonal Secundária: ");
        System.out.println(resultado.diagSecundaria());
    }
}
