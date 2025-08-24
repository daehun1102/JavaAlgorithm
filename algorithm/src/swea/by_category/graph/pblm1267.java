package swea.by_category.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.StringTokenizer;
import java.util.List;

/**
 * 문제개요
 * 1. 그래프가 주어지면,
 * 2. 작업순서가 되는것중에 1개를 출력하기
 * 
 * 아이디어
 * 1. 줄세우기랑 구조가 완전히 똑같음.
 * 2. 위상정렬, 진입차수로 진입차수, bfs 로 0인것 출력하기.
 * 
 * 문제풀이
 * 1. 정점의 개수, 간선의 개수가 주어지면,
 * 2. 간선을 받아서 인접리스트에 등록하고,
 *  2-1. to 가 들어올때마다 진입차수++ 
 * 3. bfs 를 구현한다. 
 *  3-1. 노드가 들어오면, 방문처리하고 queue 에 넣기
 *  3-2. 연결되어있는 노드 꺼내서,
 *      3-2-1. 진입차수 -- 해주고,
 *      3-2-2. 진입차수가 0이라면,
 *          3-2-2-1. 출력넣어주고
 *          3-2-2-2. 방문처리후 큐에 넣어주기
 * 4. 진입차수가 0인것부터 bfs
 * 5. 방문 하지 않은것 추가하기
 */
public class pblm1267 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static List<List<Integer>> adjList;
    static int[] indegree;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        testCaseNum = 10;
        br = new BufferedReader(new InputStreamReader(System.in));
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(' ');
            
            //1. 정점의 개수, 간선의 개수가 주어지면,
            int nodeNum = Integer.parseInt(st.nextToken());
            int edgeNum = Integer.parseInt(st.nextToken());
    
            visited = new boolean[nodeNum+1];
            indegree = new int[nodeNum+1];
            adjList = new ArrayList<>(nodeNum+1);
            
            for (int node = 0; node <= nodeNum; node++) {
                adjList.add(new ArrayList<>());
            }

            st = new StringTokenizer(br.readLine().trim());
            // 2. 간선을 받아서 인접리스트에 등록하고,
            for (int edge = 0; edge < edgeNum; edge++) {
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                adjList.get(from).add(to);
    
                // 2-1. to 가 들어올때마다 진입차수++ 
                indegree[to]++;   
            }
    
            // 4. 진입차수가 0인것부터 bfs
            for (int node = 1; node <= nodeNum; node++) {
                if(indegree[node] == 0){
                    bfs(node); 
                }
            }

            // 5. 나머지 방문하지 않은 노드를 추가한다.
            for (int node = 1; node <= nodeNum; node++) {
                if(!visited[node]) {
                    sb.append(node).append(' ');
                } 
            }

            System.out.println(sb);
            
        }
        
    }

    // 3. bfs 를 구현한다. 
    public static void bfs(int node){
        if (visited[node]) return;
        Deque<Integer> queue = new ArrayDeque<Integer>();
        visited[node] = true;    
        queue.offer(node);

        while(!queue.isEmpty()){
            int curNode = queue.poll();
            sb.append(curNode).append(' ');

            for (Integer nextNode : adjList.get(curNode)) {
                indegree[nextNode]--;
                if (visited[nextNode]) continue;
                if (indegree[nextNode] == 0) {
                    visited[nextNode] = true;
                    queue.offer(nextNode);
                }
            }
        }
    }
}
