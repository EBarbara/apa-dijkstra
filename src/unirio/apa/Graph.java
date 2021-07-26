package unirio.apa;

import java.util.Arrays;

public class Graph {
    class Node {
        Integer destNode;
        Integer weight;
        Node next;

        public Node(Integer destNode, Integer weight) {
            this.destNode = destNode;
            this.weight = weight;
            next = null;
        }

        public Node(){
            this(null, null);
        }

        public String toString(){
            String data =  " > " + destNode + " (" + weight + ")";
            if (next != null){
                return data + next;
            }else{
                return data;
            }
        }
    }

    private final int numNodes;
    private final int numEdges;
    private final boolean ghostNode;
    private final Node[] nodeLists;

    private static boolean hasFalse(boolean[] array){
        for(boolean b : array) if(!b) return true;
        return false;
    }

    public Graph(int numNodes, int numEdges, boolean ghostNode){
        this.numNodes = numNodes;
        this.numEdges = numEdges;
        this.ghostNode = ghostNode;
        nodeLists = new Node[numNodes];
    }

    public Graph(int numNodes, int numEdges){
        this(numNodes, numEdges, false);
    }

    public void addAdjacency(int node, int destNode, int weight) {
        Node newNode = new Node(destNode, weight);
        if (nodeLists[node] == null){
            nodeLists[node] = newNode;
        }else{
            Node lastNode = nodeLists[node];
            while (lastNode.next != null){
                lastNode = lastNode.next;
            }
            lastNode.next = newNode;
        }
    }

    public void addNode(int node) {
        Node newNode = new Node();
        if (nodeLists[node] == null) {
            nodeLists[node] = newNode;
        }
    }

    public void printAdjacencyList(){
        for(int i = 0; i < numNodes; i++){
            if(ghostNode && i == 0)
                continue;
            Node node = nodeLists[i];
            System.out.println(Integer.toString(i) + node);
        }
    }

    public Integer[] minimalPathsBasic(){
        // Inicializações
        final int HIGH_VALUE = 99999;
        Integer[] paths = new Integer[numNodes];
        boolean[] explored = new boolean[numNodes];
        int starterNode = 0;
        if(ghostNode) {
            starterNode = 1;
            paths[0] = null;
            explored[0] = true;
        }
        for(int i = starterNode; i < numNodes; i++) {
            paths[i] = HIGH_VALUE;
            explored[i] = false;
        }

        // Iniciamos com o nó inicial
        paths[starterNode] = 0;
        explored[starterNode] = true;

        // Enquanto houver nós não explorados
        while(hasFalse(explored)){
            int minDist = HIGH_VALUE;
            int nextNode = -1;
            for(int i = starterNode; i < numNodes; i++){
                if(explored[i]){
                    Node node = nodeLists[i];
                    while(node != null){
                        int candidate = node.destNode;
                        if(!explored[candidate]){
                            int dist = paths[i] + node.weight;
                            if(dist < minDist){
                                minDist = dist;
                                nextNode = candidate;
                            }
                        }
                        node = node.next;
                    }
                }
            }
            if(minDist == HIGH_VALUE) break;
            // Se não chegou a nenhum nó novo, os outros são inacessíveis
            paths[nextNode] = minDist;
            explored[nextNode] = true;
        }
        return paths;
    }

    public void printMinimalPathsBasic() {
        Integer[] paths = minimalPathsBasic();
        for(int i = 0; i < numNodes; i++){
            if(ghostNode && i == 0)
                continue;
            System.out.println("d[" + i + "] = " + paths[i]);
        }
    }

    // TODO Desconsiderar nó fantasma
    public Integer[] minimalPathsHeap(int starterNode){
        return null;
    }
}