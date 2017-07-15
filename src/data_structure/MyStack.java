package data_structure;

import java.util.Arrays;

/**
 * Created by user on 8/8/2015.
 */
public class MyStack {
    private int top;
    private Object[] storage;

    MyStack(int capacity) {
        storage = new Object[capacity];
        top = -1;
    }

    void push(int value) {
        top++;
        storage[top] = value;
    }

    Object peek() {
        return storage[top];
    }

    Object pop() {
        Object x= storage[top];
        top--;
        return x;
    }

    boolean isEmpty() {
        return (top == -1);
    }

    public boolean contains(Object x){
        for(int i=0;i<=top;i++){
            if(storage[i].equals(x)) return true;
        }
        return false;
    }

    public void print(){
//        System.out.println(Arrays.toString(storage));
        for(int i=0;i<=top;i++){
            System.out.print(storage[i]+", ");
        }
        System.out.println();
    }

}
