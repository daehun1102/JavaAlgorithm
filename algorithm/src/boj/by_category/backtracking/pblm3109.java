package boj.by_category.backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제조건
 * 1. input : 1열과 마지막열이 가스관, 중간에 건물이 있는 맵
 * 2. output : 설치할 수 있는 파이프 최대 개수
 * 
 * 문제 풀이
 * 1. Row Col 값을 받는다.
 * 2. 원본 배열 생성한다.
 * 3. DFS 를 구현한다. (return : boolean )
 *  3-1 도착했다면 바로 탈출, 움직인 곳은 방문처리.
 *  3-2 3방향 (오른쪽 방향) 으로 탐색한다. ( 단 윗방향 우선으로 탐색하다가 정답을 만나면 다른곳 탐색을 멈춘다. cross 해서 가는 경우가 좋을 순 없다.) 
 * 4. 가스관의 처음 행부터 마지막까지 전부다 해보면서 true 일때 answer ++
 */
public class pblm3109 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static char[][] map;
    static boolean[][] visited;
    static int answer = 0;
    static int rowSize;
    static int colSize;

    static final int[] deltaRow = {-1,0,1};
    static final int[] deltaCol = {1, 1, 1};

    // 3. 3방향 (오른쪽 방향) 으로 탐색한다. ( 단 윗방향 우선으로 탐색하다가 정답을 만나면 다른곳 탐색을 멈춘다. cross 해서 가는 경우가 좋을 순 없다.)
    public static boolean dfs(int row, int col){
        // 어차피 그리디라서 방문처리해도 괜찮음.
        visited[row][col] = true;

        // 3-1 도착했다면 바로 탈출, 움직인 곳은 방문처리.
        if(col == colSize-1) return true;

        for (int deltaIndex = 0; deltaIndex < 3; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];
            if (newRow<0 || newCol<0 || newRow >=rowSize || newCol >=colSize) continue;
            if (map[newRow][newCol] == 'x' || visited[newRow][newCol]) continue;

            // 윗방향 우선이라 나머지 확인 안해도 괜찮음.
            if (dfs(newRow, newCol)) return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        // 1. Row Col 값을 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        rowSize = Integer.parseInt(st.nextToken());
        colSize = Integer.parseInt(st.nextToken());
        map = new char[rowSize][colSize];
        visited = new boolean[rowSize][colSize];

        // 2. 원본 배열 생성한다.
        for (int row = 0; row < rowSize; row++) {
            map[row] = br.readLine().toCharArray();
        }

        // 4. 가스관의 처음 행부터 마지막까지 전부다 해보면서 true 일때 answer ++
        for (int row = 0; row < rowSize; row++) {
            if (dfs(row,0)) answer++;
        }
        System.out.println(answer);
    }
}
