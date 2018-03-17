import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MergeBU;


public class CircularSuffixArray
{
    private CircularSuffix[] csArray;
    private String s;

    
    
    // circular suffix array of s
    public CircularSuffixArray(String s)
    {
        if (s == null) { throw new java.lang.IllegalArgumentException(); }
        this.s = s;
        csArray = new CircularSuffix[s.length()];
        
        for (int i = 0; i < s.length(); i++)
        {
            csArray[i] = new CircularSuffix(s, i, i);
        }
        
         MergeBU.sort(csArray);
    }
 
 
    
    private String getcs() 
    { 
        String out = "";
        for (CircularSuffix cs: csArray)
        {
            out += cs.toString();
        }
        
        return out;
    }
 
    
    
    // length of s
    public int length()
    {
        return s.length();
    }
    
    
    
    //returns index of ith sorted suffix
    public int index(int i)
    {
        if (i < 0 || i >= s.length()) { throw new java.lang.IllegalArgumentException(); }
        return csArray[i].index;
    }
    
    
    
    public static void main(String[] args)
    {
        String strings = "AAABBAAABB";

        CircularSuffixArray suffix = new CircularSuffixArray(strings);
        StdOut.println(strings);
        StdOut.println("length: " + suffix.length());
        
        StdOut.println(suffix.index(7));
        StdOut.println(suffix.getcs());
                      
    }
    
 /*==========================================================================================*/
    
    
    
    private static class CircularSuffix implements Comparable<CircularSuffix>
    {
        private int fletter;
        private int index;
        private String string;
        
        
        
        public CircularSuffix(String string, int fletter, int index)
        {
            this.string = string;
            this.index = index;
            this.fletter = fletter;
        }
        
        
        
        public int compareTo(CircularSuffix that)
        {
            int i = this.fletter;   
            int j = that.fletter;
            int count = 0;
            
            while ( count < this.string.length())
            {   
                if (this.string.charAt(i) > that.string.charAt(j)) { return +1; }
                if (this.string.charAt(i) < that.string.charAt(j)) { return -1; }

                i++; if ( i == this.string.length()) { i = 0; }
                j++; if (j == that.string.length()) { j = 0; }
                count ++;
            }
            
            return 0; 
        }
        
        
        
        public String toString()
        {
            return  string.charAt(fletter) + "_" + index + " ";
        }
}
}
