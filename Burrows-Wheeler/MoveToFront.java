import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class BurrowsWheeler
{    
    private static final int R = 256;
    private static CircularSuffixArray csArray;
    private static char[] transform;


    
    // apply Burrow-Wheeler transform, reading from standard input and writing to standard output
    public static void transform()
    {
        String sInput = BinaryStdIn.readString();
        char[] string = sInput.toCharArray();
        csArray = new CircularSuffixArray(sInput);
        transform = new char[csArray.length()];
        
         // find out the row in the sorted array that matches the original string(sInput)
        for (int i = 0; i < string.length; i++)   
        { 
            int firstletter = csArray.index(i);
            if (firstletter == 0)  { BinaryStdOut.write(i);   transform[i] = string[string.length-1]; }
            else { transform[i] = string[firstletter -1];   }
        }
        
        String str = String.valueOf(transform);
        BinaryStdOut.write(str);
        
        BinaryStdOut.close();
         
    }
    
    
    
    // apply Burrow-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform()
    {
        int firstletter = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        int n = t.length(); 
        
        int[] count = new int[R+1];
        int[] next = new int[n];
        
        // count the frequency of each char appearance in the input string
        for (int i = 0; i < n; i++)
        {   count[t.charAt(i)+1]++;  }
        
        // convert the frequency to index
        for (int i = 1; i <= R; i++)
        {   count[i] = count[i] + count[i-1]; }
        
        // put the index into the next[]
        for (int i = 0; i < n; i++)
        {   next[count[t.charAt(i)]++] = i; }
        
        // construct the original string with next[] and first and t[]
        int index = next[firstletter];
        
        for (int i = 0; i < n; i++)
        {
            BinaryStdOut.write(t.charAt(index));
            index = next[index];
        }
        
        BinaryStdOut.close();
    }
    
    
    
    // if args[0] is ' - ', apply move-to-front encoding
    // if args[0] is ' + ', apply move-to-front decoding
    public static void main(String[] args)
    {
         if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
        
    }
}
