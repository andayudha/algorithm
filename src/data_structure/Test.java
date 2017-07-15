package data_structure;

import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 8/8/2015.
 */
public class Test {

    public static void main(String[] args) {
//        MyList list = new MyList();
//        for(int i=1;i<=5;i++){
//            list.add(i);
//            list.print();
//        }
//        list.remove(list.indexOf(4));
//        list.print();
//        list.add(7);
//        list.print();
//        list.contains(2);
//        System.out.println(list.contains(3));
//        System.out.println(list.contains(6));

//        MyStack stack = new MyStack(5);
//        for(int i=1;i<=5;i++){
//            stack.push(i);
//            stack.print();
//        }
//        System.out.println(stack.contains(5));
//        System.out.println(stack.contains(9));
//        System.out.println(stack.pop());
//        stack.print();
//        System.out.println(stack.pop());
//        stack.print();

//        MyQeueu q = new MyQeueu(10);
//        for(int i=1;i<=5;i++){
//            q.enqueue(i);
//            q.print();
//        }
//        System.out.println(q.contains(2));
//        System.out.println(q.dequeue());
//        q.print();

//        int[] a = new int[]{5,7,1,3,6,2,9,0,4};
//        System.out.println(Arrays.toString(a));
//        Util.quickSort(a, 0, a.length - 1);
//        System.out.println(Arrays.toString(a));

//        int[] a = new int[]{5,7,1,3};
//        List<List<Integer>> list = Util.combi(a, 2);
//        for(List<Integer> sublist : list){
//            System.out.println(Arrays.toString(sublist.toArray()));
//        }

        System.out.println(Util.fibonacci(8)==21);
        System.out.println(Util.fibonacci(13)==233);
        System.out.println(Util.fibonacci(23)==28657);
    }
}
