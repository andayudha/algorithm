package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by user on 7/12/2015.
 */
public class AndaGraph {
    public int N;
    private List<Integer>[] adj;
    private boolean hasCycle;
    private boolean isDirected;

    public AndaGraph(int n, boolean directed) {
        N = n+1;
        adj = (List<Integer>[]) new List[N];
        for(int i=0;i<N;i++){
            adj[i] = new ArrayList<Integer>();
        }
        isDirected=directed;
    }

    public void addEdge(int u, int v){
        adj[u].add(v);
        if(!isDirected){
            adj[v].add(u);
        }
    }

    public void removeEdge(int i, int j) {
        if(adj[i].contains(j)){
            adj[i].remove(adj[i].indexOf(j));
        }if(adj[j].contains(i)){
            adj[j].remove(adj[j].indexOf(i));
        }
//        Iterator<Integer> it = adj[i].iterator();
//        while (it.hasNext()) {
//            if (it.next() == j) {
//                it.remove();
//                return;
//            }
//        }
    }

    public List<Integer> getNeighbours(int i){
        return adj[i];
    }

    public List<Integer> inEdges(int i) {
        List<Integer> edges = new ArrayList<Integer>();
        for (int j = 0; j < N; j++)
            if (adj[j].contains(i))    edges.add(j);
        return edges;
    }


    public void print() {
        System.out.println("N = "+(N-1));
        for(int i=1;i<N;i++){
            System.out.println(i + " : " + Arrays.toString(adj[i].toArray()));
        }
    }

    public void bfs(int x){
        boolean visited[] = new boolean[N];
        Queue<Integer> q = new LinkedList<Integer>();

        visited[x]=true;
        q.add(x);

        while(!q.isEmpty()){
            int r = q.remove();
            List<Integer> tetangga = getNeighbours(r);
            for(int i : tetangga){
                if(visited[i]==false){
                    visited[i]=true;
                    q.add(i);
                }
            }
        }
        System.out.println(Arrays.toString(visited));
    }

    public void dfs(int x){
        boolean[] visited = new boolean[N];
        Stack<Integer> stack = new Stack<Integer>();
//        visited[x]=true;
        stack.push(x);
        while(!stack.isEmpty()){
            int r = stack.pop();
            if(visited[r]==false){
                visited[r]=true;
                List<Integer> tetangga = getNeighbours(r);
                for(int i : tetangga){
                    stack.push(i);
                }

            }
        }
    }

    public boolean isConnected(int u,int v){
        boolean visited[] = new boolean[N];
        Queue<Integer> q = new LinkedList<Integer>();

        visited[u]=true;
        q.add(u);

        while(!q.isEmpty()){
            int r = q.remove();
            List<Integer> tetangga = getNeighbours(r);
            for(int i : tetangga){
                if(i==v){
                    return true;
                }
                if(visited[i]==false){
                    visited[i]=true;
                    q.add(i);
                }
            }
        }
        return false;
    }

    public void findCycle(int v, int u){
        boolean[] marked = new boolean[N];
        marked[v]=true;
        List<Integer> tetangga = getNeighbours(v);
        for(int w : tetangga){
            if(marked[w]==false){
                marked[w]=true;
                findCycle(w, v);
            }else if( v!=u){
                hasCycle=true;
                return;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public static class DFSCycle {

        private boolean marked[];
        private int s;
        private AndaGraph g;
        private boolean hasCycle;

        // s - starting node
        public DFSCycle(AndaGraph g, int s) {
            this.g = g;
            this.s = s;
            marked = new boolean[g.N];
            findCycle(g,s,s);
        }

        public boolean hasCycle() {
            return hasCycle;
        }

        public void findCycle(AndaGraph g, int v, int u) {

            marked[v] = true;

            for (int w : g.getNeighbours(v)) {
                if(!marked[w]) {
                    marked[w] = true;
                    findCycle(g,w,v);
                } else if (v != u) {
                    hasCycle = true;
                    return;
                }
            }

        }
    }

    public static class DirectedCycle{
        private boolean[] marked;        // marked[v] = has vertex v been marked?
        private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
        private boolean[] onStack;       // onStack[v] = is vertex on the stack?
        private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

        public DirectedCycle(AndaGraph G) {
            marked  = new boolean[G.N];
            onStack = new boolean[G.N];
            edgeTo  = new int[G.N];
            for (int v = 1; v < G.N; v++){
                if (!marked[v]) dfs(G, v);
            }
        }

        private void dfs(AndaGraph g, int v){
            onStack[v] = true;
            marked[v]=true;

            //for all neighbours
            for(int w : g.getNeighbours(v)){
                //stop if cycle not null
                if(cycle!=null) {
                    return;
                }else if(marked[w]==false){ //kalo sitetangga blm dimarked, dfs
                    edgeTo[w]=v; //isi titik sebelum si tetangga itu
                    dfs(g, w);
                }else if(onStack[w]==true){// ketika sitetangga ada di stack(tetangga yg udah pernah di marked/visit) back edge
                    cycle = new Stack<Integer>();
                    for(int x=v; x!=w; x=edgeTo[x]){// mula2 x=v; terus x di replace sama edgeTo[x] sampe x!=2
                        //x=edgeTo[x] : ngetrack semua titik sebelum titik x
                        cycle.push(x);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }// end for tetangga

            onStack[v]=false;
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        public Iterable<Integer> cycle() {
            return cycle;
        }
    }

    public static void main(String[] args){
        try {
            Scanner sc = new Scanner(new File("graph.txt"));
            int V = sc.nextInt();
            int E = sc.nextInt();
            AndaGraph G = new AndaGraph(V, true); //directed graph
            for(int e=0;e<E;e++){
                int u = sc.nextInt();
                int v = sc.nextInt();
                G.addEdge(u,v);
            }
            G.print();
//            System.out.println(Arrays.toString(G.getNeighbours(3).toArray()));
//            G.dfs(1);
//            System.out.println(G.isConnected(1,8));
            DirectedCycle checkCycle = new DirectedCycle(G);
            if(!checkCycle.hasCycle()){
                System.out.println("no cycle");
            }else{
                for(int x : checkCycle.cycle()){
                    System.out.print(x + "-");
                }
            }
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
