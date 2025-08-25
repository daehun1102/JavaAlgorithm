package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 노드와 엣지가 주어지면,
 * 2. 뎁스 4이상이 되는지 확인해서 되면 1 안되면 0을 출력하자.
 * 
 * 아이디어
 * 1. 인접리스트 양방향 그래프를 구현한다.
 * 2. dfs 를 구현해서 depth 체크를 한다.
 * 
 * 문제풀이
 * 1. 노드개수와 엣지개수를 받아서,
 * 2. 양방향 인접리스트를 만든다.
 * 3. 엣지를 받을때마다,
 *  3-1. 양방향으로 연결 노드를 추가한다. 
 * 4. dfs 를 구현한다. (node, depth)
 *  4-1. node 를 받아서 인접한것 중
 *  4-2. 방문을 안했다면,
 *  4-3. 다시 탐색
 * 
 */
public class pblm13023 {
    static  BufferedReader br;
    static  StringTokenizer st;
    static  StringBuilder sb;

    static  List<List<Integer>> adjList;
    static  boolean[] visited;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        
        // 1. 노드개수와 엣지개수를 받아서,
        int nodeNum = Integer.parseInt(st.nextToken());
        int edgeNum = Integer.parseInt(st.nextToken());

        // 2. 양방향 인접리스트를 만든다.
        adjList = new ArrayList<>(nodeNum + 1);
        visited = new boolean[nodeNum+1];

        for (int index = 0; index <= nodeNum; index++) {
            adjList.add(new ArrayList<Integer>());
        }

        // 3. 엣지를 받을때마다,
        for (int edge = 0; edge < edgeNum; edge++) {
            //3-1. 양방향으로 연결 노드를 추가한다.
            st = new StringTokenizer(br.readLine().trim());
            
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            adjList.get(from).add(to);
            adjList.get(to).add(from);

        }

        // 5. 노드 순회하면서 dfs 되는지 확인을 하자.
        boolean flag = false;
        for (int node = 0; node < nodeNum; node++) {
            visited[node] = true;
            if (dfs(node, 0)) {
                flag = true;
                System.out.println(1); 
                break;
            }    
            visited[node] = false;
        }

        if (!flag) {
            System.out.println(0);
        }
    }
    // 4. dfs 를 구현한다. (node, depth)
    public static boolean  dfs(int node, int depth) { 
        
        if (depth == 4) return  true;
        
        for (int nextNode : adjList.get(node)) {
            if (visited[nextNode]) continue;
            visited[nextNode] = true;
            if (dfs(nextNode, depth+1)) {
                return  true;
            };
            visited[nextNode] = false;
        }

        // depth 4 되기 이전에 탐색할것 없다면 false return
        return false;
    }
}
