package swea.by_category.prim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 문제풀이
 * 1. 테스트케이스를 받는다.
 * 2. 섬개수를 받는다. 
 * 3. 프림으로 최소 비용 구한다.
 * 
 */
public class pblm1251 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static List<List<Edge>> adjList;
    static int[][] nodeIndex;
    static boolean[] visited; 

    static double totalWeight;

    static class Edge implements Comparable<Edge> {
        int from,to;
        double weight;

        public Edge(int from, int to, double  weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
        }
    } 

    private static double prim() {
        
        int startNode = 1;
        visited[startNode] = true;
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        pq.addAll(adjList.get(startNode));

        totalWeight = 0;
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();

            if (visited[edge.to]) continue;

            visited[edge.to] = true;
            totalWeight += edge.weight;

            for (Edge nextEdge : adjList.get(edge.to)) {
                if (!visited[nextEdge.to]) {
                    pq.add(nextEdge);
                }
            }
        }

        return totalWeight;
    }
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            int nodeNum = Integer.parseInt(br.readLine().trim());
            
            nodeIndex = new int[2][nodeNum+1];
            visited = new boolean[nodeNum+1];

            // x 좌표 기록 1~
            st = new StringTokenizer(br.readLine().trim());
            for (int indexX = 1; indexX <= nodeNum; indexX++) {
                nodeIndex[0][indexX] = Integer.parseInt(st.nextToken());
            }
    
            // y 좌표 기록 1~
            st = new StringTokenizer(br.readLine().trim());
            for (int indexY = 1; indexY <= nodeNum; indexY++) {
                nodeIndex[1][indexY] = Integer.parseInt(st.nextToken());
            }
    
            double E = Double.parseDouble(br.readLine().trim());
            adjList = new ArrayList<>();
            for (int i = 0; i <= nodeNum; i++) {
                adjList.add(new ArrayList<>());
            }
    
            // 엣지 등록
            for (int index1 = 1; index1 <= nodeNum-1; index1++) {
                for (int index2 = index1+1; index2 <= nodeNum; index2++) {
                    double curDistance = Math.pow(Math.abs(nodeIndex[0][index1]-nodeIndex[0][index2]),2)
                                        +Math.pow(Math.abs(nodeIndex[1][index1]-nodeIndex[1][index2]),2);
                    double curWeight = curDistance*E;
                    
                    Edge curEdge1 = new Edge(index1, index2, curWeight);
                    Edge curEdge2 = new Edge(index2, index1, curWeight);
                    adjList.get(index1).add(curEdge1);
                    adjList.get(index2).add(curEdge2);
    
                }
            }

            long answer = (long) Math.round(prim());
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(answer);
            System.out.println(sb);
        }    


    }
}
