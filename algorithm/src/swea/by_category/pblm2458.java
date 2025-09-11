package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 
 * 문제개요
 * 1. 단방향 그래프가 주어질때 본인의 키순서를 알 수 있는 학생의 수
 * 
 * 
 * 아이디어
 * 1. 본인의 키순서를 알기위해선 N-1 번 만큼의 키비교를 해봐야한다.
 * 2. 하지만 간선의 일부만 주어지기 때문에,
 * 3. 본인의 앞과 뒤에 몇명의 학생이 있는지 알아야한다.
 * 
 * 리팩토링
 * 1. 메모이제이션
 */
public class pblm2458 {
    static int node,edge,adj[][], radj[][];
    static int count;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= testCaseNum; tc++) {
            node = Integer.parseInt(br.readLine().trim());
            edge = Integer.parseInt(br.readLine().trim());

            adj = new int[node+1][node+1];
            radj = new int[node+1][node+1];

            for (int m = 0; m < edge; m++) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                adj[from][to] = 1;
                radj[to][from] = 1;
            }

            int ans = 0;
            for (int i = 1; i <= node; i++) {
                count = 0;
                dfs(i,adj, new boolean[node+1]);
                dfs(i,radj, new boolean[node+1]);
                if(count==node-1) ans++;
            }

            System.out.println("#" + tc + " " + ans);
        }
    }

    private static void dfs(int cur,int[][] adj, boolean[] visited ){
        visited[cur] = true;
        for (int i = 1; i <= node; i++) {
            if (adj[cur][i] ==1 && !visited[i]) {
                ++count;
                dfs(i, adj, visited);
            }
        }
    }
}
