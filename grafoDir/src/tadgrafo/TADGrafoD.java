/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import taddic.TADDicChain;

/**
 *
 * @author Serenna
 */
public class TADGrafoD {
    //matriz guarda id da aresta
    private int [][] mat = null; //representando o grafo através de matriz de adjacencia
    private String nome;    //nome escolhido para o grafo
    private int quantVertices = 0;    //contagem de vertices
    private int quantEdges = 0;     //contagem arestas
    private int geraIDedge = 1;     //atributo para gerenciar IDs das arestas
    private int geraIDvertice = 1;//atributo para gerenciar IDs dos vertices
    private int primVertice = 0;    //aponta para o primeiro vertice
    private int ultiVertice = 0;    //aponta para o ultimo vértice
    private LinkedList<Integer> lstEliminados = null; //lista com ids deletados, para posterior reciclagem 
    private TADDicChain dicLblVertex = new TADDicChain(null);   //guarda label dos vértices, no formato label : idVertice
    private TADDicChain dicLblEdge = new TADDicChain(null); //guarda label das arestas, no formato label : idAresta
    
    public TADGrafoD(String nome){
        mat = new int[16][16];
        this.nome = nome;
        
        lstEliminados = new LinkedList();
    }
    
    //Construtor determinando tamanho da matriz
    public TADGrafoD(String nome, int tamMx){
        mat = new int[tamMx][tamMx];
        this.nome = nome;
        
        lstEliminados = new LinkedList();
    }
    /*Retorna quantidade de vértices*/
    public int numVertices(){
        return quantVertices;
    }
    /*Retorna quantidade de arestas*/
    public int numEdges(){
        return quantEdges;
    }
    /*Verifica se vertice passado se enquadra nas condições
    */
    public boolean valido(int v){
        return((v >= primVertice) && (v<=ultiVertice) && !(lstEliminados.contains(v)));
    }
    
    
    //Retorna objeto edge com label associado 
    public Edge getEdge(String lblEdge){
        Edge e = (Edge)dicLblEdge.findElement(lblEdge);
        if(!dicLblEdge.NO_SUCH_KEY()){
            return e;
        }
        return null;
    }
    //Retorna a aresta que conecta os vertices passados (label deles)
    public Edge getEdge(String origem, String destino) {
        //Procura o vértice de destino, se não encontrar, retorna null
        Vertex vDestino = (Vertex) dicLblVertex.findElement(destino);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        //Procura o vértice de origem, se não encontrar, retorna null
        Vertex vOrigem = (Vertex) dicLblVertex.findElement(origem);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        //Caso origem e destino existam, acessa o id da aresta na matriz do grafo
        int idEdge = mat[vOrigem.getId()][vDestino.getId()];
        //Se for zero, origem e destino passados não tem nenhuma aresta conectando
        if (idEdge == 0) {
            return null;
        } else {
            LinkedList<Object> lstEdgeKeys = dicLblEdge.keys();

            for (int i = 0; i < lstEdgeKeys.size(); i++) {
                Edge e = (Edge) dicLblEdge.findElement(lstEdgeKeys.get(i));
                //id do edge deve ser igual ao id do vertice de origem
                if (vOrigem.getId() == idEdge) {
                    return e;
                }
            }
        }

        return null;
    }
    
    //Retorna o vertice pedido
    public Vertex getVertex(String label) {
        //Consulta no dicionário de labels
	Vertex v = (Vertex)dicLblVertex.findElement(label);
	if(dicLblVertex.NO_SUCH_KEY()) 
            return null;
        else 
            return v;
}
    //Retorna o nome do grafo
    public String getNome(){
        return nome;
    }
    
    //Lista com todos os vértices do grafo
    public LinkedList<Vertex> vertices() {
        LinkedList<Vertex> vx = new LinkedList();
        //pega os objetos Vertice dentro do dicionario
        LinkedList<Object> allvertex = dicLblVertex.elements();
        for (int i = 0; i < allvertex.size(); i++) {
            vx.add((Vertex) allvertex.get(i));
        }
        return vx;
    }
    
