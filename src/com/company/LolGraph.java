package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 8/9/2015.
 */
public class LolGraph {
    public int N;
     List<Integer>[] adj;
    private boolean isDirected;
    int[][] A;

    public void setAdj(List<Integer>[] adj, int[][] a) {
        this.adj = adj;
        this.A = a;
    }

    public LolGraph(int n, boolean directed) {
        N = n;
        adj = (List<Integer>[]) new List[N];
        for(int i=0;i<N;i++){
            adj[i] = new ArrayList<Integer>();
        }
        A = new int[N][N];
        isDirected=directed;
    }

    public void addEdge(int u, int v){
        adj[u].add(v);
        Integer[] edge = new Integer[]{Math.min(u,v),Math.max(u,v)};
        A[u][v]=1;
        A[v][u]=1;
    }


    public void deleteEdge(int u, int v){
        adj[u].remove(adj[u].indexOf(v));
        if(!isDirected){
            adj[v].remove(adj[v].indexOf(u));
        }
    }

    public List<Integer> getNeighbours(int i){
        return adj[i];
    }


    public void print() {
        System.out.println("N = "+N);
        for(int i=0;i<N;i++){
            System.out.println(i + " : " + Arrays.toString(adj[i].toArray()));
        }
//            System.out.print("total edges: "+edges.size()+" | ");
        for(int i=0;i<A.length;i++){
            System.out.println(Arrays.toString(A[i]));
        }
        System.out.println();
    }
}
