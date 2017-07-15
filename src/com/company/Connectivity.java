package com.company;

import data_structure.MyList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SRIN on 8/6/2015.
 */
public class Connectivity {

    static final char[]  ALPHA = new char[]{'.',};

    static int charToInt(char c){
        int temp = (int)c;
        int temp_integer = 64; //for upper case
        if(temp<=90 & temp>=65){
            return temp-temp_integer;
        }else {
            return 0;
        }
    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("graph3.txt"));
//            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
            for(int test_case = 0; test_case < T; test_case++) {
                int Answer;
                String h = sc.next();sc.nextLine();
                int N = charToInt(h.charAt(0));
                AndaGraph g = new AndaGraph(N, false);
                /*int E = sc.nextInt();
                for(int i=0;i<E;i++){
                    String edge = sc.next();
                    int u = charToInt(edge.charAt(0));
                    int v = charToInt(edge.charAt(1));
                    g.addEdge(u, v);
                }*/
                String line=sc.nextLine();

                while(!line.isEmpty()){
                    int u = charToInt(line.charAt(0));
                    int v = charToInt(line.charAt(1));
                    g.addEdge(u, v);
                    try {
                        line = sc.nextLine();
                    }catch (Exception e){
                        line = "";
                    }
                }

                Connected c = new Connected(g);
                Answer = c.getNumComponents();
                System.out.println("#"+(test_case+1)+" "+Answer);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class AndaGraph {
        public int N;
        private MyList[] adj;
        private boolean isDirected;

        public AndaGraph(int n, boolean directed) {
            N = n+1;
            adj = new MyList[N];
            for(int i=0;i<N;i++){
                adj[i] = new MyList();
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

        public MyList getNeighbours(int i){
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

    static class Connected{
        private boolean[] visited;
        private int[] edgeTo;
        private int counter;

        public Connected(AndaGraph g) {
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

        private void dfs(AndaGraph g, int v) {
            visited[v] = true;
            MyList tetangga = g.getNeighbours(v);
            for(Object w : tetangga.list){
                int ww = (Integer) w;
                if(!visited[ww]){
                    edgeTo[ww]=v;
                    dfs(g, ww);
                }
            }

        }

        public int getNumComponents() {
            return counter;
        }

        public boolean isConnected(){
            return (counter<2);
        }
    }
}
