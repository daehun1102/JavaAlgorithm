package swea.by_category.twodimension;

import java.io.*;
import java.util.*;

//  1. 테스트케이스 번호와 이차원 배열을 받는다.
//  2. DFS 를 구현한다. (row, col)
//      2-1. 기저조건 : 사다리 끝에 도달해서 2이면 탐색종료 true 반환
//      2-2. 방문처리
//      2-3. delta 활용해서 탐색. 맵밖에 안나가고, 방문 안했고, 1인곳 탐색
//      타다가 좌우 먼저 보고 (delta 로 우선순위), 되는 곳 먼저 발견하면 그곳으로만 이동
//  3. 발견하면 끝내기
public class pblm1210 {
    static final int TEST_CASE = 10;
    static final int MAP_SIZE = 100;

    static int[][] ladderMap;
    static boolean[][] visited;
    static boolean found;

    static final int[] deltaRow = {0, 0, 1};
    static final int[] deltaCol = {-1, 1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int testCase = 1; testCase <= TEST_CASE; testCase++) {
            int testNum = Integer.parseInt(br.readLine().trim());
            
            ladderMap = new int[MAP_SIZE][MAP_SIZE];
            for (int row = 0; row < MAP_SIZE; row++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int col = 0; col < MAP_SIZE; col++) {
                    ladderMap[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            
            int answerCol = -1;
            found = false;
            for (int startCol = 0; startCol < MAP_SIZE; startCol++) {
                if (ladderMap[0][startCol] != 1) continue;
                visited = new boolean[MAP_SIZE][MAP_SIZE];
                dfs(0, startCol);
                //  3. 발견하면 끝내기
                if (found) {
                    answerCol = startCol;
                    break;
                }
            }

            sb.append('#').append(testNum).append(' ').append(answerCol).append('\n');
        }

        System.out.print(sb.toString());
    }

    //  2. DFS 를 구현한다. (row, col)
    public static void dfs(int row, int col) {
        //      2-1. 기저조건 : 사다리 끝에 도달해서 2이면 탐색종료 true 반환
        if (row == MAP_SIZE - 1) {
            if (ladderMap[row][col] == 2) found = true;
            return; 
        }

        //      2-2. 방문처리
        visited[row][col] = true; 

        //      2-3. delta 활용해서 탐색. 맵밖에 안나가고, 방문 안했고, 1인곳 탐색
        //      타다가 좌우 먼저 보고 (delta 로 우선순위), 되는 곳 먼저 발견하면 그곳으로만 이동
        for (int deltaIndex = 0; deltaIndex < 3; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];
            if (newRow < 0 ||  newRow >= MAP_SIZE || newCol < 0 || newCol >= MAP_SIZE) continue;
            if (ladderMap[newRow][newCol] == 0 || visited[newRow][newCol]) continue;
            
            dfs(newRow,newCol);
            return;
        }
        return;
    }
}