    //Lista com todas as arestas
    public LinkedList<Edge> edges() {
        LinkedList<Edge> es = new LinkedList();
        LinkedList<Object> edges = dicLblEdge.elements();
        for (int i = 0; i < edges.size(); i++) {
            Edge ed = (Edge) edges.get(i);
            es.add(ed);
        }
        return es;
    }
    
    /*Método para exibir o estado da matriz do grafo*/
    public void printmat(){
        for(int i=primVertice; i<ultiVertice; i++){
            //Se não estiver eliminado, será exibido
            if(!lstEliminados.contains(i)){
                for(int k=0;k<mat[0].length; k++){
                    System.out.println(String.format("%d",mat[i][k]));
                }
            System.out.println();    
            }
        }
    }
    
    //Imprime o grafo
    public void printgrafo() {
        ArrayList<String> al = new ArrayList<>();
        String s;
        String labelOrigem = "", labelDestino = "", labelEdge = "";
        LinkedList<Object> lstVs = dicLblVertex.keys(); //Todos os labels dos vértices
        LinkedList<Object> lstEs = dicLblEdge.keys();   //Todos os labels das arestas
        Vertex v;  
        Edge e;
        
        //Percorrendo todos os ids dos vértices do grafo
        for(int i = primVertice; i <= ultiVertice; i++) {
            s = ""; //inicializa a variável
            
            //Caso o vertex não tenha sido excluido
            if(!lstEliminados.contains(i)) {
                //Percorre lista de labels dos vértices
                for(int j = 0; j < lstVs.size(); j++) {
                    v = (Vertex)dicLblVertex.findElement(lstVs.get(j));
                    //i assumirá o valor de cada um dos vértices
                    //verifica se o id do vértice é igual ao id do i no momento
                    if(v.getId() == i) {
                        //Guarda o label do vértice
                        labelOrigem = v.getLabel();
                        break;
                    }
                }
                //Percorre novamente todos os ids dos vértices para achar o vértice de destino
                for(int k = primVertice; k <= ultiVertice; k++) {
                    //id não pode ter sido eliminado
                    if(!lstEliminados.contains(k)) {
                        //Percorre novamente label de todos os vértices, 
                        for(int m = 0; m < lstVs.size(); m++) {
                            v = (Vertex)dicLblVertex.findElement(lstVs.get(m));
                            if(v.getId() == k) {
                                labelDestino = v.getLabel();
                                break;
                            }
                        }
                        
                        int idEdge = mat[i][k];
                        
                        if(idEdge != 0) {
                            for(int m = 0; m < lstEs.size(); m++) {
                                e = (Edge)dicLblEdge.findElement(lstEs.get(m));
                                if(e.getId() == idEdge) {
                                    labelEdge = e.getLabel();
                                    break;
                                }
                            }
                            
                            s = labelOrigem + "--" + labelEdge + "-->" + labelDestino;
                            al.add(s);
                        }
                    }
                }
            }
        } //End for 
        //Island vertex treatment (quando nenhum vértice aponta para o vertice)
        for (int i = 0; i < lstVs.size(); i++) {
            String lbl = (String) lstVs.get(i);
            if (degree(lbl) == 0) {
                al.add(lbl);
            }
        }

        Collections.sort(al);
        for (int n = 0; n < al.size(); n++) 
            System.out.println(al.get(n));
        
    }
    
