package practice;

import data_structure.IndexMinPQ;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by user on 8/9/2015.
 */
public class ShortestPath {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("assets/Djiskstra.txt"));
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
                Djikstra dj = new Djikstra(g, 1);
                if(dj.hasPath(5)){
                    System.out.println(Arrays.toString(dj.pathTo(5).list));
                    System.out.println(dj.distTo(5));
                }else{
                    System.out.println("no path");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class Djikstra{
        private Edge[] edgeTo;
        private double[] distTo;
        private IndexMinPQ<Double> pq;
        int source;

        public Djikstra(WGraph G, int s) {
            source = s;
            edgeTo = new Edge[G.V];
            distTo = new double[G.V];
            pq = new IndexMinPQ<Double>(G.V);
            for (int v = 0; v < G.V; v++){
                distTo[v] = Double.POSITIVE_INFINITY;
            }
            distTo[s] = 0.0;
            pq.insert(s, 0.0);
            while (!pq.isEmpty()){
                relax(G, pq.delMin());
            }
        }

        private void relax(WGraph G, int v)
        {
            for(Object o : G.adj(v).list)
            {
                Edge e = (Edge) o;
                int w = e.to();
                if (distTo[w] > distTo[v] + e.weight())
                {
                    distTo[w] = distTo[v] + e.weight();
                    edgeTo[w] = e;
                    if (pq.contains(w)) {
                        pq.change(w, distTo[w]);
                    }
                    else{
                        pq.insert(w, distTo[w]);
                    }
                }
            }
        }

        AndaList pathTo(int v){
            if(!hasPath(v)) return null;
            AndaList path = new AndaList();
            for(int x=v; x!=source; x=edgeTo[x].from()){
                path.add(x);
            }
            path.add(source);
            return path;
        }

        boolean hasPath(int v){ return  distTo[v]!=Double.POSITIVE_INFINITY;}

        public double distTo(int v) {
            return distTo[v];
        }
    }

    static class WGraph{
         final int V;               // number of vertices
        private int E;                     // number of edges
        private AndaList[] adj;
        public WGraph(int V)
        {
            this.V = V+1;
            this.E = 0;
            adj = new AndaList[V];
            for (int v = 0; v < V; v++){
                adj[v] = new AndaList();
            }
        }

        public void addEdge(Edge e)
        {
            adj[e.from()].add(e);
            E++;
        }

        public AndaList adj(int v){
            return adj[v];
        }
    }

    static class Edge{
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
