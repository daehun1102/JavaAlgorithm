package boj.by_category.dfsbfs;

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
 * 1. N 명의 학생이 주어지고, 키 비교 간선이 주어지면,
 * 2. 조건에 맞게 출력 
 * 
 * 아이디어
 * 1. 트리는 x , 한 노드 위에 두 부모가 존재 -> 그래프
 * 2. 위상정렬, 진입차수를 고려하자   
 *  2-1. 진입차수가 곧 뎁스이다.
 * 3. bfs 로 진입차수 0인거부터 탐색하기. 
 * 4. 방문배열 만들어서 나머지 노드 마지막에 출력 
 * 
 * 문제풀이
 * 1. 노드수, 간선 수를 받는다.
 * 2. 간선이 들어올때마다,
 *  2-1. from to 로 인접리스트에 기록,
 *  2-2. to 는 진입차수 ++
 * 3. bfs 를 구현한다.
 *  3-1. 노드가 주어지면 방문 처리하고
 *  3-2. 연결되어있는 노드 방문한다.
 * 4. 진입차수 0 을 돌면서 bfs 를 돌린다.
 * 5. 나머지 방문하지 않은 노드를 추가한다.
 */
public class pblm2252 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static List<List<Integer>> adjList;
    static int[] indegree;
    static boolean[] visited;
    public static void main(String[] args) throws IOException{
      // 1. 노드수, 간선 수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        sb = new StringBuilder();
        int nodeNum = Integer.parseInt(st.nextToken());
        int edgeNum = Integer.parseInt(st.nextToken());

        adjList = new ArrayList<>(nodeNum+1);
        for (int nodeIndex = 0; nodeIndex <= nodeNum; nodeIndex++) {
          adjList.add(new ArrayList<>());
        }
        
        indegree = new int[nodeNum+1];
        
        // 2-1. from to 로 인접리스트에 기록,
        for (int edge = 0; edge < edgeNum; edge++) {
          st = new StringTokenizer(br.readLine().trim());
          int from = Integer.parseInt(st.nextToken());
          int to = Integer.parseInt(st.nextToken());
          adjList.get(from).add(to);
          // 2-2. to 는 진입차수 ++
          indegree[to]++;
        }

        // 4. 진입차수 0 을 돌면서 bfs 를 돌린다.
        visited = new boolean[nodeNum+1];
        for (int node = 1; node <= nodeNum; node++) {
          if(indegree[node] == 0) {
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
    // 3. bfs 를 구현한다.
    public static void bfs(int node) {
        // 3-1. 노드가  주어지면 방문 처리하고 
        if (visited[node]) return;

        Deque<Integer> queue = new ArrayDeque<Integer>();
        visited[node] = true;
        queue.offer(node);

        while (!queue.isEmpty()) {
          int curNode = queue.poll();
          sb.append(curNode).append(' ');
          
          // 3-2. 연결되어있는 노드 방문한다.
          for (int nextNode : adjList.get(curNode)) {
            indegree[nextNode]--; // 진입차수 빼주면서
            if (visited[nextNode]) continue;
            // 진입차수 0일때 방문
            if (indegree[nextNode] == 0) {
              visited[nextNode] = true;
              queue.offer(nextNode);
            }
          }
        }
    }
}