    //Retorna os vértices origem/destino da aresta passada
    public Vertex[] endVertices(String labelE) {
        Edge orgE = (Edge) dicLblEdge.findElement(labelE);
        if (dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        
        int idE = orgE.getId();
        //i assume ids dos vertices validos
        for (int i = primVertice; i <= primVertice; i++) {
            if (valido(i)) {
                for (int k = primVertice; k <= primVertice; k++) {
                    //Ao encontrar o id da aresta procurada
                    if (mat[i][k] == idE) {
                        Vertex[] v = new Vertex[2];
                        v[0] = intToVertex(i);
                        v[1] = intToVertex(k);
                        return v;
                    }
                }
            }
        }

        return null;
    }
    
    //Retorna o vértice que está conectado a aquela aresta e aquele vértice
    public Vertex opposite(String v, String e) {
        //verifica se vértice passado é valido
        Vertex vertice = (Vertex) dicLblVertex.findElement(v);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        //verifica se aresta é válida
        Edge aresta = (Edge) dicLblEdge.findElement(e);
        if (dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        //percorre id dos vértices
        for (int i = primVertice; i <= primVertice; i++) {
            //Se id não estiver entre os deletados e o id na matriz for o id da aresta
            if ((!lstEliminados.contains(i)) && (mat[vertice.getId()][i] == aresta.getId())) {
                LinkedList<Object> lstVs = dicLblVertex.keys(); //pega todos os labels 
                //percorre a lista de labels, até achar o label 
                for (int k = 0; k < lstVs.size(); k++) {
                    Vertex vOp = (Vertex) dicLblVertex.findElement(lstVs.get(k));
                    if (vOp.getId() == i) {
                        return vOp;
                    }
                }
            }
        }

        return null;
    }
    //Retorna a quantidade de arestas que tem o vértice como origem
    public Integer outDegree(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        } else {
            
            int line = v.getId();
            int grade = 0;
            //
            for (int i = primVertice; i <= ultiVertice; i++) {
                if ((mat[line][i] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }

            return grade;
        }
    }
    
    //Retorna a quantidade de arestas que tem o vértice passado como destino 
    public Integer inDegree(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        } else {
            int line = v.getId();
            int grade = 0;

            for (int i = primVertice; i <= primVertice; i++) {
                if ((mat[i][line] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }

            return grade;
        }
    }
    
    //Retorna a quantidade de arestas relacionadas ao vértice
    public Integer degree(String label) {
        Integer in = inDegree(label);
        Integer out = outDegree(label);

        if ((in == null) || (out == null)) {
            return null;
        } else {
            return in + out;
        }
    }
    
    //Insere um Vértice no grafo ... dúvida
    public Vertex insertVertex(String label, Object dado) {
        int idVertex = geraIDVertex();  //gera ID do vertice

        if (idVertex > primVertice) {
            primVertice = idVertex;
        }

        if (idVertex < primVertice) {
            primVertice = idVertex;
        }

        Vertex v = (Vertex) dicLblVertex.findElement(label);

        //Including a new vertex
        if (dicLblVertex.NO_SUCH_KEY()) {
            v = new Vertex(label, dado);
            v.setId(idVertex);
            dicLblVertex.insertItem(label, v);
            quantVertices++;
        } else { //updating a existent vertex
            v.setDado(dado);
        }

        return v;
    }
    
    //Insere uma aresta no grafo, passando vértice de origem, destino, label e o dado
    public Edge insertEdge(String origem, String destino, String label, Object o) {
        Vertex vOrigem = (Vertex) dicLblVertex.findElement(origem);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }

        Vertex vDestino = (Vertex) dicLblVertex.findElement(destino);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }

        Edge e = (Edge) dicLblEdge.findElement(label);

        //Inclusion of a new arch
        if (dicLblEdge.NO_SUCH_KEY()) {
            e = new Edge(label, o);
            e.setId(geraIDedge++);

            dicLblEdge.insertItem(label, e);

            mat[vOrigem.getId()][vDestino.getId()] = e.getId();
            quantEdges++;
        } //Update of a existent arch
        else {
            e.setDado(o);
        }

        return e;
    }
    
    public Object removeEdge(String edge) {
        Edge e = (Edge) dicLblEdge.findElement(edge);
        if (dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }

        int idE = e.getId();

        for (int i = primVertice; i <= primVertice; i++) {
            if (!lstEliminados.contains(i)) {
                for (int j = primVertice; j <= primVertice; j++) {
                    if (mat[i][j] == idE) {
                        mat[i][j] = 0;
                        quantEdges--;
                        dicLblEdge.removeElement(edge);
                        return e.getDado();
                    }
                }
            }
        }

        /* Anomalia: o arco de label existe mas o seu id não se encontra */
        return null;
    }
    
    public Object removeVertex(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }

        int idV = v.getId();

        if (idV == primVertice) {
            for (int i = primVertice + 1; i <= primVertice; i++) {
                if (!lstEliminados.contains(i)) {
                    primVertice = i;
                    break;
                }
            }
        }

        if (idV == primVertice) {
            for (int i = primVertice + 1; i <= primVertice; i++) {
                if (!lstEliminados.contains(i)) {
                    primVertice = i;
                    break;
                }
            }
        }

        for (int i = primVertice; i <= primVertice; i++) {
            //Fill removed vertex line with 0's that means the vertex does not exist
            if (mat[idV][i] != 0) {
                quantEdges--;
                mat[idV][i] = 0;
            }

            //Fill removed vertex column with 0's that menas the vertex does not exist
            //Also prevent from decrementing quantEdges already decremented
            if ((mat[i][idV] != 0) && (mat[idV][i] != mat[i][idV])) {
                quantEdges--;
                mat[i][idV] = 0;
            }
        }

        quantVertices--;
        lstEliminados.add(idV);
        return dicLblVertex.removeElement(label);
    }
    
    //Retorna true caso vértices passados sejam adjacentes
    public boolean areAdjacent(String origem, String destino){
        Vertex vOrig = (Vertex)dicLblVertex.findElement(origem);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }
        
        Vertex vDest = (Vertex)dicLblVertex.findElement(destino);
        if(dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }
        
        return mat[vOrig.getId()][vDest.getId()] != 0;
    }
    
