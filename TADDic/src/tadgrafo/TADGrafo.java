/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgrafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import taddic.TADDicChain;

/**
 *
 * @author Serenna
 */
public class TADGrafo {

    private int [][] mat = null;
    private String nome;
    private int quantVertices = 0;
    private int quantEdges = 0;
    private int geraIDedge = 1;
    private int geraIDvertice = 1;
    private int primVertice = 0;
    private int ultiVertice = 0;
    private LinkedList<Integer> lstEliminados = null; //gerenciar os nodes deletados
    private TADDicChain dicLblVertex = new TADDicChain(null);
    private TADDicChain dicLblEdge = new TADDicChain(null);
    
    public TADGrafo(String nome){
        mat = new int[16][16];
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
    Obs.: Com mudanças, aparentemente não é mais necessário rç*/
    public boolean valido(int v){
        return((v >= primVertice) && (v<=ultiVertice) && !(lstEliminados.contains(v)));
    }
    
    //Retorna o Vertice na posição passada da matriz
    public Edge getEdge(String origem, String destino) {
        Vertex vDestino = (Vertex) dicLblVertex.findElement(destino);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }

        Vertex vOrigem = (Vertex) dicLblVertex.findElement(origem);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }

        int idEdge = mat[vOrigem.getId()][vDestino.getId()];

        if (idEdge == 0) {
            return null;
        } else {
            LinkedList<Object> lstEdgeKeys = dicLblEdge.keys();

            for (int i = 0; i < lstEdgeKeys.size(); i++) {
                Edge e = (Edge) dicLblEdge.findElement(lstEdgeKeys.get(i));
                if (vOrigem.getId() == idEdge) {
                    return e;
                }
            }
        }

