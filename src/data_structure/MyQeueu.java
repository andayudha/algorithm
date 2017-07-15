package data_structure;

import java.util.Arrays;

/**
 * Created by user on 8/8/2015.
 */
public class MyQeueu {
    private Object [ ] array;
    private int        currentSize;
    private int        front;
    private int        back;

    public MyQeueu( int capacity )
    {
        array = new Object[ capacity ];
        makeEmpty( );
    }


    public boolean isEmpty( )
    {
        return currentSize == 0;
    }


    public boolean isFull( )
    {
        return currentSize == array.length;
    }


    public void makeEmpty( )
    {
        currentSize = 0;
        front = 0;
        back = -1;
    }

    public boolean contains(Object x){
        for(int i=front;i<=back;i++){
            if(array[i].equals(x)) return true;
        }
        return false;
    }

    public Object getFront( )
    {
        if( isEmpty( ) )
            return null;
        return array[ front ];
    }

    public Object dequeue( )
    {
        if( isEmpty( ) )
            return null;
        currentSize--;

        Object frontItem = array[ front ];
        array[ front ] = null;
        front = increment( front );
        return frontItem;
    }


    public void enqueue( Object x )
    {
        if( isFull( ) ){
            return;
        }
        back = increment( back );
        array[ back ] = x;
        currentSize++;
    }

    private int increment( int x )
    {
        if( ++x == array.length )
            x = 0;
        return x;
    }


    public void print(){
        for(int i=front;i<=back;i++){
            System.out.print(array[i]+", ");
        }
        System.out.println();
    }
}
