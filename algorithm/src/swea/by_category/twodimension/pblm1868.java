package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 맵이들어오면,
 * 2. 맵을 다 확인 할 수 있는 최소한의 클릭구하기
 * 
 * 아이디어
 * 1. 0인곳 우선으로 체킹하고 bfs
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받고,
 * 2. 맵크기 받아서,
 * 3. 맵 받고,
 * 4. 0인덱스 구하기
 * 5. bfs 구현
 *  5-1. 0 인덱스 받아서 주변 방문처리, 
 *      5-1-1. 0 인곳은 queue 에 넣기,
 *  5-2. queue 에 넣고 다시 탐색
 * 6. 좌표 돌면서 방문안한 0 만 bfs 탐색돌리기
 * 7. 7. 방문안한곳은 count++
 */
public class pblm1868 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;

    static char[][] boomMap;
    static boolean[][] visited;
    static int mapSize;
    static int[] deltaRow = {0,0,-1,-1,-1,1,1,1};
    static int[] deltaCol = {1,-1,0,1,-1,0,1,-1};
    public static void main(String[] args) throws  IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            mapSize = Integer.parseInt(br.readLine().trim());
            boomMap = new char[mapSize][mapSize];
            visited = new boolean[mapSize][mapSize];
            //3. 맵 받고,
            for (int row = 0; row < mapSize; row++) {
                boomMap[row] = br.readLine().toCharArray();
            }

            // 4. 0인덱스 구하기
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (boomMap[row][col] == '.') {
                        // 0이라면
                        if (checkingZero(row, col)) {
                        boomMap[row][col] = '0' ; 
                        }
                    }
                }
            }

            // 5. 지뢰는 그냥 방문처리
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (boomMap[row][col] == '*') {
                        visited[row][col] = true;
                    }
                }
            }

            int count = 0;
            // 6. 좌표 돌면서 방문안한 0 만 bfs 탐색돌리기
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (visited[row][col]) continue;
                    if (boomMap[row][col] == '0') {
                    bfs(row, col);
                    count++;
                    }     
                }
            }

            // 7. 방문안한곳은 ++
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (!visited[row][col]) count++; 
                }
            }
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(count);
            System.out.println(sb);
            
        }
    }

    // 0인지 확인하는 메서드
    public static boolean  checkingZero(int row, int col) {
        for (int deltaIndex = 0; deltaIndex < 8; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];
            if (newRow <0 || newCol<0 || newRow >= mapSize || newCol >= mapSize) continue;
            if (boomMap[newRow][newCol] == '*') return false;
        }
        return true;
    }

    // 5. bfs 구현
    public static void bfs( int row, int col) {
        Deque<int[]> queue = new ArrayDeque<>();
        visited[row][col] = true;
        queue.offer(new int[] {row,col});
        // 5-1. 0인 인덱스 받아서 주변 방문처리,
        while (!queue.isEmpty()) {
            int[] curIndex = queue.poll();
            for (int deltaIndex = 0; deltaIndex < 8; deltaIndex++) {
                int newRow = curIndex[0] + deltaRow[deltaIndex];
                int newCol = curIndex[1] + deltaCol[deltaIndex];

                if (newRow <0 || newCol<0 || newRow >= mapSize || newCol >= mapSize) continue;
                if (visited[newRow][newCol]) continue;
                // 5-1-1. 0 인곳은 queue 에 넣기,
                if (boomMap[newRow][newCol] == '0') {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow,newCol});
                }
                visited[newRow][newCol] = true;
            }
        }
    }

}
