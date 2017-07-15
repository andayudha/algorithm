package data_structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 8/8/2015.
 */
public class Util {

    public static void quickSort(int[] a, int low, int high){
        if(low>=high) return;
        //center index as pivot
        int middle = low+(high-low)/2;
        int pivot = a[middle];

        //partition
        int i= low; int j=high; //index
        while(i<=j){
            //left
            while(a[i]<pivot){
                i++;
            }
            //right
            while(a[j]>pivot){
                j--;
            }
            if(i<=j){
                //swap
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                i++;
                j--;
            }

        }//end of while
        System.out.println("pivot : "+pivot);
        System.out.println(Arrays.toString(a));
        //do recursive for two partiotions
        if(low<j) quickSort(a, low, j);
        if(high>i) quickSort(a, i, high);
    }

    public static int gcd(int a, int b){
        if(b==0) {
            return a;
        }else{
            return gcd(b, a%b);
        }

    }

    public static boolean isPrime(int x){
        if(x%2==0) return false;
        for(int i=3; i*i<=x; i+=2){
            if(x%i==0)return false;
        }
        return true;
    }

    public static int fibonacci(int n){
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;
        else
            return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static int lcm(int a,int b){
        return a*b/gcd(a,b);
    }

    public static List<List<Integer>> combi(int[] a, int r){
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        int length = a.length;
        int count = (int)Math.pow(2, length);
        for(int i=0;i<count;i++){
            List<Integer> sublist = new ArrayList<Integer>();
            for(int j=0;j<length;j++){
                int pos = i<<j;
                if((i&pos) == pos){
                    sublist.add(a[j]);
                }
            }
            if(sublist.size()==r){
                list.add(sublist);
            }
        }
        return list;
    }
}