        return null;
    }
    
    //Retorna o nome do grafo?
    public String getNome(){
        return nome;
    }
    
    //Vetor com todos os vértices 
    public Vertex[] vertices() {
        Vertex[] v = new Vertex[numVertices()];
        LinkedList<Object> vertex = dicLblVertex.elements();
        for (int i = 0; i < vertex.size(); i++) {
            v[i] = (Vertex) vertex.get(i);
        }
        return v;
    }
    
    //Vetor com todas as arestas
    public Edge[] edges() {
        Edge[] e = new Edge[numEdges()];
        int pos = 0;
        LinkedList<Object> edges = dicLblEdge.elements();
        for (int i = 0; i < edges.size(); i++) {
            Edge ed = (Edge) edges.get(i);
            e[pos] = ed;
            pos++;

        }
        return e;
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
        LinkedList<Object> lstVs = dicLblVertex.keys();
        LinkedList<Object> lstEs = dicLblEdge.keys();
        Vertex v;
        Edge e;
        
        //where is this percorrendo
        for(int i = primVertice; i <= ultiVertice; i++) {
            s = "";
            
            //Caso o vertex não tenha sido excluido
            if(!lstEliminados.contains(i)) {
                for(int j = 0; j < lstVs.size(); j++) {
                    v = (Vertex)dicLblVertex.findElement(lstVs.get(j));
                    if(v.getId() == i) {
                        labelOrigem = v.getLabel();
                        break;
                    }
                }
                
                for(int k = primVertice; k <= ultiVertice; k++) {
                    if(!lstEliminados.contains(k)) {
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
    
    
    public Vertex[] endVertices(String labelE) {
        Edge oE = (Edge) dicLblEdge.findElement(labelE);
        if (dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }
        int idE = oE.getId();
        for (int i = primVertice; i <= primVertice; i++) {
            if (valido(i)) {
                for (int k = primVertice; k <= primVertice; k++) {
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
    
    public Vertex opposite(String v, String e) {
        Vertex objV = (Vertex) dicLblVertex.findElement(v);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        }

        Edge objE = (Edge) dicLblEdge.findElement(e);
        if (dicLblEdge.NO_SUCH_KEY()) {
            return null;
        }

        for (int i = primVertice; i <= primVertice; i++) {
            if ((!lstEliminados.contains(i)) && (mat[objV.getId()][i] == objE.getId())) {
                LinkedList<Object> lstVs = dicLblVertex.keys();

                for (int m = 0; m < lstVs.size(); m++) {
                    Vertex oU = (Vertex) dicLblVertex.findElement(lstVs.get(m));
                    if (oU.getId() == i) {
                        return oU;
                    }
                }
            }
        }

        return null;
    }
 
    public Integer outDegree(String label) {
        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return null;
        } else {
            int line = v.getId();
            int grade = 0;

            for (int i = primVertice; i <= ultiVertice; i++) {
                if ((mat[line][i] != 0) && !lstEliminados.contains(i)) {
                    grade++;
                }
            }

            return grade;
        }
    }
    
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
    
    public Integer degree(String label) {
        Integer in = inDegree(label);
        Integer out = outDegree(label);

        if ((in == null) || (out == null)) {
            return null;
        } else {
            return in + out;
        }
    }
    
    /*Pq um insert e um insere?*/
    public Vertex insertVertex(String label, Object dado) {
        int idVertex = geraIDVertex();

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
    /*Pq um insert e um insere /\*/
    public Vertex insereVertex(String label, Object o) {
        int idV = geraIDvertice++;

        if (idV > primVertice) {
            primVertice = idV;
        }
        if (idV < primVertice) {
            primVertice = idV;
        }

        Vertex v = (Vertex) dicLblVertex.findElement(label);
        if (dicLblVertex.NO_SUCH_KEY()) {
            v = new Vertex(label, o);
            v.setId(idV);
            dicLblVertex.insertItem(label, v);
            quantVertices++;
        } else {
            v.setDado(o);
        }

        return v;
    }
    
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
    
    public boolean areaAdjacent(String origem, String destino) {
        Vertex vOrigem = (Vertex) dicLblVertex.findElement(origem);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }

        Vertex vDestino = (Vertex) dicLblVertex.findElement(destino);
        if (dicLblVertex.NO_SUCH_KEY()) {
            return false;
        }

        return mat[vOrigem.getId()][vDestino.getId()] != 0;
    }
    
    public Vertex destination (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)
    		return vet[1];
    	else
    		return null;
    }
    
    public Vertex origin (String labelE) {
    	Vertex [] vet = endVertices(labelE);
    	if(vet!= null)
    		return vet[0];
    	else
    		return null;
    }
    
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
    	LinkedList<Edge> lst = new LinkedList <Edge>();
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
    	LinkedList<Edge> lst = new LinkedList <Edge>();
    	int id = v.getId();
    	
    	for(int i = primVertice; i<= primVertice; i++)
    		if((!lstEliminados.contains(i) && (mat[i][id] != 0)))
    			lst.add(intToEdge(mat[i][id]));
    	return lst;
    }
    
    
    public LinkedList<Vertex> outAdjacentVertices(String labelV){
    	Vertex v = (Vertex)dicLblVertex.findElement(labelV);
    	if(dicLblVertex.NO_SUCH_KEY())
    		return null;
    	LinkedList<Vertex> lst = new LinkedList<Vertex>();
    	int id = v.getId();
    	for(int k = primVertice; k<= primVertice; k++)
    		if(!lstEliminados.contains(k) && (mat[id][k] != 0))
    			lst.add(intToVertex(k));
    	return lst;
    }
    
    public LinkedList<Vertex> adjacentVertices(String labelV) {
        LinkedList<Vertex> lst = inAdjacentVertices(labelV);
        lst.addAll(outAdjacentVertices(labelV));
        return lst;

    }
    
    public LinkedList<Vertex> inAdjacentVertices(String labelV){
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
    
   
    public LinkedList<Edge> incidentEdges(String labelV){
    	LinkedList<Edge> lst = inIncidentEdges(labelV);
    	lst.addAll(outIncidentEdges(labelV));
    	return lst;
    }
    
    private int geraIDVertex() {
        int id;

        if (lstEliminados.size() == 0) {
            id = geraIDvertice++;
        } else {
            id = lstEliminados.get(0);
            lstEliminados.remove();
        }

        if (id < primVertice) {
            primVertice = id;
        }

        if (id > primVertice) {
            primVertice = id;
        }

        return id;
    }

}
