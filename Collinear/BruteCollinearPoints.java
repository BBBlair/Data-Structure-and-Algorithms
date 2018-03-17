import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints{
    
    private int NoSegment = 0;
    private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
    
    
    
    //finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] point){  

        if(point == null){ throw new java.lang.IllegalArgumentException();}
        
        int n = point.length;
        
        //check if there is null point
        for (int z = 0;z < n;z++){ 
            if (point[z] == null){throw new java.lang.IllegalArgumentException();}
        }
       
        Point[] pointsCopy = Arrays.copyOf(point, point.length);
        Arrays.sort(pointsCopy);

        
        // check if there is duplicate point
        for (int y = 0;y < n - 1;y++){ 
            if (pointsCopy[y].compareTo(pointsCopy[y+1]) == 0) {throw new java.lang.IllegalArgumentException();}
        }

        for (int p = 0;p <= n - 4;p++){
            for (int q = p+1;q <= n - 3;q++){               
                for (int r = q + 1;r <= n - 2;r++){
                    
                    double pq = pointsCopy[p].slopeTo(pointsCopy[q]);
                    double qr = pointsCopy[q].slopeTo(pointsCopy[r]);
                    
                    if (pq == qr){
                        
                        for (int s = r + 1;s <= n - 1;s++){
                            
                            double rs = pointsCopy[r].slopeTo(pointsCopy[s]);
                            
                            if (qr == rs) {
                                
                                NoSegment++;
                                LineSegment ps = new LineSegment(pointsCopy[p],pointsCopy[s]);
                                ls.add(ps);
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    
    public int numberOfSegments(){//the number of line segments
        
        return NoSegment;
    }
    
    
    
    public LineSegment[ ] segments(){//the line segments
        
        LineSegment[] LineSegmentArray = new LineSegment[NoSegment];
        
        for (int i = 0;i < NoSegment;i++){ LineSegmentArray[i] = ls.get(i);}
        
        return LineSegmentArray;
    }
    
    
    
    public static void main(String[] args) {
        
        
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        
        StdOut.println("after sorting");
        for (int u=0;u<points.length;u++){
            
             StdOut.println(points[u].toString());
        }
        
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        
    }
    
}
