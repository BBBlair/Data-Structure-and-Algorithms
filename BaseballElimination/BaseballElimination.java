import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;


public class BaseballElimination {
    
    // private arrays for the wins, losses and remaining games of a team
    private final String[ ] teams;
    private final int[ ] w;
    private final int[ ] loss;
    private final int[ ] r;
    private final int[ ][ ] g;
    private final int n;      // the number of teams;
    private Bag<String> rr;
    
    private FlowNetwork fnw;
    
    // constructor: create a baseball division from givenfilename in format specified below
    public BaseballElimination(String filename) {
        
        In in = new In(filename);
        
        if (in.isEmpty()) { throw new java.lang.IllegalArgumentException(); }
        
        n = in.readInt();     
        teams = new String[n];
        w = new int[n];
        loss = new int[n];
        r = new int[n];
        g = new int[n][n];
        
        int i = 0;
        
        while (in.hasNextLine() && i < n)
        {
            teams[i] = in.readString();
            
            w[i] = in.readInt();
            loss[i] = in.readInt();
            r[i] = in.readInt();
            
            for (int j = 0; j < n; j++)
            {
                g[i][j] = in.readInt();
            }
            
            i++;
        }
     }
    
    
    
    // a private helper to examina  to return the number of the team and  to check if the team is valid
    private int teamn(String team) {

        for (int x = 0; x < n; x++)
        {
            if (team.equals(teams[x])) { return x; }
        }

        throw new java.lang.IllegalArgumentException(); 
    }



    // number of teams
    public int numberOfTeams() {
        
        return n;
        
     }
    
    
    
    // all teams
    public Iterable<String> teams() {
        
        Bag<String> t = new Bag<String>();
        for (int tt = 0; tt < teams.length; tt++) { t.add(teams[tt]); }
        return t;
    }
    
    
    
    // number of wins for given team
    public int wins(String team) {
        
        int x = teamn(team);
        
        return w[x];
    }
    
    
    
    // number of losses for given team
    public int losses(String team) {
        
        int x = teamn(team);
        
        return loss[x];
    }
    
    
    
    // number of remaining games for given team
    public int remaining(String team) {
        
        int x = teamn(team);
        
        return r[x];
    }
    
    
    
    // number of remianing games between team1 and team2
    public int against(String team1, String team2) {
        
        int x1 = teamn(team1);
        int x2 = teamn(team2);
        
        return g[x1][x2];
    }
   
    
    
    // construct a FlowNetwork 
    private FlowNetwork network(int x) {
        
        // int e = (int) (2.5 * (n - 1) * (n - 2) + (n - 1));  // the number of edges: game + game*2 + team 
        int v = n * (n - 1)/2 + 2;                // the number of vertex: game vertex + team vertex + 2(s,t)
        int gg = (n - 1) * (n - 2)/2;             // the number of pairs of game party
        double infinity = Double.POSITIVE_INFINITY;
        int vn = 1;
        double pit = w[x] + r[x];                     // possible scores in total for team x

        fnw = new FlowNetwork(v);
        
        for (int i = 0; i < n; i++) {
            if (i == x) continue;   // do not involve ifself
            for (int j = i + 1; j < n; j++) {
                if (j == x) continue;   // do not involve with ifself
                // source to game vertex
                fnw.addEdge(new FlowEdge(0, vn, (double) g[i][j]));
                

                // game vertex to team vertex
                if (i < x) { fnw.addEdge(new FlowEdge(vn, gg+i+1, infinity)); }
                else { fnw.addEdge(new FlowEdge(vn, gg+i, infinity)); }
                if (j < x) { fnw.addEdge(new FlowEdge(vn, gg+j+1, infinity)); }
                else { fnw.addEdge(new FlowEdge(vn, gg+j, infinity));  }
                
                vn++;
            }
        }
        
        // team vertex to target 
        for (int y = 1; y < n; y++) {
            
            if (y <= x) { fnw.addEdge(new FlowEdge(gg+y, v-1, pit - w[y-1])); }
            else { fnw.addEdge(new FlowEdge(gg+y, v-1, pit - w[y])); }
        }
        
        return fnw;
        
    }

    
    
    // is given team eliminated?
    public boolean isEliminated(String team) {
        
        int xx = teamn(team);
        rr = new Bag<String>();
        boolean eli = false;
        
        // trivial elimination
        for (int y = 0; y < n; y++) {
            if (w[xx]+r[xx] < w[y])  { 
                rr.add(teams[y]);
                eli = true; }
        }
        
        if (eli) { return true; }

        int gg = (n - 1) * (n - 2)/2;  
        FlowNetwork current = network(xx);
        FordFulkerson ff = new FordFulkerson(current, 0, current.V() -1);
        String eliminated;

        for (FlowEdge e: current.adj(current.V() - 1)) {
            
            if (ff.inCut(e.from()))
            { 
                eli = true;
                if (e.from()-gg <= xx) { eliminated = teams[e.from()-gg-1]; }
                else { eliminated = teams[e.from()-gg]; }
                rr.add(eliminated);
            
            }
            else { continue; }
        }
        
        
        return eli;
    }
    
    
    
    // subset r of teams that eliminated given team; null if not eliminated 
    public Iterable<String> certificateOfElimination(String team) {
        
        if (isEliminated(team)) { return rr; }
        else { return null; }
    }
    

   
    
    public static void main(String[] args) {
        
        BaseballElimination division = new BaseballElimination(args[0]);

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset r = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    
    }
    
    }    

