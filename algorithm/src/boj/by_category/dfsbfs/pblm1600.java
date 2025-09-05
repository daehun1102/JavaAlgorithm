package boj.by_category.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 
 * 아이디어
 * 
 * dfs 는 최적을 고려하지 못하는 경우가 생긴다. 최단거리는 반드시 bfs...
 * 
 * 문제풀이
 * 1. bfs 로 풀자. 
 *  1-1. 기저조건1 : 도착지에 도착했을때
 *  1-2. 지저조건2 : 지금 도착한 dist 값이 도착지 dist 보다 클때.
 *  1-3. 매번 4or 방향 탐색을 하면서,
 *      1-3-1. 맵밖을 나갔다면 continue
 *      1-3-2. 다음 dist 가 현재dist 보다 크거나 같다면 continue
 *      1-3-3. 위조건을 다통과 했으면 다음 탐색 (depth +1) 조건에 따라 말카운트
 */
public class pblm1600 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    
    static int[][] map;
    static boolean [][][] visited; // 거리를 기록하는데 초반에는 0으로 초기화
    static int horseCount, height, width;
    static int[] deltaRow = {1,1,2,2,-1,-1,-2,-2,0,0,1,-1};
    static int[] deltaCol = {2,-2,1,-1,2,-2,1,-1,1,-1,0,0};
    static int endRow,endCol;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        horseCount = Integer.parseInt(br.readLine().trim());
        st = new StringTokenizer(br.readLine().trim());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());
        
        // 맵을 받자.
        map = new int[height][width];
        visited = new boolean[height][width][horseCount+1];
        for (int row = 0; row < height; row++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int col = 0; col < width; col++) {
                int tmpInput = Integer.parseInt(st.nextToken());
                map[row][col] = tmpInput;
            }
        }
        endRow = height-1;
        endCol = width-1;

        Deque<int[]> queue = new ArrayDeque<>();
        visited[0][0][horseCount] = true;
        queue.add(new int[]{0,0,0,0});
        // 시작지점부터 방문처리 하면서 목적지 도착하면 기록
        int answer = Integer.MAX_VALUE;
        while(!queue.isEmpty()) {
            int[] curCoord = queue.poll();
            int curRow = curCoord[0];
            int curCol = curCoord[1];
            int curDepth = curCoord[2];
            int curHorseCount = curCoord[3];
            // System.out.println("curRow : " + curRow + " curCol : " + curCol + " curDepth : " + curDepth + " curHorseCount : " + curHorseCount );
            // 도착하면 최솟값 갱신
            if (curRow == endRow && curCol == endCol) {
                answer = Math.min(answer, curDepth);
                continue;
            }

            int startIndex = (curHorseCount == horseCount) ? 8: 0;
            for (int deltaIndex = startIndex; deltaIndex < 12; deltaIndex++) {
                int newRow = curRow + deltaRow[deltaIndex];
                int newCol = curCol + deltaCol[deltaIndex];

                if (newRow <0 || newCol <0 || newRow >= height || newCol >= width) continue;
                if (map[newRow][newCol] == 1 ) continue;

                // 말처럼 뛰는 경우
                if (deltaIndex < 8) {
                    if (visited[newRow][newCol][curHorseCount+1]) continue;
                    visited[newRow][newCol][curHorseCount+1] = true;
                    queue.offer(new int[]{newRow, newCol,curDepth+1,curHorseCount+1});
                
                } else {
                    if (visited[newRow][newCol][curHorseCount]) continue;
                    visited[newRow][newCol][curHorseCount] = true;
                    queue.offer(new int[]{newRow, newCol,curDepth+1,curHorseCount});
                }
            }
        }
        if (answer == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(answer);
    }

}
