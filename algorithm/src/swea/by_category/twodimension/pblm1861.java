package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 테스트 케이스개수를 받는다.
 * 2. 맵의 크기를 받고, 맵을 받는다.
 * 3. 맵을 순회하면서 탐색한다.
 * 4. 4방향으로 탐색을 하면서 방을 탐색한다.
 *  4-1. 맵안쪽에 있어나,
 *  4-2. 4방향 중 1큰곳이 있다면
 *  4-3. 다시탐색한다.
 * 5. 탐색을 하면서 아무곳도 갈곳이 없다면 종료
 */
public class pblm1861 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static int mapSize;
    static final int[] deltaRow = {0,0,1,-1};
    static final int[] deltaCol = {1,-1,0,0};
    public static int dfs(int row, int col, int tempRoomCount){
        
        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];
            // 4-1. 맵안쪽에 있어나,
            if (newRow < 0 || newCol < 0 ||  newCol >=mapSize || newRow >=mapSize ) continue;
            if (map[newRow][newCol] == map[row][col]+1) { // 4-2. 4방향 중 1큰곳이 있다면
                return dfs(newRow, newCol, tempRoomCount+1); // 4-3. 다시탐색한다.
            }
        }
        // 네곳 모두 막혀있는 경우 현재 길이 반환
        return tempRoomCount;
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());

        // 1. 테스트 케이스개수를 받는다.
        int testcaseNum = Integer.parseInt(st.nextToken());
        for (int testcase = 1; testcase <= testcaseNum; testcase++) {
            //  * 2. 맵의 크기를 받고, 맵을 받는다.
            sb = new StringBuilder();
            int bestRoom = Integer.MAX_VALUE;
            int bestRoomCount = 0;
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            map = new int[mapSize][mapSize];

            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            // 3. 맵을 순회하면서 탐색한다.
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    int tempRoomCount = dfs(row,col,1);
                    if (tempRoomCount == bestRoomCount) {
                        bestRoomCount = tempRoomCount;
                        bestRoom = ( bestRoom > map[row][col] ) ? map[row][col]: bestRoom;
                    } else if (tempRoomCount > bestRoomCount) {
                        bestRoomCount = tempRoomCount;
                        bestRoom = map[row][col];
                    }
                }
            }
            sb.append("#").append(testcase).append(" ").append(bestRoom).append(" ").append(bestRoomCount);
            System.out.println(sb);
        }
    }
}
