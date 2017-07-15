package practice;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by user on 8/9/2015.
 */
public class Practice1 {

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
                Path p = new Path(g, 1);
                if(p.reachTo(5)){
                    System.out.println(Arrays.toString(p.pathTo(5).list));
                }else{
                    System.out.println("not reachable");
                }

                Cycle c = new Cycle(g);
                if(c.hasCycle()){
                    System.out.println(Arrays.toString(c.cycle.list));
                }else{
                    System.out.println("no cycle");
                }
//                if(new Coloring(g).twoColorable){
//                    System.out.println("YES");
//                }else{
//                    System.out.println("NO");
//                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class Coloring{
        boolean[] visited;
        boolean[] color;
        boolean twoColorable = true;

        public Coloring(AndaGraph g){
            visited = new boolean[g.size];
            color = new boolean[g.size];
            for(int v = 1; v<g.size;v++){
                if(!visited[v]){
                    dfs(g, v);
                }
            }
        }

        private void dfs(AndaGraph g, int v) {
            visited[v] = true;

            AndaList tetangga = g.getTetangga(v);
            for(Object o : tetangga.list){
                int w = (Integer) o;
                if(!visited[w]){
                    color[w]=!color[v];
                    dfs(g, w);
                }else if(color[w]==color[v]){
                    twoColorable=false;
                }
            }
        }


    }

    static class Cycle{
        boolean[] visited;
        int []edgeTo;
        AndaList cycle;

      private void dfs(AndaGraph g, int v) {
        }  public Cycle(AndaGraph g) {
            visited = new boolean[g.size];
            edgeTo = new int[g.size];
            for(int v=1;v<g.size;v++){
                if(!visited[v]){
                    dfs(g, -1, v);
                }
            }
        }

        boolean hasCycle(){
            return cycle!=null;
        }

        void dfs(AndaGraph g,int u, int v){
            visited[v]=true;
            AndaList tetangga = g.getTetangga(v);
            for(Object o : tetangga.list){
                int w = (Integer) o;
                if(cycle!=null) {
                    return;
                }else if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g,v, w);
                }else if(u!=w){
                    cycle = new AndaList();
                    for(int x=v; x!=w; x=edgeTo[x]){
                        cycle.add(x);
                    }
                    cycle.add(w);
                }
            }
        }
    }

    static class Path{
        boolean[] visited;
        int []edgeTo;
        int source;

        public Path(AndaGraph g, int s) {
            visited = new boolean[g.size];
            edgeTo = new int[g.size];
            source=s;
            dfs(g, s);
        }

        void dfs(AndaGraph g, int v){
            visited[v]=true;
            AndaList tetangga = g.getTetangga(v);
            for(Object o : tetangga.list){
                int w = (Integer) o;
                if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g, w);
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

        boolean reachTo(int v){
            return visited[v];
        }
    }

    static int charToInt(char c){
        int temp = (int)c;
        int temp_integer = 64; //for upper case
        if(temp<=90 & temp>=65){
            return temp-temp_integer;
        }else {
            return 0;
        }
    }

    static class Connected{
        boolean[] visited;
        int[] componentsId;
        int components;
        int []edgeTo;
        AndaList cycle;

        public Connected(AndaGraph g) {
            visited = new boolean[g.size];
            componentsId = new int[g.size];
            edgeTo = new int[g.size];
            components = 0;
            for(int v=1;v<g.size;v++){
                if(!visited[v]){
                    components++;
                    dfs(g, v);
                }
            }
        }

        void dfs(AndaGraph g, int v){
            visited[v]=true;
            componentsId[v]=components;
            AndaList tetangga = g.getTetangga(v);
            for(Object o : tetangga.list){
                int w = (Integer) o;
                if(!visited[w]){
                    edgeTo[w]=v;
                    dfs(g, w);
                }
            }
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
