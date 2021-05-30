import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;

class Graph {
    HashMap<Integer, ArrayList<Integer>> hm;
    boolean directed;
    
    Graph(boolean directed) {
        hm = new HashMap<>();
        this.directed = directed;
    }
    
    Graph() {
        this(false);
    }
    
    void addVertex(int u) {
        if(!vertexExists(u)) {
            hm.put(u, new ArrayList<>());
        }
        return;
    }
    
    boolean vertexExists(int u) {
        return hm.containsKey(u);
    }
    
    void addEdge(int u, int v) {
        if(!vertexExists(u) || !vertexExists(v)) {
            System.out.println("Vertex missing : Error while trying to add edge "+u+" "+v);
            return;
        }
        hm.get(u).add(v);
        if(directed == false) {
            hm.get(v).add(u);
        }
        return;
    }
    
    boolean edgeExists(int u, int v) {
        if(vertexExists(u)) {
            ArrayList<Integer> l = hm.get(u);
            for(int i: l) {
                if(i == v) {
                    return true;
                }
            }
        }
        return false;
    }
    
    ArrayList<Integer> getNeighbours(int u) {
        if(vertexExists(u)) {
            return hm.get(u);
        }
        return new ArrayList<Integer>();
    }
    
    int getDegree(int u) {
        if(vertexExists(u)) {
            return hm.get(u).size();
        }
        return -1;
    }
    
    void printGraph() {
        for(Map.Entry<Integer, ArrayList<Integer>> entry: hm.entrySet()) {
            System.out.print(" "+entry.getKey()+" : ");
            for(int i: entry.getValue()) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
        return;
    }
    
    void kahnsTopologicalSort() {
        int n = hm.size();
        int[] inVertex = new int[n];
        boolean[] visited = new boolean[n];
        for(Map.Entry<Integer, ArrayList<Integer>> entry:hm.entrySet()) {
            int u = entry.getKey();
            for(int v: entry.getValue()) {
                inVertex[v]++;
            }
        }
        Queue<Integer> q = new LinkedList<Integer>();
        for(int i: inVertex) {
            if(inVertex[i] == 0) {
                q.add(i);
            }
        }
        int processed = 0;
        int[] topoOrder = new int[n];
        while(q.peek() != null) {
            int u = q.poll();
            topoOrder[processed++]=u;
            for(int v: hm.get(u)) {
                inVertex[v]--;
                if(inVertex[v] == 0 && visited[v] == false) {
                    q.add(v);
                    visited[v] = true;
                }
            }
        }
        if(processed != n) {
            System.out.println("There is a cycle in graph(DAG)");
            return;
        }
        System.out.print("Topological order : ");
        for(int i: topoOrder) {
            System.out.print(i+" -> ");
        }
        System.out.println();
        return;
    }
}


public class unweightedGraph {
    public static void main(String args[]) {
        Graph g = new Graph(true);
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.printGraph();
        g.kahnsTopologicalSort();
    }
}
