package com.company;

import data_structure.MyQeueu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SRIN on 8/5/2015.
 */
public class UndirectedPath {

    boolean[] visited;
    int[] edgeTo;
    int source;

    public UndirectedPath(com.company.AndaGraph g, int s) {
        this.visited = new boolean[g.N];
        this.edgeTo = new int[g.N];
        this.source = s;
        dfs(g, s);
    }

    private void dfs(com.company.AndaGraph g, int s) {
        visited[s]=true;
        List<Integer> tetangga = g.getNeighbours(s);
        for(int w : tetangga){
            if(!visited[w]){
                edgeTo[w]=s;
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int v){
        return visited[v];
    }

    public List<Integer> pathTo(int v){
        if(!hasPathTo(v)) return null;
        List<Integer> path = new ArrayList<Integer>();
        for(int x=v; x!=source; x=edgeTo[x]){
            path.add(x);
        }
        path.add(source);
        return path;

    }

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("graph5.txt"));
//            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
            for(int test_case = 0; test_case < T; test_case++) {
                int N = sc.nextInt();
                int E = sc.nextInt();
                AndaGraph g= new AndaGraph(N, false);
                for(int i=0;i<E;i++){
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    g.addEdge(u,v);
                }
                BFSPath p = new BFSPath(g, 1);
                if(p.reachTo(5)){
                    System.out.println(Arrays.toString(p.pathTo(5).list));
                }else{
                    System.out.println("not reachable");
                }


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class BFSPath{
        private boolean[] visited;
        private int[] edgeTo;
        private final int source;

        public BFSPath(AndaGraph g, int s)        {
            visited = new boolean[g.size];
            edgeTo = new int[g.size];
            this.source = s;
            bfs(g, source);
        }

        private void bfs(AndaGraph g, int s)   {
            MyQeueu queue = new MyQeueu(g.size);
            visited[s] = true;
            queue.enqueue(s);
            while (!queue.isEmpty())  {
                int v = (Integer) queue.dequeue();
                for (Object o : g.getTetangga(v).list){
                    int w = (Integer)o;
                    if (!visited[w]){
                        edgeTo[w] = v;
                        visited[w] = true;
                        queue.enqueue(w);
                    }
                }

            }
        }

        AndaList pathTo(int d){
            if(reachTo(d)){
                AndaList path = new AndaList();
                for(int x = d; x!=source; x = edgeTo[x]){
                    path.add(x);
                }
                path.add(source);
                return path;
            }
            return null;
        }

        public boolean reachTo(int v)
        {  return visited[v];  }
    }

    static class AndaGraph{
        AndaList[] adj;
        int size;
        boolean isDirected;

        public AndaGraph(int n, boolean isDirected) {
            size = n+1;
            adj = new AndaList[size];
            for(int i=0;i<size;i++) adj[i] = new AndaList();
            this.isDirected = isDirected;
        }

        void addEdge(int u, int v){
            adj[u].add(v);
            if(!isDirected){
                adj[v].add(u);
            }
        }

        AndaList getTetangga(int v){
            return adj[v];
        }

        void print(){
            for(AndaList a : adj){
                System.out.println(Arrays.toString(a.list));
            }
        }
    }

    public static class AndaList{
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
