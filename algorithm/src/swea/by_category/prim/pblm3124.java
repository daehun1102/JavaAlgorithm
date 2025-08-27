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
 * 1. 엣지를 만든다.
 * 2. 인접리스트를 만든다.
 * 3. 테스트케이스를 받고,
 * 4. Edge 를 만들어서 인접리스트에 등록한다.
 * 5. 시작노드를 하나 잡고 
 *  5-1. 연결된 간선을 모두 우선순위큐에 넣는다.
 *  5-2. 방문한 점점이라면 무시하고
 *  5-3. 방문안한 정점이라면 방문하고, 가중치를 넣는다.
 *  5-4. 새로 방문한 정점에서 뻗어나가는 간선을 삽입한다.  
 */
public class pblm3124 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    
    static List<List<Edge>> adjList;
    static boolean[] visited;
    static long totalWeight;
    public static class Edge implements Comparable<Edge> {
        int from,to,weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        // 오름차순 정렬
        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    public static void prim(int nodeNum) {
        
        // 방문 배열을 만든다.
        visited = new boolean[nodeNum+1];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        
        // 5. 시작노드를 하나 잡고 
        int startNode = 1;
        visited[startNode] = true;

        // 5-1. 연결된 간선을 모두 우선순위큐에 넣는다.
        pq.addAll(adjList.get(startNode));
        
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            
            // 5-2. 방문한 점점이라면 무시하고
            if (visited[edge.to]) continue;

            // 5-3. 방문안한 정점이라면 방문하고, 가중치를 넣는다.
            visited[edge.to] = true;
            totalWeight += edge.weight;

            // 5-4. 새로 방문한 정점에서 뻗어나가는 간선을 삽입한다.  
            for (Edge next : adjList.get(edge.to)) {
                if (!visited[next.to]) {
                    pq.add(next);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine());
        
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine());
            int nodeNum = Integer.parseInt(st.nextToken());
            int edgeNum = Integer.parseInt(st.nextToken());
            adjList = new ArrayList<>();

            for (int i = 0; i <= nodeNum; i++) {
                adjList.add(new ArrayList<>());
            }

            // 인접리스트에 등록
            for (int edge = 0; edge < edgeNum; edge++) {
                st = new StringTokenizer(br.readLine());
                int curFrom = Integer.parseInt(st.nextToken());
                int curTo = Integer.parseInt(st.nextToken());
                int curWeight = Integer.parseInt(st.nextToken()); 
                Edge curEdge1 = new Edge(curFrom, curTo, curWeight);
                Edge curEdge2 = new Edge(curTo, curFrom, curWeight);
                adjList.get(curFrom).add(curEdge1);
                adjList.get(curTo).add(curEdge2);
            }
            totalWeight = 0;

            prim(nodeNum);
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(totalWeight);
            System.out.println(sb);
        }
    }
}


