import java.util.*;

class GraphL{

    public static void MatrixR(){
        int n = 4;
        int m = 6;
        int adj[][] = new int[n+1][m+1];

        // edge 1 -- 2
        adj[1][2] = 1;
        adj[2][1] = 1;

        // edge 2 -- 3
        adj[2][3] = 1;
        adj[3][2] = 1;

        // edge 3 -- 4
        adj[3][4] = 1;
        adj[4][3] = 1;

        /*
        * adj[u][v] = 1;
        * adj[v][u] = 1;
        */

    }


    public static void ListR(){
        int n = 3; 
        int m = 3;
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i<=n ; i++){
            adj.add(new ArrayList<Integer>());

            // edge 1 -- 2
            adj.get(1).add(2);
            adj.get(2).add(1);
            
            // edge 2 -- 3
            adj.get(2).add(3);
            adj.get(3).add(2);
            
            // edge 3 -- 4
            adj.get(3).add(4);
            adj.get(4).add(3);

            /*
            * adj.get(u).add(v);
            * adj.get(v).add(u);
            */
        }

        // Print
        for(int i = 1; i<n ; i++){
            for(int j = 0; i<adj.get(1).size(); j++){
                System.out.println(adj.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj){

        ArrayList<Integer> bfs = new ArrayList<>();

        boolean vis[] = new boolean[V];

        Queue<Integer> q = new LinkedList<>();

        q.add(0);
        vis[0] = true;

        while(!q.isEmpty()){
            Integer node  = q.poll();
            bfs.add(node);

            for(Integer it: adj.get(node)){
                if(vis[it] == false){
                    vis[it] = true;
                    q.add(it);
                }
            }
        }
        
        return bfs;
    }

      public static void dfs(int node, boolean[] vis, ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> ls){
        vis[node] = true;
        ls.add(node);

        for (Integer it : adj.get(node)) {
            if(vis[node] == false){
                dfs(it, vis, adj, ls);
            }
        }
    }

    public static ArrayList<Integer> dfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj){
        
        // Boolean array to keep track of visited vertices
        boolean vis[] = new boolean[V+1];
        vis[0] = true;
        
        ArrayList<Integer> ls = new ArrayList<>();
        dfs(V, vis, adj, ls);

        return ls;
    }

    // adj matrix to adj list
    private static void change(ArrayList<ArrayList<Integer>> adj, int V){
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i<V; i++){
            for(int j = 0; j<V; j++){
                if(adj.get(i).get(j) == 1 && i != j){
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
        }
    }

    private static void dfsnP(int i, int[] vis,ArrayList<ArrayList<Integer>> adjList ){
        // DFS call
        vis[i] = 1;

        for(Integer it: adjList.get(i)){
            if(vis[it] == 0){
                dfsnP(it, vis, adjList);
            }
        }

    }

     private static int numProvinces(ArrayList<ArrayList<Integer>> adj, int V) {
        // code here

        // Converting adj matrix to adj list
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();

        for(int i = 0; i<V; i++){
            adjList.add(new ArrayList<Integer>());
        }

        for(int i = 0; i< V; i++){
            for (int j = 0; j< V; j++){
                if(adj.get(i).get(j) == 1 && i != j){
                    adjList.get(i).add(j);
                    adjList.get(j).get(i);
                }
            }
        }
        // to store the total count 
        int count = 0;
        // to store the visited index values
        int[] vis = new int[V];

        for(int i = 0; i< V; i++){
            if(vis[i] == 0){
                count++;
                dfsnP(i,vis, adjList);
            }
        }

        return count;
    }

    public static int numIslands(char[][] grid) {
        // Code here
        int n = grid.length;
        int m = grid[0].length;

        int[][] vis = new int[n][m];
        int count = 0;

        // checking each and every cell in the grid 
        for(int row = 0; row< n; row++){
            for(int col = 0; col< m ; col++){
                // check if the current value is 1 and it is not visited
                if(vis[row][col] == 0 && vis[row][col] == '1'){
                    count++;
                    bfsNI(row,col,vis,grid);
                }
            }
        }
        
        return count;
    }

    private static void bfsNI(int row, int col, int[][] vis, char[][] grid) {
        vis[row][col] = 1;
        Queue<Pairs> q = new LinkedList<Pairs>();
        q.add(new Pairs(row, col));

        int n = grid.length;
        int m = grid[0].length;

        while(!q.isEmpty()){
            int ro = q.peek().first;
            int co = q.peek().second;
            q.remove();

            // Traverse in the neighbour and mark the if it is land
            for(int delrow = -1;delrow <= 1; delrow++){
                for(int delcol = -1;delcol <= 1; delcol++){
                    int nrow = ro + delrow;
                    int ncol = co  + delcol;

                    if(nrow >= 0 && nrow < n && ncol >= 0 && ncol< n && 
                    grid[nrow][ncol] == '1' && vis[nrow][ncol] == 0)
                    {
                        vis[nrow][ncol] = 1; 
                        q.add(new Pairs(nrow, ncol));
                    }            
                }
            }
        }
    }

    static class Pairs{
        //  To store row and col index
        int first;
        int second;
        Pairs(int row, int col){
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        
    }
}






