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
    private TADDicChain dicLblVertex = new TADDicChain(null);   //guarda label dos vértices, no formato label : vertex
    private TADDicChain dicLblEdge = new TADDicChain(null); //guarda label das arestas, no formato label : edge
    
    public TADGrafoD(String nome){
        mat = new int[16][16];
        this.nome = nome;
        
        lstEliminados = new LinkedList();
    }
    
    //Construtor determinando tamanho da matriz
    public TADGrafoD(String nome, int totalNos){
        mat = new int[totalNos][totalNos];
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
            Edge edg = this.intToEdge(idEdge);
            return edg;
        }
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
        LinkedList<Object> allvertex = dicLblVertex.elements();
        
        for (int i = 0; i < allvertex.size(); i++) {
            vx.add((Vertex) allvertex.get(i));
        }
        return vx;
    }
    
    //Lista com todas as arestas
    public LinkedList<Edge> edges() {
        LinkedList<Edge> allEdges = new LinkedList();
        LinkedList<Object> lblEdges = dicLblEdge.elements();
        
        for(Object ed : lblEdges){
            allEdges.add((Edge)ed);
        }
        return allEdges;
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
                    //i assumirá o valor de cada id dos vértices
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
        
        for (int i = primVertice; i <= ultiVertice; i++) {
            if (!(lstEliminados.contains(i))) {
                for (int j = primVertice; j <= ultiVertice; j++) {
                    //Ao encontrar o id da aresta procurada
                    if (mat[i][j] == idE) {
                        Vertex[] v = new Vertex[2];
                        v[0] = intToVertex(i);
                        v[1] = intToVertex(j);
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
        for (int i = primVertice; i <= ultiVertice; i++) {
            //Se id não estiver entre os deletados e o id na matriz for o id da aresta
            if ((!lstEliminados.contains(i)) && (mat[vertice.getId()][i] == aresta.getId())) {
                Vertex vOp = intToVertex(i); //Modularização do código
                return vOp;
            }
        }

        return null;
    }
    //Retorna a quantidade de arestas que tem o vértice como origem
    public Integer outDegree(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        //Verifica se label passado existe
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        } else {
            int linha = v.getId(); 
            int qtdEd = 0;
            
            for (int i = primVertice; i <= ultiVertice; i++) {
                //Caso o id não esteja deletado e se houver um id saindo naquela posiçao
                if ((mat[linha][i] != 0) && !lstEliminados.contains(i)) {
                    qtdEd++;
                }
            }
            return qtdEd;
        }
    }
    
    //Retorna a quantidade de arestas que tem o vértice passado como destino 
    public Integer inDegree(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        } else {
            int line = v.getId();
            int qtdEd = 0;

            for (int i = primVertice; i <= ultiVertice; i++) {
                if ((mat[i][line] != 0) && !lstEliminados.contains(i)) {
                    qtdEd++;
                }
            }
            return qtdEd;
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
        Vertex vtx = (Vertex) dicLblVertex.findElement(label);
            
        //Caso não exista o label passado, cria-se novo vértice
        if (dicLblVertex.NO_SUCH_KEY()) {
            int idVertex = geraIDVertex();  //gera ID do vertice, gerenciando se é o primeiro ou ultimo
            vtx = new Vertex(label, dado);
            vtx.setId(idVertex);
            dicLblVertex.insertItem(label, vtx);
            quantVertices++;
        } else { //Atualiza dado do vértice passado
            vtx.setDado(dado);
        }
        return vtx;
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
        //Verifica se label já existe
        Edge e = (Edge) dicLblEdge.findElement(label);
        //Senão existir, adiciona o novo
        if (dicLblEdge.NO_SUCH_KEY()) {
            e = new Edge(label, o);
            e.setId(geraIDedge++);
            dicLblEdge.insertItem(label, e);
            this.mat[vOrigem.getId()][vDestino.getId()] = e.getId();
            quantEdges++;
        } //Se existir, atualiza com o dado passado
        else {
            e.setDado(o);
        }

        return e;
    }
    
    //Remove aresta referente ao label passado, retornando o que foi deletado
    public Object removeEdge(String edge) {
        Edge e = (Edge) dicLblEdge.findElement(edge);
        if (dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        int idE = e.getId();

        for (int i = primVertice; i <= ultiVertice; i++) {
            if (!lstEliminados.contains(i)) {
                for (int j = primVertice; j <= ultiVertice; j++) {
                    if (mat[i][j] == idE) {
                        mat[i][j] = 0;
                        quantEdges--;
                        dicLblEdge.removeElement(edge);
                        return e.getDado();
                    }
                }
            }
        }
        return null;
    }
    
    //Remove vértice com label correspondente
    public Object removeVertex(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }
        int idV = v.getId();
        //Se for primeiro vértice, pega o próximo id disponivel e seta como novo primeiro
       if (idV == primVertice) {
            for (int i = primVertice + 1; i <= ultiVertice; i++) {
                if (!lstEliminados.contains(i)) {
                    primVertice = i;
                    break;
                }
            }
        }
       //Se for ultimo vértice, pega o id anterior diponivel e seta como novo ultimo
        if (idV == ultiVertice) {
            for (int i = primVertice + 1; i <= ultiVertice; i++) {
                if (!lstEliminados.contains(i)) {
                    ultiVertice = i;
                    break;
                }
            }
        }
        //"No meio"
        for (int i = primVertice; i <= ultiVertice; i++) {
            //Apaga todos os ids de arestas relacionados a aquele vertice
            if (mat[idV][i] != 0) {
                quantEdges--;
                mat[idV][i] = 0;
            }

            //Zera ids da coluna do vértice removido
            if ((mat[i][idV] != 0) && (mat[idV][i] != mat[i][idV])) {
                quantEdges--;
                mat[i][idV] = 0;
            }
        }
        //Decrementa a qtd de vertices e adiciona seu id na lista de eliminados
        quantVertices--;
        lstEliminados.add(idV);
        return dicLblVertex.removeElement(label); //retorna o objeto removendo do dicionario
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
    public Vertex origin(String labelE){
    	Vertex[] vet = endVertices(labelE);
    	if(vet!= null)
            return vet[0];
    	else
            return null;
    }
    
    //recebe o id do vertex e retorna o objeto vertice
    public Vertex intToVertex(int id){
        //pega todos os ids dentro do dic
        LinkedList<Object> lst = dicLblVertex.elements();
        for (int i = 0; i < lst.size(); i++){
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
    
    /*Descobre e retorna todas as arestas que chegam ao vértice de label lbl.*/
    public LinkedList<Edge> inIncidentEdges (String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()){
            return null;
        }
    	LinkedList<Edge> lstIEdgs = new LinkedList <>();
    	int id = v.getId();
    	
    	for(int i = primVertice; i<= primVertice; i++){
            if((!lstEliminados.contains(i) && (mat[i][id] != 0)))
    		lstIEdgs.add(intToEdge(mat[i][id]));
        }
    	return lstIEdgs;
    }
    
    /*Descobre e retorna todas as arestas que saem do vértice de label lbl.*/
    public LinkedList<Edge> outIncidentEdges (String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()){
            return null;
        }
    	LinkedList<Edge> lstOEdgs = new LinkedList <>();
    	int id = v.getId();
    	
    	for(int i = primVertice; i<= primVertice; i++){
            if((!lstEliminados.contains(i) && (mat[i][id] != 0)))
                lstOEdgs.add(intToEdge(mat[id][i]));
        }
    	return lstOEdgs;
    }
    
    //Retorna lista com vertices que saem do vértice com o label passado
    public LinkedList<Vertex> outAdjacenteVertices(String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()){
            return null;
        }
    	LinkedList<Vertex> lstOutV = new LinkedList<>();
    	int id = v.getId();
        
    	for(int j = primVertice; j<= primVertice; j++){
            //Se não estiver deletado e existir uma aresta, coloca na lista
            if(!lstEliminados.contains(j) && (mat[id][j] != 0)){
                lstOutV.add(intToVertex(j));
            }
        }	
    	return lstOutV;
    }
    
    //Retorna todos os vértices adjacentes (que possuem arestas conectando-os)
    public LinkedList<Vertex> adjacentVertices(String labelV) {
        //"Soma" dos vértices de saída e entrada.
        LinkedList<Vertex> lst = new LinkedList();
        lst.addAll(inAdjacenteVertices(labelV));
        lst.addAll(outAdjacenteVertices(labelV));
        return lst;
    }
    
    /*Descobre e retorna os vértices origem das arestas que entram no vértice de label lbl.*/
    public LinkedList<Vertex> inAdjacenteVertices(String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY()) {
            return null;
    	}
    	LinkedList<Vertex> lstInV = new LinkedList<>();
    	int id = v.getId();
        
    	for(int i = primVertice; i<= primVertice; i++){
            if(!lstEliminados.contains(i) && (mat[i][id] != 0))
    		lstInV.add(intToVertex(i));
        }
    	return lstInV;
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
            id = lstEliminados.remove();
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
    
    /*Função estática que retorna um tad grafo dirigido a partir dos dados armazenados no arquivo de nome nome_arq_tgf.*/
    public static TADGrafoD carregaTGF(String  nome_arq_tgf, int matSize) {
        //instancia o grafo de saída
        TADGrafoD grafo = new TADGrafoD(nome_arq_tgf, matSize);
        try {
            FileReader fileReader = new FileReader(nome_arq_tgf);
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
                    grafo.insertVertex(l[0], value);
                } else {
                    String edgeLabel = l[0] + '-' + l[1];
                    grafo.insertEdge(l[0], l[1], edgeLabel, "");
                }
                linha = arq.readLine();
            }
            arq.close();
            return grafo;

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    //Cria novo grafo com mesmos dados do grafo pedido
    public TADGrafoD clone() {
        //novo grafo terá nome do que está sendo clonado + cloned e o mesmo tamanho alocado
        TADGrafoD gClone = new TADGrafoD("cloned"+this.getNome(),this.mat[0].length);
        for (int i = primVertice; i <= ultiVertice; i++) {
            for (int k = primVertice; k <= ultiVertice; k++) {
                //Verifica se o id não está eliminado e se há uma aresta
                if (!lstEliminados.contains(k) && (mat[i][k] != 0)) {
                    Vertex chave = intToVertex(k);
                    gClone.insertVertex(chave.getLabel(), chave.getDado());
                    Edge e = intToEdge(mat[i][k]);
                    String origem = this.intToVertex(i).getLabel();
                    String destino = this.intToVertex(k).getLabel();
                    gClone.insertEdge(origem, destino, e.getLabel(), e.getDado());
                }
            }
        }
        return gClone;
    }
    
    //Função que compara dois grafos, retornando true se forem iguais e false se forem diferentes
    public boolean equals​(TADGrafoD grap2){
        if (this.numEdges() == grap2.numEdges() && this.numVertices() == grap2.numVertices()){
            //Percorre todos os ids
            for (int i = primVertice; i <= ultiVertice; i++){
                for (int k = primVertice; k <= ultiVertice; k++){                    
                    LinkedList<Vertex> g2vertex = grap2.vertices(); //Lista para ser percorrida para comparar os vertices
                    for (Vertex vertex : g2vertex) {
                        //Verifica para cada vertex do outro grafo, se há o mesmo vertex neste grafo
                        Vertex v = (Vertex) this.dicLblVertex.findElement(vertex.getLabel());
                        if (dicLblVertex.NO_SUCH_KEY()) {
                            return false;
                        }
                        if (!vertex.equals(v)) {
                            return false;
                        }
                    }
                    LinkedList<Edge> g2edges = grap2.edges();   //Lista para ser percorrida para comparar as arestas
                    for (Edge edge : g2edges) {
                        //Mesma lógica que dos vértices
                        Edge e = (Edge) this.dicLblEdge.findElement(edge.getLabel());
                        if (dicLblEdge.NO_SUCH_KEY()) {
                            return false;
                        }
                        if (!edge.equals(e)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
