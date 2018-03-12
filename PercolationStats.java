/*
Author: Blair Xiao
Date: Sep 10th, 2017
*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats{
    
    // an array stores the result from each test 
    private double[ ] result;
    
    private int trials;
    
    private double mean, stddev, confidenceLo, confidenceHi;
    
    
    // perform trails independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials){ 
        
        if (n <= 0) throw new IllegalArgumentException(" index n out of bounds ");  
        if (trials <= 0) throw new IllegalArgumentException(" index trails out of bounds "); 
        
        this.trials = trials;
        
        result = new double[trials];
        
        // Repeat the trails t times
        for (int t = 0; t < trials; t++)
        {            
            Percolation pl = new Percolation(n);
            while (!pl.percolates())
            {
                int rrow = StdRandom.uniform(1,n+1);
                int ccol = StdRandom.uniform(1,n+1);
                  
                if (!pl.isOpen(rrow,ccol)){ pl.open(rrow,ccol); }
                else{ continue; }
            }     
                       
            int OpenNo = pl.numberOfOpenSites();
            result[t] = ((double) OpenNo)/(n*n);     
        }
        
        mean = StdStats.mean(result);
        stddev = StdStats.stddev(result);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }
    
    
    
    // calculate the mean of ps in t times trails
    public double mean(){            
        
         return mean;
    }
    
    
    
    // calculate the standard deviation
    public double stddev(){ 
        
        if (trials == 1){ return Double.NaN; }
        return stddev; 
    }
    
    
    
    public double confidenceLo(){
        
        return confidenceLo;
    }
    
    
    
    public double confidenceHi(){
        
        return confidenceHi;
    }
        
    
    
    public static void main(String[] args){
    
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats stats=new PercolationStats(n,trials);
        
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
    
    }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        