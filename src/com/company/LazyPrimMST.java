package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by user on 8/17/2015.
 */
public class LazyPrimMST {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("tinny.txt"));
//            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
            for(int test_case = 0; test_case < T; test_case++) {
                int N = sc.nextInt();
                int E = sc.nextInt();
                WGraph g= new WGraph(N);
                for(int i=0;i<E;i++){
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    int w = sc.nextInt();
                    g.addEdge(new Edge(u, v, (double)w));
                }
                LazyPrim prim = new LazyPrim(g);
                prim.printMST();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class LazyPrim{
        boolean[] visited;
        PriorityQueue<Edge> pq;
        List<Edge> mst;

        public LazyPrim(WGraph g) {
            pq = new PriorityQueue<Edge>();
            visited = new boolean[g.E];
            mst = new ArrayList<Edge>();

            visit(g, 0);
            while(!pq.isEmpty() || mst.size()<(g.V-1)){
                Edge e = pq.poll();
                System.out.println("pop (" + e.from() + "," + e.to() + ")-" + e.weight);
                printPQ();
                int v = e.either(), w = e.other(v);
                if (visited[v] && visited[w]) continue;  // Skip if ineligible.
                mst.add(e);                        // Add edge to tree.
                printMST();
                if (!visited[v]) visit(g, v);           // Add vertex to tree
                if (!visited[w]) visit(g, w);           //   (either v or w)
            }
        }

        private void visit(WGraph g, int v) {
            System.out.println("v = "+v);
            visited[v]=true;
            for(Object o : g.adj(v).list){
                Edge e = (Edge) o;
                if(!visited[e.other(v)]){
                    pq.add(e);
                }
            }
            printPQ();
        }

        void printPQ(){
            System.out.println("Queue edge : ");
            for(Object o : pq.toArray()){
                Edge e = (Edge) o;
                System.out.print("("+e.from()+","+e.to()+")-"+e.weight+"   ");
            }
            System.out.println();
        }

        void printMST(){
            System.out.println("MST : ");
            for(Object o : mst.toArray()){
                Edge e = (Edge) o;
                System.out.print("("+e.from()+","+e.to()+")-"+e.weight+"   ");
            }
            System.out.println();
            System.out.println();
        }

        public List<Edge> getMst() {
            return mst;
        }
    }

    static class WGraph{
        final int V;               // number of vertices
        private int E;                     // number of edges
        private AndaList[] adj;
        public WGraph(int V)
        {
            this.V = V;
            this.E = 0;
            adj = new AndaList[V];
            for (int v = 0; v < V; v++){
                adj[v] = new AndaList();
            }
        }

        public void addEdge(Edge e)
        {
            adj[e.from()].add(e);
            adj[e.to()].add(e);
            E++;
        }

        public AndaList adj(int v){
            return adj[v];
        }
    }

    static class Edge implements Comparable<Edge>{
        private final int v;                       // edge source
        private final int w;                       // edge target
        private final double weight;               // edge weight
        public Edge(int v, int w, double weight)
        {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }
        public double weight()
        {  return weight;  }
        public int from()
        {  return v;  }
        public int to()
        {  return w;  }
        public int either()
        {  return v;  }
        public int other(int vertex)
        {
            if      (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new RuntimeException("Inconsistent edge");
        }
        public int compareTo(Edge that)
        {
            if      (this.weight() < that.weight()) return -1;
            else if (this.weight() > that.weight()) return +1;
            else                                    return  0;
        }
    }

    static class AndaList{
        Object[] list;
        int size;

        public AndaList() {
            size=0;
            list = new Object[size];
        }

        void add(Object x){
            size++;
            Object[] tem = new Object[size];
            for(int i=0;i<size-1;i++){
                tem[i]=list[i];
            }
            tem[size-1]=x;
            list = tem;
        }

        void remove(int index){
            if(size>0 && index!=-1){
                int newSize = size-1;
                Object[] temp = new Object[newSize];
                if(index==size-1){
                    for (int i=0;i<size-1;i++){
                        temp[i]=list[i];
                    }
                }else if(index==0){
                    for(int i=1;i<size;i++){
                        temp[i-1]=list[i];
                    }
                }else{
                    for(int i=0;i<index;i++){
                        temp[i]=list[i];
                    }
                    for(int i=index+1;i<size;i++){
                        temp[i-1]=list[i];
                    }
                }
            }
            size--;
        }

        int indexOf(Object o){
            for(int i=0;i<size;i++){
                if(list[i].equals(o)) return i;
            }
            return -1;
        }

        boolean contains(Object o){
            return indexOf(o)>-1;
        }

        public int size() {
            return size;
        }
    }
}