    //Retorna vértice de destino da aresta passada, caso exista
    public Vertex destination (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)
            return vet[1];
    	else
            return null;
    }
    
    //Retorna vértice de origem da aresta passada, caso exista
    public Vertex origin (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)
            return vet[0];
    	else
            return null;
    }
    
    //recebe o id do vertex e retorna o objeto vertice
    public Vertex intToVertex(int id) {
        LinkedList<Object> lst = dicLblVertex.elements();
        for (int i = 0; i < lst.size(); i++) {
            Vertex v = (Vertex) lst.get(i);
            if (id == v.getId()) {
                return v;
            }
        }
        return null;
    }
    //recebe o id do vertex e retorna o objeto edge
    public Edge intToEdge(int id) {
        LinkedList<Object> lst = dicLblEdge.elements();
        for (int i = 0; i < lst.size(); i++) {
            Edge e = (Edge) lst.get(i);
            if (id == e.getId()) {
                return e;
            }
        }
        return null;
    }
    
    public LinkedList<Edge> inIncidentEdges (String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
    	LinkedList<Edge> lst = new LinkedList <>();
    	int id = v.getId();
    	
    	for(int k = primVertice; k<= primVertice; k++)
    		if((!lstEliminados.contains(k) && (mat[id][k] != 0)))
    			lst.add(intToEdge(mat[id][k]));
    	return lst;
    }
    
    public LinkedList<Edge> outIncidentEdges (String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
    	LinkedList<Edge> lst = new LinkedList <>();
    	int id = v.getId();
    	
    	for(int i = primVertice; i<= primVertice; i++)
    		if((!lstEliminados.contains(i) && (mat[i][id] != 0)))
    			lst.add(intToEdge(mat[i][id]));
    	return lst;
    }
    
    //Retorna lista com vertices que saem do vértice com o label passado
    public LinkedList<Vertex> outAdjacenteVertices(String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
        //lista de saida
    	LinkedList<Vertex> lst = new LinkedList<>();
    	int id = v.getId();
    	for(int j = primVertice; j<= primVertice; j++){
            //Se não estiver deletado e existir uma aresta, coloca na lista
            if(!lstEliminados.contains(j) && (mat[id][j] != 0)){
                lst.add(intToVertex(j));
            }
        }
    		
    	return lst;
    }
    
    //Retorna todos os vértices adjacentes (que possuem arestas conectando-os)
    public LinkedList<Vertex> adjacentVertices(String labelV) {
        //"Soma" dos vértices de saída e entrada.
        LinkedList<Vertex> lst = new LinkedList();
        lst.addAll(inAdjacenteVertices(labelV));
        lst.addAll(outAdjacenteVertices(labelV));
        return lst;

    }
    
    public LinkedList<Vertex> inAdjacenteVertices(String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()) {
    		//System.out.println("OUT OF BOUND");
    		return null;
    	}
    	LinkedList<Vertex> lst = new LinkedList<Vertex>();
    	int id = v.getId();
    	for(int k = primVertice; k<= primVertice; k++)
    		if(!lstEliminados.contains(k) && (mat[id][k] != 0))
    			lst.add(intToVertex(k));
    	return lst;
    }
    
   //Retorna todas as arestas incidentes (que entram/saem) do vértice
    public LinkedList<Edge> incidentEdges(String labelV){
    	LinkedList<Edge> lst = inIncidentEdges(labelV);
    	lst.addAll(outIncidentEdges(labelV));
    	return lst;
    }
    
    //Faz o controle de ids dos vértices
    private int geraIDVertex() {
        int id;
       //Caso não tenha nenhum eliminado,      
        if (lstEliminados.isEmpty()) {
            id = geraIDvertice++;
        } else {
            id = lstEliminados.get(0);
            lstEliminados.remove();
        }
        //Caso o id seja menor que o id do primeiro vértice
        //O id gerado se torna o primeiro 
        if (id < primVertice) {
            primVertice = id;
        }
        //Analogamente, o id gerado se torna o último
        if (id > ultiVertice) {
            ultiVertice = id;
        }

        return id;
    }
    
    public static TADGrafoD carregaTGF(String fileName, int matSize) {
        TADGrafoD g = new TADGrafoD(fileName, matSize);
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader arq = new BufferedReader(fileReader);
            String linha = arq.readLine();
            boolean arestas = false;
            while (linha != null) {
                if (linha.contains("#")) {
                    arestas = true;
                    linha = arq.readLine();
                }
                String[] l = linha.split(" ");
                if (!arestas) {
                    String value = "";
                    for (int i = 1; i < l.length; i++) {
                        value += " " + l[i];
                    }
                    g.insertVertex(l[0], value);
                } else {
                    String edgeLabel = l[0] + '-' + l[1];
                    g.insertEdge(l[0], l[1], edgeLabel, "");
                }
                linha = arq.readLine();
            }
            arq.close();
            return g;

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    
    //Cria novo dicionário com mesmos dados do 
    public TADGrafoD clone() {
        TADGrafoD clone = new TADGrafoD("cloned"+this.getNome(), this.numVertices());
        LinkedList<Vertex> vertices = this.vertices();
        for (Vertex vertice : vertices) {
            clone.insertVertex(vertice.getLabel(), vertice.getDado());
        }
        for (int i = primVertice; i <= ultiVertice; i++) {
            for (int j = primVertice; j <= ultiVertice; j++) {
                if (mat[i][j] != 0 && !lstEliminados.contains(j)) {
                    String origem = this.intToVertex(i).getLabel();
                    String destino = this.intToVertex(j).getLabel();
                    Edge edge = this.intToEdge(mat[i][j]);
                    clone.insertEdge(origem, destino, edge.getLabel(), edge.getDado());
                }
            }
        }
        return clone;

    }
}
