/*
Author: Blair Xiao
Date: Sep 10th, 2017
*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    
    private final WeightedQuickUnionUF uf;
    
    // a separate to keep track of Open&Block
    private boolean[] OpenOrBlock;        
    
    // set up two extra points: the top one connects the first row while the bottom one connects the lat row
    private final int vtop;
    private final int vbottom;
    
    // the number of the grid
    private final int n;
    
    // the number of open sites
    private int countOpen = 0;

    
    
    public Percolation(int n){     
        
        if (n <= 0) throw new IllegalArgumentException(" size of the grid n out of bounds ");        
        
        this.n = n;

        OpenOrBlock = new boolean[n*n];
   
        uf = new WeightedQuickUnionUF(n*n+2);
        
        // Points inside the grid range from [0,n*n-1], the extra top is n*n while the extra bottom is n*n+1
        vtop = n*n;     
        vbottom = n*n+1;
    }

    
    
    // Open the site if it is not open
    public void open(int row, int col){ 
         
         int ConvertedNo = conversion(row,col);

         if (!isOpen(row,col)) {
             countOpen++;
             OpenOrBlock[ConvertedNo] = true;
         }
         
         // connect the specific point with top/bottom if it is at the first/last row
         if (row == 1) {uf.union(vtop,ConvertedNo);}
         if (row == n) {uf.union(vbottom,ConvertedNo);}

         
         // check the left, right, up and down in order
         if ((col-1) >= 1 && isOpen(row,col-1))
         {
             uf.union(ConvertedNo, ConvertedNo - 1); 
             
             if (row == 1) {uf.union(vtop,ConvertedNo);}
             if (row == n) {uf.union(vbottom,ConvertedNo);}
         }
             
         if ((col+1) <= n && isOpen(row,col+1)){

            uf.union( ConvertedNo, ConvertedNo + 1);
            
            if (row == 1) {uf.union(vtop,ConvertedNo);}
            if (row == n) {uf.union(vbottom,ConvertedNo);}
         }   
         
         if ((row-1) >= 1 && isOpen(row-1,col)){
            
            uf.union( ConvertedNo, ConvertedNo - n);
            
            if (row == 1) {uf.union(vtop,ConvertedNo);}
            if (row == n) {uf.union(vbottom,ConvertedNo);}
         } 
         
         if ((row+1) <= n && isOpen(row+1,col)){
            
             uf.union( ConvertedNo, ConvertedNo + n);
             if (row == 1) {uf.union(vtop,ConvertedNo);}
             if (row == n) {uf.union(vbottom,ConvertedNo);}
         } 

     }
     
     
     
    //check if the site is open
    public boolean isOpen(int row, int col){ 
        
        return OpenOrBlock[conversion(row,col)];
    }
    
    
    
    //check if the site is rooted in the top virtual site
    public boolean isFull(int row, int col){ 
 
         if (isOpen(row,col) && uf.connected(vtop,conversion(row,col))) { return true;}
         else { return false;}
     }      
    

         
    // calculate how many site has been open     
    public int numberOfOpenSites( ) { 
         
          return countOpen;
     }
     

     
     // check if the grid is percolated
    public boolean percolates(){

         return (uf.connected(vtop,vbottom));
     }
     
    
    
    // converted to the according point number([0,n*n-1) of the grid from the row and column number
    private int conversion(int row, int col){
    
        isValid(row, col);
        return ((row-1)*n+(col-1));   
    }
    
    
    
    // check if the row and column numbers are validate
    private void isValid(int row, int col){
        
        if (row<1 || row>n || col<1 || col>n)
         {
             throw new IllegalArgumentException(" index row or col out of bounds ");  
         }
    }

     
}
