import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
    
    private Item[] targetArray;
    private int n;  // number of Item
    
    public RandomizedQueue(){
        
        targetArray = (Item[]) new Object[2];
        n = 0;
    }
    
    
    
    private void resize(int c){
        
        Item[] temp = (Item[]) new Object[c];
        
        for (int i = 0; i < n; i++){
            
            temp[i] = targetArray[i];
        }
        
        targetArray = temp;
        
    }
    
    
    
    public boolean isEmpty(){return n == 0;}
    
    
    
    public int size(){return n;}
    
    
    
    public void enqueue(Item item){
        
        if (item == null){throw new java.lang.IllegalArgumentException();}
        
        if (n == targetArray.length) {resize(2*targetArray.length);}
       
        targetArray[n++] = item;   
    }
    
    
    public Item dequeue(){
        
        if (isEmpty()){throw new java.util.NoSuchElementException();}
        
        int randomNum = StdRandom.uniform(n);
        Item item = targetArray[randomNum];
        
        // replace to chosen one with the last item
        targetArray[randomNum] = targetArray[--n];
        targetArray[n] = null;
        
        if(n < targetArray.length/4) resize(targetArray.length/2);
        
        return item;  
    }
    
    
    
    public Item sample(){
        
        if (isEmpty()){throw new java.util.NoSuchElementException();}
        
        return targetArray[StdRandom.uniform(n)];
    }
    
    
    
    public Iterator<Item> iterator(){
        
        return new arrayIterator(); 
    }
    
    
    
    private class arrayIterator implements Iterator<Item>{
        
        private int i = 0;
        private Item ar[] = (Item[]) new Object[n];
        
        private arrayIterator(){
            
            for (int t = 0; t < n; t++){ar[t] = targetArray[t];}
        }        
        
        
        
        public boolean hasNext(){return i <= n - 1;}
        
        
        
        public void remove() {throw new java.lang.UnsupportedOperationException();}
        
        
        
        public Item next(){
            
            if (!hasNext()){throw new java.util.NoSuchElementException();}
            
            int random = StdRandom.uniform(n);
            Item item = ar[random];
            
            ar[random] = ar[n-1];
            ar[--n] = null;
            
            
            return  item;
        }
    }
    
     public static void main(String[] args){ }     
        
}