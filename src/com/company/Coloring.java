package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SRIN on 8/7/2015.
 */
public class Coloring {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for(int test_case = 0; test_case < T; test_case++) {
            String Answer = "";
            int N = sc.nextInt();
            int E = sc.nextInt();
            AndaGraph g = new AndaGraph(N, false);
            for(int i=0;i<E;i++){
                int u = sc.nextInt();
                int v = sc.nextInt();
                g.addEdge(u, v);
            }
            TwoColoring coloring = new TwoColoring(g);
            if(coloring.isTwoColorable()){
                Answer="BICOLORABLE.";
            }else{
                Answer="NOT BICOLORABLE.";
            }
            System.out.println("#"+(test_case+1)+" "+Answer);

        }

    }

    static class AndaGraph {
        public int N;
        private List<Integer>[] adj;
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
            if(!adj[u].contains(v)){
                adj[u].add(v);
            }
            if(!isDirected){
                if(!adj[v].contains(u)){
                    adj[v].add(u);
                }
            }
        }

        public List<Integer> getNeighbours(int i){
            return adj[i];
        }

        public int minDegree(){
            int min = Integer.MAX_VALUE;
            int counter = 0;
            for(int i=1;i<N;i++){
                counter++;
                min = Math.min(getNeighbours(i).size(), min);
            }
//            System.out.println("counter : "+counter+", min deg = "+min);
            return min;
        }

        public void print() {
            System.out.println("N = "+(N-1));
            for(int i=1;i<N;i++){
//                System.out.println(i + " : " + Arrays.toString(adj[i].toArray()));
            }
        }
    }

    static class TwoColoring {
        private boolean[] visited;
        private boolean[] color;
        private boolean twoColorable=true;

        public TwoColoring(AndaGraph g) {
            visited = new boolean[g.N];
            color = new boolean[g.N];
            for(int v=1;v<g.N;v++){
                if(!visited[v]) {
                    dfs(g, v);
                }
            }
        }

        private void dfs(AndaGraph g, int v) {
            visited[v] = true;
            List<Integer> tetangga = g.getNeighbours(v);
            for(int w : tetangga){
                if(!visited[w]){
                    color[w]=!color[v];
                    dfs(g, w);
                }else if(color[w]==color[v]){
                    twoColorable=false;
                }
            }

        }

        public boolean isTwoColorable() {
            return twoColorable;
        }

    }
}
