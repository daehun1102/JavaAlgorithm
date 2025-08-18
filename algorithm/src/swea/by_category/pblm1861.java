package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 이동할 수 있는 최대한의 방개수를 구한다.
 * 2. 이동하려는 방에 적힌 숫자보다 정확히 1 더 커야한다.
 * 3. 완전탐색으로 4곳을 보면서 탐색한다.
 * 
 * 문제 풀이
 * 1. 테스트케이스를 받는다.
 * 2. 맵 크기를 받고, 맵도 받는다.
 * 3. dfs 를 구현한다.
 *  3. 4방향으로 탐색하면서,
 *  3-1. 만약에 자신보다 1큰곳이 있다면,
 *  3-2. 다시 탐색하고 +1
 *  3-2. 없을때 return (이태까지 온길)
 */
public class pblm1861 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int[][] map;
    static int testCaseNum;

    static int mapSize;
    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};

    public static int dfs(int row, int col, int tempRoom) {

        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];
            if (newRow <0 || newCol<0 || newRow >= mapSize || newCol >= mapSize) continue;
            if (map[row][col]+1 != map[newRow][newCol]) continue;
            // 자신 방숫자보다 1 큰 방이 있다면 다음 방을 탐색한다.
            dfs(newRow, newCol, tempRoom+1); 
        }

        return tempRoom;

    }
    public static void main(String[] args) throws IOException {

        // 1. 테스트케이스를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        
        testCaseNum = Integer.parseInt(st.nextToken());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            sb = new StringBuilder();
            // 2. 맵 크기를 받고, 맵도 받는다.
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            map = new int[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            
            int answerRoom = 0;
            int answer = 0;

            // 맵 탐색
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    int tempAns = dfs(row, col,1); 
                    if ( tempAns > answer) {
                        answerRoom = map[row][col];
                        answer = Math.min(answer,tempAns); 
                    }    
                }
            }
            sb.append("#").append(testCase).append(" ").append(answerRoom).append(" ").append(answer);
            System.out.println(sb);
        }
    }
}
