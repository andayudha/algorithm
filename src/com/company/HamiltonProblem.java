package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SRIN on 8/6/2015.
 */
public class HamiltonProblem {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("graph2.txt"));
//            Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for(int test_case = 0; test_case < T; test_case++) {
            int N = sc.nextInt();
            MyGraph g = new MyGraph(N, false);
            int u,v ;
            while(true){
                u = sc.nextInt();
                if(u==0){
                    break;
                }else {
                    v = sc.nextInt();
                    g.addEdge(u, v);
                }

            }
            String Answer;
            g.print();
            Connected c = new Connected(g);
            //System.out.println(c.isHamiltonian());
            if(c.isConnected()){
                if(g.N-1>=3 && (g.minDegree()>=(g.N-1)/2)) {
                    Answer = "Y";
                }else if(g.N-1>=3 && g.oreConditions()){
                    Answer = "Y";
                }else {
                    Answer="N";
                }
            }else{
                Answer="N";
            }

            System.out.println("#"+(test_case+1)+" "+Answer);

        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class MyGraph {
        public int N;
        private List<Integer>[] adj;
        private boolean isDirected;
        private int[][] A;

        public MyGraph(int n, boolean directed) {
            N = n+1;
            adj = (List<Integer>[]) new List[N];
            for(int i=0;i<N;i++){
                adj[i] = new ArrayList<Integer>();
            }
            isDirected=directed;
            A = new int[N][N];
        }

        public void addEdge(int u, int v){
            adj[u].add(v);
            A[u][v]=1;
            if(!isDirected){
                adj[v].add(u);
                A[v][u]=1;
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
                System.out.println(i + " : " + Arrays.toString(adj[i].toArray()));
            }
        }

        public boolean oreConditions(){

            for(int i = 1;i<N;i++){
                for(int j=i+1;j<N-1;j++){
                    if(A[i][j]==0){
                        if(getNeighbours(i).size()+getNeighbours(j).size()>=N-1){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    static class Connected{
        private boolean[] visited;
        private int[] edgeTo;
        private int counter;
        private List<Integer> cycle;
        private boolean isHamiltonian = false;

        public boolean isHamiltonian() {
            return isHamiltonian;
        }

        public Connected(MyGraph g) {
            counter=0;
            visited = new boolean[g.N];
            edgeTo = new int[g.N];
            for(int v=1;v<g.N;v++){
                if(!visited[v]) {
                    counter++;
                    dfs(g, v);
                }
            }
        }

        private void dfs(MyGraph g, int v) {
            visited[v] = true;
            List<Integer> tetangga = g.getNeighbours(v);
            for(int w : tetangga){
                if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g, w);
                }
            }

        }


        private void dfs(MyGraph g, int u, int v){
            visited[v] = true;
            List<Integer> tetangga = g.getNeighbours(v);
            for(int w : tetangga){
//                if(isHamiltonian) return;
                if(isHamiltonian()){
                    return;
                } else if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g, v, w);
                }else if(u!=w){
                    cycle = new ArrayList<Integer>();
                    for(int x=v; x!=w; x=edgeTo[x]){
                        cycle.add(x);
                    }
                    cycle.add(w);
                    if(cycle.size()==g.N-1){
                        isHamiltonian=true;
                        return;
                    }
                }
            }
        }

        private boolean isAllvisited(){
            for(int i=1;i<visited.length;i++){
                if(!visited[i]) return false;
            }
            return true;
        }

        public boolean isConnected(){
            return (counter<2);
        }
    }
}
