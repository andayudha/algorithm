package com.company;

import java.util.List;

/**
 * Created by user on 8/9/2015.
 */
public class DFS {
    /*default, for all cases*/
    boolean[] visited;

    /*for connectivity*/
    int[] componentsId;
    int components;

    /*for path or cycle*/
    int []edgeTo;
    int source;
    List cycle; //for cycle only

    /*for coloring bipartite*/
    boolean[] color;
    boolean twoColorable = true;

    /*constructor*/
    public DFS(AndaGraph g) {
        visited = new boolean[g.N];

        /*connectivity*/
        componentsId = new int[g.N];
        components=0;
        for(int v=1;v<g.N;v++){
            if(!visited[v]) {
                components++;
//                dfs(g,v);
            }
        }

        /*for coloring*/


        edgeTo = new int[g.N];
        components = 0;
    }
}
