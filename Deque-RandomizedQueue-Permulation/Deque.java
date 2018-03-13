import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item>{
    
    private Node first;
    private Node last; 
    private int n = 0;  //number of item in linked list
    
    private class Node{
        
        private Item item;
        private Node next;
        private Node pre;
    }
    
    
    
    public Deque(){
        
        first = null;
        last = null;
    }
    
    
    
    public boolean isEmpty(){return (n == 0);}
    
    
    
    public int size(){ return n; }
    
    
    
    public void addFirst(Item item){
        
        if (item == null){throw new java.lang.IllegalArgumentException();}
        
        Node helper = first;
        first = new Node();
        first.item = item;
        first.pre = null;
        
       if (!isEmpty()){
           
            first.next = helper;
            helper.pre = first;
        }
       else{
           
            first.next = null;
            last = first; 
        }
       
        n++;
    }
    
    
    
    public void addLast(Item item){
        
        if (item==null){throw new java.lang.IllegalArgumentException();}
        
        Node helper = last;
        last = new Node();
        last.next = null;
        last.item = item;
        
        if (!isEmpty()){
            
            last.pre = helper;
            helper.next = last;
        }
        else{
            
            last.next = null;
            first = last;
        }
        
        n++;
    }
    
    
    
    public Item removeFirst(){
        
        if (isEmpty()){throw new java.util.NoSuchElementException();}
        
        Item item = first.item;
        
        if (n > 1){
            
            first = first.next;
            first.pre = null; 
        }
        else{
            
            first = null;
            last = null;
        }
        
        n--;
        
        return item;
    }
    
    public Item removeLast(){
        
        if (isEmpty()){throw new java.util.NoSuchElementException();}
        
        Item item = last.item;
        if (n > 1){
            
            last = last.pre;
            last.next = null;
        }
        else{
            
            first = null;
            last = null;
        }
        
        n--;
        
        return item;
            
    }
    
    public Iterator<Item> iterator(){
        
        return new linkedIterator(); 
    }
    
    private class linkedIterator implements Iterator<Item>{
        
        private Node current = first;
        
        public boolean hasNext(){return current != null;}
        
        public void remove() {throw new java.lang.UnsupportedOperationException();}
        
        public Item next(){
            
            if (!hasNext()){throw new java.util.NoSuchElementException();}
            Item item = current.item;
            current = current.next;
            
            return (item);
        }
    }
    
    
    public static void main(String[] args){ //empty 
       /*
        Deque<Integer> l = new Deque<Integer>();
        for (int i = 0; i < 10; i++)
            l.addFirst(i);
        l.addFirst(3);
        System.out.println(l.size());

        */
    }
    
}
