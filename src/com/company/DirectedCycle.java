package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by SRIN on 8/5/2015.
 */
public class DirectedCycle {
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private List<Integer> cycle;    // directed cycle (or null if no such cycle)
    private Stack stack;

    public DirectedCycle(AndaGraph G) {
        marked  = new boolean[G.N];
        onStack = new boolean[G.N];
        stack = new Stack();
        edgeTo  = new int[G.N];
        for (int v = 1; v < G.N; v++){
            if (!marked[v]) dfs(G, v);
        }
    }

    private void dfs(AndaGraph g, int v){
        onStack[v] = true;
        stack.push(v);
        marked[v]=true;

        //for all neighbours
        for(int w : g.getNeighbours(v)){
            //stop if cycle not null
            if(cycle!=null) {
                return;
            }else if(marked[w]==false){ //kalo sitetangga blm dimarked, dfs
                edgeTo[w]=v; //isi titik sebelum si tetangga itu
                dfs(g, w);
            }else if(/*onStack[w]==true*/ stack.contains(w)){// ketika sitetangga ada di stack(tetangga yg udah pernah di marked/visit) back edge
                cycle = new ArrayList<Integer>();
                for(int x=v; x!=w; x=edgeTo[x]){// mula2 x=v; terus x di replace sama edgeTo[x] sampe x!=2
                    //x=edgeTo[x] : ngetrack semua titik sebelum titik x
                    cycle.add(x);
                }
                cycle.add(w);
                cycle.add(v);
            }
        }// end for tetangga

        onStack[v]=false;
        stack.pop();
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
