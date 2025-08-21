package swea.by_category.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 맛있는 정도가 다른 정사각형모양 치즈가 들어온다.
 * 2. 100일동안 X 번째 날에 X 인 칸을 먹는다.
 * 3. 날마다 덩어리(그룹) 개수 카운팅해서 가장 많은 덩어리 개수 구하기
 * 
 * 아이디어
 * 1. 테스트케이스마다, 맵을 받고, visited 배열 초기화
 * 2. 100일 동안
 *  2-1. 해당하는 날짜의 값을 방문처리
 * 3. bfs 로 그룹 카운팅
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 치즈맵의 한변 길이를 받는다.
 * 3. 치즈맵을 받고
 * 4. 100일 동안,
 *  4-1. 해당 날짜 날짜값 방문처리 (메서드 in : 날짜, out : x)
 * 5. 맵 좌표를 순회하면서, 
 *  5-1. bfs 로 새로운 그룹인지 체크( 메서드 in : 치즈맵 좌표, out : boolean)
 */

public class pblm7733 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    
    static int mapSize;
    static int[][] cheezeMap;
    static boolean[][] visited;
    static int testCaseNum;

    static final int[] deltaRow = {0,0,1,-1};
    static final int[] deltaCol = {1,-1,0,0};
    static int maxGroupCount;

    public static void main(String[] args) throws IOException {
        // 1. 테스트케이스 개수를 받는다. 
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            
            // 2. 치즈맵의 한변 길이를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            cheezeMap = new int[mapSize][mapSize];
            visited = new boolean[mapSize][mapSize];
            
            // 3. 치즈맵을 받고
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    cheezeMap[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            maxGroupCount = 1; // 요정이 먹기전까진 1덩이..
            // 4. 100일 동안,
            for (int day = 1; day <= 100; day++) {
                // 4-1. 해당 날짜 날짜값 방문처리 (메서드 in : 날짜, out : x)
                deleteCheeze(day);
                
                for (int row = 0; row < mapSize; row++) {
                    for (int col = 0; col < mapSize; col++) {
                        if (cheezeMap[row][col] == -1) {
                            visited[row][col] = true;
                        } else {
                            visited[row][col] = false;
                        }
                    }
                }

                // 5. 맵 좌표를 순회하면서,
                int groupCount = 0;
                for (int row = 0; row < mapSize; row++) {
                    for (int col = 0; col < mapSize; col++) {
                        //5-1. bfs 로 새로운 그룹인지 체크( 메서드 in : 치즈맵 좌표, out : boolean)
                        if (bfs(row,col)) groupCount++;
                    }
                }
                
                // 최대 그룹 개수인지 카운팅
                maxGroupCount = Math.max(maxGroupCount, groupCount);
            }
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(maxGroupCount);
            System.out.println(sb);
        }
    }

    public static void deleteCheeze(int day){
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                if (cheezeMap[row][col] == day) {
                    cheezeMap[row][col] = -1;
                }
            }
        }
    }

    // 새로운 그룹이 될수있는 좌표인지 체크
    public static boolean bfs(int row, int col){
        int newRow = 0;
        int newCol = 0;
        if (visited[row][col]) return false;
        Deque<int[]> qDeque = new ArrayDeque<>();
        visited[row][col] = true;
        qDeque.offer(new int[]{row,col});
        
        while (!qDeque.isEmpty()) {
            int[] curIndex = qDeque.poll();
            for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                newRow = curIndex[0] + deltaRow[deltaIndex];
                newCol = curIndex[1] + deltaCol[deltaIndex];
                
                if (newRow<0 || newCol<0 || newRow >=mapSize || newCol>= mapSize) continue;
                if (visited[newRow][newCol]) continue;
                
                visited[newRow][newCol] = true;
                qDeque.offer(new int[]{newRow,newCol});
            }
        }
        return true;
    }
}
