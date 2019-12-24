import java.util.*;

class Graph<T> {

    ArrayList<T> isVisited = new ArrayList<>();
    boolean Check = false;
    int x = 4;

    // We use Hashmap to store the edges in the graph 
    private Map<T, List<T>> map = new HashMap<>();

    // This function adds a new vertex to the graph 
    public void addVertex(T s)
    {
        map.put(s, new LinkedList<T>());
    }

    // This function adds the edge 
    // between source to destination 
    public void addEdge(T source,
                        T destination,
                        boolean bidirectional)
    {

        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(destination);
        if (bidirectional == true) {
            map.get(destination).add(source);
        }
    }

    // This function gives the count of vertices 
    public void getVertexCount()
    {
        System.out.println("The graph has "
                + map.keySet().size()
                + " vertex");
    }

    // This function gives the count of edges 
    public void getEdgesCount(boolean bidirection)
    {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has "
                + count
                + " edges.");
    }

    // This function gives whether 
    // a vertex is present or not. 
    public boolean hasVertex(T s)
    {
        if (map.containsKey(s)) {
//            System.out.println("The graph contains "
//                    + s + " as a vertex.");
            return true;
        }
        else {
            System.out.println("No such actor.");
            return false;
        }
    }

    // This function gives whether an edge is present or not.
    public boolean hasEdge(T s, T d)
    {
        if(map.containsKey(s)) {
            if (map.get(s).contains(d)) {
                return true;
            }
        }
        return false;

    }

    List<T> getVertex(T s){
        return map.get(s);
    }

    // Prints the adjancency list of each vertex. 
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }

    // A function to perform a Depth-Limited search
    // from given source 'src'
    boolean DLS(T src, T target, int limit)
    {
        if (src.equals(target)) {
            System.out.print(src);
            Check = true;
            return true;
        }


        // If reached the maximum depth, stop recursing.
        if (limit <= 0)
            return false;

        // Recur for all the vertices adjacent to source vertex
        var temp =map.get(src);
        for (int i = 0; i < temp.size(); ++i) {
            if (DLS(temp.get(i), target, limit - 1)) {
                System.out.print("-->" + src);
                return true;
            }
        }

        return false;
    }

    // IDDFS to search if target is reachable from v.
// It uses recursive DFSUtil().
    boolean IDDFS(T src, T target, int max_depth)
    {
        // Repeatedly depth-limit search till the
        // maximum depth.
        for (int i = 0; i <= max_depth; i++)
            if (DLS(src, target, i))
                return true;

        return false;
    }

} 