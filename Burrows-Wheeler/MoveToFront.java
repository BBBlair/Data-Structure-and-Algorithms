import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;


public class MoveToFront
{    
    private static final int R = 256;

    
    
    public MoveToFront() {  }
    
    
    
    private static char[] alphabetArray()
    {
        // Initialize the alphabet
        char[] dict = new char[R];
        for (char i = 0; i < R; i++) { dict[i] = i; }
        
        return dict;
    }
    
    
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode()
    {
        char[] order = alphabetArray();
        
        while (!BinaryStdIn.isEmpty())
        {
             // Read in 8-bits char from input one at a time
            char c = BinaryStdIn.readChar(8);
            int out = -1;
            
            // Locate the letter in the alphabet
            for (int i = 0; i < R; i++)
            {
                char cInOrder = order[i];
                if (c == cInOrder)  
                {   
                    BinaryStdOut.write(i, 8); 
                    out = i;
                    break; 
                }
            }
                // Move the letter to the front in the dictionary
                while ( out > 0)
                {
                    order[out] = order[out-1];
                    out--;
                }
                order[0] = c;
            }
        
        BinaryStdOut.close();
    }
    
    
    
    // apply move-to--front decoding, reading from standard input and witing to standard output
    public static void decode()
    {
        char[] order = alphabetArray();
        
        while (!BinaryStdIn.isEmpty())
        {
            int i = BinaryStdIn.readInt(8);
            char c = order[i];
            
            BinaryStdOut.write(c);
            
            // Move the letter to the front
            while (i > 0)
            {
                order[i] = order[i-1];
                i--;
            }
            order[0] = c;
        }
       
        BinaryStdOut.close();
    }
    
    
    
    // if args[0] is ' - ', apply move-to-front encoding
    // if args[0] is ' + ', apply move-to-front decoding
    public static void main(String[] args)
    {
        if      (args[0].equals("-")) { encode(); }
        else if (args[0].equals("+")) { decode(); }
        else { throw new IllegalArgumentException("Illegal command line argument"); }
    }
}