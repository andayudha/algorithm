

package hackerrank;

import java.io.*;
import java.util.*;

public class CellConnect {

    public static void main(String[] args) throws IOException{
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        Solve s = new Solve(m,n);
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                int x = sc.nextInt();
                s.add(x, i, j);
            }
        }
        System.out.println(""+s.max);
    }

    static class Solve{
        private int[][] a;
        private int[][] group;
        private int[] counterGroup;
        int m, n;
        int max = 0;
        int groups;

        public Solve(int m, int n) {
            this.m = m;
            this.n = n;
            a = new int[m][n];
            group = new int[m][n];
            counterGroup = new int[(m*n/2)+1];
        }

        void add(int x, int i,int  j){
            a[i][j]=x;
            if(i==0 && j==0 && x==1){
                newGroup(i, j);
                return;
            }
            if(i==0 && x==1) {//cek kiri
                if(a[i][j-1]==1){
                    group[i][j]=group[i][j-1];
                    counterGroup[group[i][j]]++;
                    max = Math.max(max, counterGroup[group[i][j]]);
                }else{
                    newGroup(i, j);
                }
                return;
            }
            if(j==0 && x==1) {//cek atas
                if(a[i-1][j]==1){
                    group[i][j]=group[i-1][j];
                    counterGroup[group[i-1][j]]++;
                    max = Math.max(max, counterGroup[group[i][j]]);
                }else if(i>0 && a[i-1][j+1]==1){//diagonal kanan atas
                    group[i][j]=group[i-1][j+1];
                    counterGroup[group[i][j]]++;
                    max = Math.max(max, counterGroup[group[i][j]]);
                }else{
                    newGroup(i,j);
                }
                return;
            }
            if(x==1){
                if(a[i-1][j]==0 && a[i][j-1]==0 && a[i-1][j-1]==0 && j<n-1 && a[i-1][j+1]==0){ //new group
                    newGroup(i, j);
                }else if(a[i-1][j]==0 && a[i][j-1]==0 && a[i-1][j-1]==0 && j==n-1){
                    newGroup(i, j);
                }else if(j<n-1 && a[i-1][j+1]==1){
                    if(a[i-1][j-1] ==1 && group[i-1][j-1]!=group[i-1][j+1]){
                        merge(i-1, j-1, i-1, j+1);
                        group[i][j] = group[i-1][j+1];
                    }else if(a[i][j-1] ==1 && group[i][j-1]!=group[i-1][j+1]){
                        merge(i, j-1, i-1, j+1);
                        group[i][j] = group[i-1][j+1];
                    }else if(a[i-1][j-1]==0 && a[i][j-1]==0){
                        group[i][j] = group[i-1][j+1];
                        counterGroup[group[i][j]]++;
                    }else if(a[i-1][j-1] ==1 /*&& group[i-1][j-1]==group[i][j-1]*/ && group[i-1][j-1]==group[i-1][j+1]){
                        group[i][j] = group[i-1][j+1];
                        counterGroup[group[i][j]]++;
                    }else if(a[i][j-1] ==1 &&  group[i][j-1]==group[i-1][j+1]){
                        group[i][j] = group[i-1][j+1];
                        counterGroup[group[i][j]]++;
                    }

                }else if(a[i-1][j]==1){
                    group[i][j]=group[i-1][j];
                    counterGroup[group[i][j]]++;
                }else if(a[i][j-1]==1){
                    group[i][j]=group[i][j-1];
                    counterGroup[group[i][j]]++;
                }else if(a[i-1][j-1]==1){
                    group[i][j]=group[i-1][j-1];
                    counterGroup[group[i][j]]++;
                }

                max = Math.max(max, counterGroup[group[i][j]]);
            }
        }

        void newGroup(int i, int j){
            groups ++; group[i][j]=groups;
            counterGroup[group[i][j]]++;
            max = Math.max(max, counterGroup[group[i][j]]);
        }

        void merge(int i1, int j1, int i2, int j2){
            int maxGroup = group[i2][j2];
            int oldGroup = group[i1][j1];
            for(int a =0;a<=i1;a++){
                for(int b=0;b<=j1;b++){
                    if(group[a][b]==group[i1][j1]) group[a][b]=maxGroup;
                }
            }
            counterGroup[maxGroup] = counterGroup[maxGroup] + counterGroup[oldGroup] +1;
            counterGroup[oldGroup] =0;
            max = Math.max(max, counterGroup[maxGroup]);
        }
    }
}
