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
public class Bridge {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("Bridge.txt"));
//        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for(int test_case = 0; test_case < T; test_case++) {
            String Answer = "";
            int N = sc.nextInt();
            MyGraph g = new MyGraph(N, false);
            for(int i=0;i<N;i++){
                int u = sc.nextInt();
                int t = sc.nextInt();
                for(int j=0;j<t;j++){
                    int v = sc.nextInt();
                    g.addEdge(u,v);
                }
            }
//            g.print();
            CC cc = new CC(g);

            System.out.println("#"+(test_case+1)+" "+Answer);
            int c = cc.getSumComponents();
            System.out.println("number of components : "+c);
            List<Integer[]> criticals = checkBridge(g, c);
            System.out.println("total criticals : "+criticals.size());
            if(criticals.size()>0){
                for(Integer[] link : criticals){
                    System.out.println(Arrays.toString(link));
                }
            }

        }

    }

    static List<Integer[]> checkBridge(MyGraph g, int c){
        List<Integer[]> criticals = new ArrayList<Integer[]>();
        for(int i=0;i<g.N-1;i++){
            for(int j=i+1;j<g.N;j++){
                if(g.A[i][j]==1){
                    g.deleteEdge(i,j);
                    CC tempCC = new CC(g);
                    g.addEdge(i,j);
                    if(tempCC.getSumComponents()>c){
                        criticals.add(new Integer[]{i, j});
                    }
                }
            }

        }
        return criticals;
    }

     static class MyGraph {
        public int N;
        private List<Integer>[] adj;
        private boolean isDirected;
        int[][] A;

        public MyGraph(int n, boolean directed) {
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
            A[u][v]=0;
            if(!isDirected){
                adj[v].remove(adj[v].indexOf(u));
                A[v][u]=0;
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

    static class CC
    {
        private boolean[] visited;
        private int[] componentsID;
        private int components;

        public CC(MyGraph g) {
            components=0;
            componentsID =  new int[g.N];
            visited = new boolean[g.N];
            for(int v=0;v<g.N;v++){
                if(!visited[v]) {
                    components++;
                    dfs(g, v);
                }
            }
        }

        private void dfs(MyGraph g, int v) {
            visited[v] = true;
            componentsID[v]=components;
            List<Integer> tetangga = g.getNeighbours(v);
            for(int w : tetangga){
                if(!visited[w]){
                    dfs(g, w);
                }
            }

        }

        public int getSumComponents() {
            return components;
        }

        public int getVerticeFromComponents(int c){
            for(int i=0;i<componentsID.length;i++){
                if(componentsID[i]==c){
                    return i;
                }
            }
            return -1;
        }


    }



}
