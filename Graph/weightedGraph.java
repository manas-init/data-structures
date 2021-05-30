import java.util.Map;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.*;

class Graph {
    HashMap<Integer, ArrayList<Edge>> hm;
    boolean directed;
    
    class Edge {
        int from, to, weight;
        
        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    
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
    
    void addEdge(int u, int v, int w) {
        if(!vertexExists(u) || !vertexExists(v)) {
            System.out.println("Vertex missing : Error while trying to add edge "+u+" "+v);
            return;
        }
        Edge e = new Edge(u, v, w);
        hm.get(u).add(e);
        if(directed == false) {
            Edge re = new Edge(v, u, w);
            hm.get(v).add(re);
        }
        return;
    }
    
    boolean edgeExists(int u, int v) {
        if(vertexExists(u)) {
            ArrayList<Edge> l = hm.get(u);
            for(Edge i: l) {
                if(i.to == v) {
                    return true;
                }
            }
        }
        return false;
    }
    
    ArrayList<Edge> getNeighbours(int u) {
        if(vertexExists(u)) {
            return hm.get(u);
        }
        return new ArrayList<Edge>();
    }
    
    int getDegree(int u) {
        if(vertexExists(u)) {
            return hm.get(u).size();
        }
        return -1;
    }
    
    void printGraph() {
        for(Map.Entry<Integer, ArrayList<Edge>> entry: hm.entrySet()) {
            System.out.print(" "+entry.getKey()+" : ");
            for(Edge i: entry.getValue()) {
                System.out.print(i.to+"("+i.weight+") ");
            }
            System.out.println();
        }
        return;
    }
    
    void BFSFromVertex(int u) {
        if(!vertexExists(u))
            return;
        boolean[] visited = new boolean[hm.size()];
        Queue<Integer> q = new LinkedList<>();
        q.add(u);
        visited[u] = true;
        int count = q.size();
        while(q.peek()!= null) {
            int curr = q.poll();
            ArrayList<Edge> l = hm.get(curr);
            for(Edge i:l) {
                if(visited[i.to] == false) {
                    visited[i.to] = true;
                    q.add(i.to);
                }
            }
            System.out.print(curr+" ");
            count--;
            if(count == 0)
            {
                System.out.println();
                count=q.size();
            }
        }
    }
    
    void BFS() {
        boolean[] visited = new boolean[hm.size()];
        for(Map.Entry<Integer, ArrayList<Edge>> entry: hm.entrySet()) {
            int u = entry.getKey();
            if(visited[u] == true)
                continue;
            Queue<Integer> q = new LinkedList<>();
            q.add(u);
            visited[u] = true;
            int count = q.size();
            while(q.peek()!= null) {
                int curr = q.poll();
                ArrayList<Edge> l = hm.get(curr);
                for(Edge i:l) {
                    if(visited[i.to] == false) {
                        visited[i.to] = true;
                        q.add(i.to);
                    }
                }
                System.out.print(curr+" ");
                count--;
                if(count == 0)
                {
                    System.out.println();
                    count=q.size();
                }
            }
        }
    }
    
    void DFSFromVertex(int u) {
        if(!vertexExists(u))
            return;
        boolean[] visited = new boolean[hm.size()];
        Stack<Integer> st = new Stack<Integer>();
        st.push(u);
        visited[u] = true;
        System.out.print(u+" ");
        while(!st.empty()) {
            int curr = st.pop();
            //System.out.print(curr+" ");
            ArrayList<Edge> l = hm.get(curr);
            for(Edge i:l) {
                if(visited[i.to] == false)
                {
                    st.push(i.to);
                    visited[i.to] = true;
                    System.out.print(i.to+" ");
                }
            }
        }
        System.out.println();
        return;
        /*ArrayList<Edge> l = hm.get(u);
        for(Edge i:l) {
            DFSFromVertex(i.to);
        }
        System.out.println();*/
    }
    
    public HashMap<Integer, Integer> dijkstra(int s) {
        
        class WeightComparator implements Comparator<Pair<Integer, Integer>>{
            public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
                if(a.getValue() > b.getValue())
                    return 1;
                else if(a.getValue() < b.getValue())
                    return -1;
                return 0;
            }
        }
        
        //If vertex is not present then return
        if(!vertexExists(s)) {
            return new HashMap<Integer, Integer>();
        }
        
        //Create a min heap with vertex and distance pair, s at 0 and other at int max distance
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<Pair<Integer, Integer>>(new WeightComparator());
        HashMap<Integer, Integer> distMap = new HashMap<Integer, Integer>();
        for(Map.Entry<Integer, ArrayList<Edge>> entry:hm.entrySet()) {
            int u = entry.getKey();
            if(u == s) {
                pq.add(new Pair(u, 0));
                distMap.put(u, 0);
            }
            else {
                distMap.put(u, Integer.MAX_VALUE);
            }
        }
        boolean[] visited = new boolean[hm.size()];
        //System.out.println(distMap);
        //System.out.println(Arrays.toString(visited));
        //while heap is not empty pick min vertex and parse it
        while(pq.size() != 0) {
            Pair<Integer, Integer> p = pq.poll();
            int u = p.getKey();
            for(Edge i:hm.get(u)) {
                if(visited[i.to] == false) {
                    int newDist = distMap.get(u) + i.weight;
                    if(newDist < distMap.get(i.to)) {
                        pq.add(new Pair(i.to, newDist));
                        distMap.put(i.to, newDist);
                    }
                }
            }
            visited[u] = true;
            //System.out.println(u);
            //System.out.println(distMap);
            //System.out.println(Arrays.toString(visited));
        }
        return distMap;
    }
    
}


public class weightedGraph {
    public static void main(String args[]) {
        Graph g = new Graph(true);
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addEdge(0, 1, 1);
        //g.addEdge(0, 4, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 40);
        g.addEdge(1, 4, 5);
        g.addEdge(2, 3, 6);
        g.addEdge(3, 4, 7);
        g.printGraph();
        //g.BFSFromVertex(0);
        //g.BFSFromVertex(1);
        //g.BFSFromVertex(4);
        //g.BFS();
        System.out.println("holla");
        g.DFSFromVertex(0);
        HashMap<Integer, Integer> shortPath = g.dijkstra(0);
        for(Map.Entry<Integer, Integer> entry:shortPath.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }
}
