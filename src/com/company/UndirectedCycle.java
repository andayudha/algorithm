package com.company;

import java.util.List;
import java.util.Stack;

/**
 * Created by SRIN on 8/5/2015.
 */
public class UndirectedCycle {
    private boolean[] visited;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    public UndirectedCycle(AndaGraph g) {
        visited = new boolean[g.N];
        edgeTo = new int[g.N];
        for(int v=1;v<g.N;v++){
            if(!visited[v]) dfs(g, -1, v);
        }
    }

    private void dfs(AndaGraph g, int u, int v) {
        visited[v] = true;

        List<Integer> tetangga = g.getNeighbours(v);
        for(int w : tetangga){
            if(cycle!=null){
                return;
            }else if(!visited[w]){
                edgeTo[w]=v;
                dfs(g, v, w);
            }else if( w != u){
                cycle = new Stack<Integer>();
                for(int x = v; x!=w; x=edgeTo[x]){
                    cycle.push(x);
                }
                cycle.push(w);
            }

        }
    }
    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
