package swea.by_category.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 홀수 크기 맵이 주어지면 
 * 2. 마름모 모양 농작물 수확
 * 
 * 아이디어
 * 1. 중앙에서 시작하는 bfs
 * 2. 중앙과의 거리 (row, col 차이합) 가 (맵크기 // 2) 보다
 *    큰 곳은 안함.
 * 
 * 문제 풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 맵의 크기를 받는다.
 * 3. 맵을 받는다.
 * 4. dfs 를 중앙에서부터 시작한다. (맵크기 // 2)
 * 5. dfs 를 구현한다.
 *  방문 체크
 *  5-1. 4방향을 탐색하면서,
 *  5-1-1. 맵밖으로 안나가고,
 *  5-1-2. 방문안한곳이면,
 *  5-1-2-1 거리체크하고,
 *  5-1-3. 다시 탐색
 */
public class pblm2805 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static boolean[][] visited;
    static int mapSize;
    static int totalGain;

    static final int[] deltaRow = {0,0,-1,1};
    static final int[] deltaCol = {-1,1,0,0};

    public static void main(String[] args) throws IOException {
        // 1. 테스트케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        int testCaseNum = Integer.parseInt(st.nextToken());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 맵의 크기를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());

            // 3. 맵을 받는다.
            map = new int[mapSize][mapSize];
            visited = new boolean[mapSize][mapSize];

            for (int row = 0; row < mapSize; row++) {
                String rowLine = br.readLine().trim();
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = rowLine.charAt(col)-'0';
                }
            }
            totalGain = 0;
            // 4. dfs 를 중앙에서부터 시작한다. (맵크기 // 2)
            dfs (mapSize / 2, mapSize / 2 );

            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(totalGain);
            System.out.println(sb);
        }
    }

    static void dfs(int row, int col) {
         
        
        visited[row][col] = true;
        totalGain += map[row][col];

        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];

            if(newRow < 0 || newCol < 0 || newRow >= mapSize || newCol >= mapSize  ) continue;
            if(visited[newRow][newCol]) continue;
            if(Math.abs(mapSize/2 - newRow) + Math.abs(mapSize/2 - newCol) > mapSize/2) continue;
            dfs(newRow, newCol);
            
        }
    }
}
