import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation{
    public static void main(String[] args){
        
        int k=Integer.parseInt(args[0]);
        RandomizedQueue<String> rq=new RandomizedQueue<String>();
        while (!StdIn.isEmpty()){
            
            String item=StdIn.readString();
            rq.enqueue(item);
        
        }
        
        for (int kk=0;kk<k;kk++){
            String output=rq.dequeue();
            StdOut.println(output);
        }
    }
}