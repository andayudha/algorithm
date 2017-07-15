package practice;

import data_structure.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by user on 8/9/2015.
 */
public class Practice2 {

    public static void main(String[] args) {
//        int[] a = new int[]{5,7,1,3,6,2,9,0,4};
//        System.out.println(Arrays.toString(a));
//        quickSort(a, 0, a.length-1);
//        System.out.println(Arrays.toString(a));

        System.out.println(Util.isPrime(9));
        System.out.println(Util.isPrime(2));
        System.out.println(Util.isPrime(37));

        try {
            Scanner sc = new Scanner(new File("graph6.txt"));
//            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
            for(int test_case = 0; test_case < T; test_case++) {
                int N = sc.nextInt();
                int E = sc.nextInt();
                AndaGraph g= new AndaGraph(N, true);
                for(int i=0;i<E;i++){
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    g.addEdge(u,v);
                }
                DirectedCycle dc = new DirectedCycle(g);
                if(dc.cycle!=null){
                    System.out.println(Arrays.toString(dc.cycle.list));
                }else {
                    System.out.println("no cycle");
                }
                int source = 5;
                int dest = 2;
                DirectedPath dp = new DirectedPath(g, source);
                AndaList path = dp.pathTo(dest);
                if(path!=null){
                    System.out.println(Arrays.toString(path.list));
                }else{
                    System.out.println("no path");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void quickSort(int[] a, int low, int high){
        if(low>=high) return;

        int midle = low + (high-low)/2;
        int pivot = a[midle];
        int i=low; int j = high;
        while(i<=j){
            while(a[i]<pivot){
                i++;
            }
            while(a[j]>pivot){
                j--;
            }
            if(i<=j){
                int temp = a[i];
                a[i]=a[j];
                a[j]=temp;
                i++;
                j--;
            }
        }//end while
        if(low<j) quickSort(a, low, j);
        if(high>i) quickSort(a, i, high);
    }

    static class DirectedPath{
        boolean[] visited;
        boolean[] onStack;
        int[] edgeTo;
        int source;
        AndaList cycle;

        public DirectedPath(AndaGraph g, int s) {
            source=s;
            visited = new boolean[g.size];
            edgeTo = new int[g.size];
            onStack = new boolean[g.size];
            dfs(g, s);
        }

        private void dfs(AndaGraph g, int v) {
            visited[v]=true;
            onStack[v]=true;
            AndaList tetangga = g.getTetangga(v);
            for(Object o : tetangga.list){
                int w = (Integer) o;

                if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g, w);
                }else if(onStack[w]){
                    cycle = new AndaList();
                    for(int x=v; x!=w; x =edgeTo[x]){
                        cycle.add(x);
                    }
                    cycle.add(w);
                }
            }
            onStack[v]=false;
        }

        AndaList pathTo(int d){
            if(!visited[d]) return null;
            AndaList path = new AndaList();
            for(int x=d; x!=source; x=edgeTo[x]){
                path.add(x);
            }
            path.add(source);
            return path;
        }
    }

    static class DirectedCycle{
        boolean[] visited;
        boolean[] onStack;
        int[] edgeTo;
        AndaList cycle;

        public DirectedCycle(AndaGraph g) {
            visited = new boolean[g.size];
            edgeTo = new int[g.size];
            onStack = new boolean[g.size];
            for(int v=1;v<g.size;v++){
                if(!visited[v]){
                    dfs(g, v);
                }
            }
        }

        private void dfs(AndaGraph g, int v) {
            visited[v]=true;
            onStack[v]=true;
            AndaList tetangga = g.getTetangga(v);
            for(Object o : tetangga.list){
                int w = (Integer) o;
                if(cycle!=null){
                    return;
                }else if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g, w);
                }else if(onStack[w]){
                    cycle = new AndaList();
                    for(int x=v; x!=w; x =edgeTo[x]){
                        cycle.add(x);
                    }
                    cycle.add(w);
                }
            }
            onStack[v]=false;
        }
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
