package boj.by_category.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 1. dfs 로 벽세우기
 * 2. 세개 다세우면 bfs 로 바이러스 퍼뜨리고 세기
 */
public class pblm14502 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static int[][] copyMap;
    static int height,width;
    static int minResult;

    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        map = new int[height][width];
        copyMap = new int[height][width];
        minResult = 0;
        // 맵을 받자
        for (int row = 0; row < height; row++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int col = 0; col < width; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        
        
        // 좌표를 선택하자
        dfs(0);

        System.out.println(minResult);
    }
    public static void dfs(int depth) {
        if (depth == 3){
            // 3개를 다 선택했다면 바이러스 퍼뜨려서 갱신
            int tmpResult = bfs();
            minResult = Math.min(tmpResult, minResult);
        }

        // 3개 이전이라면 맵돌면서 0 인곳에 설치
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (map[row][col] == 0) {
                    // 설치하고 다음거 탐색
                    map[row][col] = 1;
                    dfs(depth+1);

                    // 탐색 다끝났으면 되돌리기
                    map[row][col] = 0;
                }
            }
        }
    }

    public static int bfs(){
        int safePoint = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        // dfs 사용할 맵 카피
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                copyMap[i][j] = map[i][j];
            }
        }
        // 2인 좌표 전부 큐에 넣기
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (copyMap[row][col] == 2) {
                    queue.offer(new int[]{row,col});
                }
            }
        }

        // queue 에 있는거 퍼뜨리기
        while(!queue.isEmpty()){
            int[] curCoord = queue.poll();
            int curRow = curCoord[0];
            int curCol = curCoord[1];
            for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                int newRow = curRow + deltaRow[deltaIndex];
                int newCol = curCol + deltaCol[deltaIndex];
                if (newRow < 0 || newCol < 0 || newRow >= height || newCol >= width) continue;
                if (copyMap[newRow][newCol] == 0) {
                    copyMap[newRow][newCol] = 2;
                    queue.offer(new int[]{newRow,newCol});
                }
            }
        }

        // 안전지대 세기
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (copyMap[row][col] == 0) safePoint++;
            }
        }

        return safePoint;
    }
}
