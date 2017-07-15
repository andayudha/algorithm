package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(new File("graph.txt"));
            int V = sc.nextInt();
            int E = sc.nextInt();
            AndaGraph G = new AndaGraph(V, true); //directed graph
            for(int e=0;e<E;e++){
                int u = sc.nextInt();
                int v = sc.nextInt();
                G.addEdge(u,v);
            }
            G.print();
            int u=1,v=5;
            UndirectedPath testPath = new UndirectedPath(G, u);
            int destination = v;
            if(testPath.hasPathTo(destination)){
                List<Integer> path = testPath.pathTo(destination);
                System.out.println(Arrays.toString(path.toArray()));
            }else{
                System.out.println("no path from "+u+" to "+v);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
