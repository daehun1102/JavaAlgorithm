package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 카페맵 주어지면,
 * 2. 최대로 많이 방문 할 수 있는 카페동선 찾아서, 카페 최대 방문 횟수 찾기.
 *  2-1. 동선은 무조건 대각선 사각형
 *  2-2. 같은 번호 또들리면 안된다.
 * 
 * 아이디어
 * 1. 맵크기도 작으니 완전탐색하자
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 맵크기를 받는다.
 * 3. 맵을 받는다.
 * 4. 카페 탐색 함수를 구현 (in : 시작좌표, 방향, 직진한 개수//  out : x)
 *  4-1. 다음좌표가 맵밖이라면 return
 *  4-2. 다음좌표가 시작점이라면 최대값 갱신 후 return
 *  4-3. 방문했던곳이거나 디저트종류 겹치면 return 어차피 사각형 안됨.
 * 5. 맵에서 카페 탐색 함수 돌리기
 *  5-1. 모든 경우의수 돌리면서, 최대값 구하기
 */
public class pblm2105 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static int[][] cafeMap;
    static boolean[][] visited;
    static boolean[] cafeVisited;
    static int mapSize;

    static int answer;
    static final int[] deltaRow = {1,1,-1,-1};
    static final int[] deltaCol = {1,-1,-1,1};

    static int startRow;
    static int startCol;

    public static void main(String[] args) throws IOException {
        
        // 1. 테스트케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 맵크기를 받는다.
            mapSize = Integer.parseInt(br.readLine().trim());
            cafeMap = new int[mapSize][mapSize];
            visited = new boolean[mapSize][mapSize];
            cafeVisited = new boolean[101];

            // 3. 맵을 받는다.
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    cafeMap[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            answer = -1;

            // 5. 맵에서 카페 탐색 함수 돌리기
            for (int row = 0; row < mapSize-2; row++) {
                for (int col = 1; col < mapSize-1; col++) {
                    startRow = row;
                    startCol = col;

                    visited[row][col] = true;
                    cafeVisited[cafeMap[row][col]] = true;
                    
                    dfs(row,col,0,0);
                    
                    visited[row][col] = false;
                    cafeVisited[cafeMap[row][col]] = false;

                }
            }
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(' ').append(answer);
            System.out.println(sb);
        }
    }
    // 4. 카페 탐색 함수를 구현 (in : 시작좌표, 방향, 직진한 개수//  out : x)
    static void dfs(int row,int col, int dir, int step) {
        if(dir>3) return;
        int newRow = row + deltaRow[dir];
        int newCol = col + deltaCol[dir];

        // 다음좌표가 맵밖이라면 return
        if (newRow < 0 || newRow >= mapSize || newCol < 0 || newCol >= mapSize ) return;

        // 다음 좌표가 시작점이라면 return
        if (newRow == startRow && newCol == startCol) {
            if (dir == 3 ) {
                int count = 0; 
                for (boolean isvisit : cafeVisited) {
                    if (isvisit) count++;
                }
                answer = Math.max(answer, count);
            }
            return;
        }

        int dessert = cafeMap[newRow][newCol];
        
        // 방문했던곳이거나 디저트종류 겹치면 return
        if (visited[newRow][newCol] || cafeVisited[dessert]) return;

        visited[newRow][newCol] = true;
        cafeVisited[dessert] = true;
        
        // 직진하거나 회전하자
        dfs(newRow, newCol, dir, step + 1);
        dfs(newRow, newCol, dir+1, 0);
        
        visited[newRow][newCol] = false;
        cafeVisited[dessert] = false;

    }
}
