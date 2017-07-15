package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SRIN on 8/7/2015.
 */
public class Hamilton2 {

    public static void main(String[] args) {
//        try {
//            Scanner sc = new Scanner(new File("graph2.txt"));
            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
            for(int test_case = 0; test_case < T; test_case++) {
                int N = sc.nextInt();
                HamiltonDua ham = new HamiltonDua(N);
                MyGraph g = new MyGraph(N, false);
                int u,v ;
                while(true){
                    u = sc.nextInt();
                    if(u==0){
                        break;
                    }else {
                        v = sc.nextInt();
                        ham.addEdge(u, v);
                        g.addEdge(u, v);
                    }

                }
                String Answer;
                Connected c = new Connected(g);
                //System.out.println(c.isHamiltonian());
                if(c.isConnected()){
                    ham.findHamiltonianCycle(ham.graph);
                    if(ham.isHamiltonian()){
                        Answer="Y";
                    }else{
                        if(g.N-1>=3 && (g.minDegree()>=(g.N-1)/2)) {
                            Answer = "Y";
                        }else if(g.N-1>=3 && g.oreConditions()){
                            Answer = "Y";
                        }else {
                            Answer="N";
                        }
                    }
                }else{
                    Answer="N";
                }



                System.out.println("#"+(test_case+1)+" "+Answer);

            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    static class HamiltonDua{
        int[][] graph;
        private int V, pathCount;
        private int[] path;
        private boolean isHamiltonian=false;

        public HamiltonDua(int v) {
            V = v;
            graph = new int[V][V];
        }


        public void findHamiltonianCycle(int[][] g)
        {
            V = g.length;
            path = new int[V];

            Arrays.fill(path, -1);
            graph = g;
            try
            {
                path[0] = 0;
                pathCount = 1;
                solve(0);
            }
            catch (Exception e)
            {

                isHamiltonian=true;
            }
        }

        public void solve(int vertex) throws Exception
        {

            if (graph[vertex][0] == 1 && pathCount == V)
                throw new Exception("Solution found");

            if (pathCount == V)
                return;

            for (int v = 0; v < V; v++)
            {

                if (graph[vertex][v] == 1 )
                {

                    path[pathCount++] = v;

                    graph[vertex][v] = 0;
                    graph[v][vertex] = 0;


                    if (!isPresent(v))
                        solve(v);


                    graph[vertex][v] = 1;
                    graph[v][vertex] = 1;

                    path[--pathCount] = -1;
                }
            }
        }

        public boolean isPresent(int v)
        {
            for (int i = 0; i < pathCount - 1; i++)
                if (path[i] == v)
                    return true;
            return false;
        }

        /*public void display()
        {
            System.out.print("\nPath : ");
            for (int i = 0; i <= V; i++)
                System.out.print(path[i % V] +" ");
            System.out.println();
        }*/


        public void addEdge(int u, int v) {
            graph[u-1][v-1]=1;
            graph[v-1][u-1]=1;
        }

        public boolean isHamiltonian() {
            return isHamiltonian;
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

        public boolean isConnected(){
            return (counter<2);
        }
    }
}
