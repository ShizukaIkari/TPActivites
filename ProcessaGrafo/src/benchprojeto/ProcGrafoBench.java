package benchprojeto;

import java.util.LinkedList;
import procgrafo.DSDijkstra;
import procgrafo.DSFloydW;
import procgrafo.ProcessaGrafo;
import tadgrafo.TADGrafoD;
import tadgrafo.Vertex;

//Sugestão prof: pegar grafos do livro para teste
public class ProcGrafoBench {
	public static void main(String args[]) {
            /*
            TADGrafoD g = TADGrafoD.carregaTGF("arqvTeste", 1200);
            ProcessaGrafo pg = new ProcessaGrafo(g);
           //Ajustar origem
            LinkedList<Vertex> resposta = pg.bfs("origem");
            System.out.println("Resposta BFS: "+resposta.toString());
            resposta = pg.dfs("origem");
            System.out.println("Resposta DFS: "+resposta.toString());
            DSDijkstra respostaDS = pg.cmDijkstra("origem");
            System.out.println("Printar a resposta do Dijkstra");
            respostaDS = pg.cmBFord("origem");
            System.out.println("Printar resposta BFord");
            DSFloydW respostaDSF = pg.cmFWwarshall();
            System.out.println("print resposta");*/
            
            TADGrafoD g = new TADGrafoD("g");
            g.insertVertex("1", "");
            g.insertVertex("2", "");
            g.insertVertex("3", "");
            g.insertVertex("4", "");

            g.insertEdge("1", "3", "-2", -2);
            //g.getEdge("ad").setDado(7);
            g.insertEdge("3", "4", "2", 2);
            //g.getEdge("ab").setDado(3);
            g.insertEdge("2", "1", "4", 4);
            //g.getEdge("ba").setDado(8);
            g.insertEdge("2", "3", "3", 3);
            //g.getEdge("bc").setDado(2);
            g.insertEdge("4", "2", "-1", -1);
            //g.getEdge("ca").setDado(5);
            //g.insertEdge("C", "D", "cd", "");
            //g.getEdge("cd").setDado(1);
            //g.insertEdge("D", "A", "da", "");
            //g.getEdge("da").setDado(2);

            ProcessaGrafo pg = new ProcessaGrafo(g);
            for (int i = 0; i <pg.g.mat[0].length ; i++) {
                for (int j = 0; j < pg.g.mat[0].length; j++) {
                    System.out.print(pg.g.mat[i][j]+" ");
                }
                System.out.println("");
            }
            
            
            LinkedList<Vertex> bfs = pg.bfs("1");
            for(int i =0; i<bfs.size();i++){
                
                System.out.println(bfs.get(i).getLabel()+",");
               
            }
            
            LinkedList<Vertex> dfs = pg.dfs("1");
            for(int i =0; i<dfs.size();i++){
                System.out.println(dfs.get(i).getLabel()+",");
               
            }
            
	} /* fim de main */

} /* fim da classe ProcGrafoBench */
