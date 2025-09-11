package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 1번컴퓨터가 웜바이러스가 걸렸을때 1번에 의해 웜바이러스 걸리는 모든 컴퓨터
 */
public class pblm2606 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int computerNum, edgeNum, answer;
    static boolean[] visited;
    static List<List<Integer>> adjList;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        // 입력을 받자
        computerNum = Integer.parseInt(br.readLine().trim());
        edgeNum = Integer.parseInt(br.readLine().trim());
        visited = new boolean[computerNum+1];
        // 인접리스트 만들어주기
        adjList = new ArrayList<>();
        for (int i = 0; i < computerNum+1; i++) {
            adjList.add(new ArrayList<>());
        }

        // 인접리스트에 하나씩 등록
        for (int edgeIndex = 0; edgeIndex < edgeNum; edgeIndex++) {
            st = new StringTokenizer(br.readLine().trim());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            adjList.get(from).add(to);
            adjList.get(to).add(from);
        }

        
        // 1번을 탐색하자
        answer = bfs(1);

        System.out.println(answer);
    }

    static int bfs(int start) {
        Deque<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        queue.offer(start);
        int count = 0;
        while(!queue.isEmpty()){
            int curNode = queue.poll();
            for (Integer newNode : adjList.get(curNode)) {
                if (visited[newNode]) continue;
                visited[newNode] = true;
                queue.offer(newNode);
                count++;
            }
        }

        return count;
    }
}
