package boj.by_category.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제개요
 *  1. N+1 에 3명의 궁수를 배치할 수 있는데
 *  2. 턴마다 거리가 되는 적을 잡아서
 *  3. 최대 적을 잡아야한다.  
 * 아이디어
 *  1. 궁수를 배치하는 모든 경우의수를 구하자.
 *  2. 가까운거 먼저 잡아야한다. 
 *  3. 궁수가 잡을 수 있는 범위가 존재.
 *      3-2. 범위를 표시를 해서, 턴마다 앞쪽부터 최대한으로 잡을 수 있는 만큼 잡자.
 *      3-3. 잡으면 0으로 바꾸기. 
 * 문제풀이
 * 1. 맵의 크기, 궁수 범위를 받는다.  
 * 2. 맵을 받는다. (이때 row + 1 : 궁수 배치)
 * 3. 궁수를 배치할때마다, 
 *  4.  잡을 수 있는 적의 최대값을 구한다.
 * 5. 궁수를 배치하고 최대값을 구하는 함수를 구현한다. (in : 궁수 좌표 3개 , out : 최대값)
 *  5-1. 궁수 배치한 좌표를 받아서
 *  5-2. bfs 로 최소 거리의 적을 잡는 함수를 만든다.
 * 
 * 
 */
public class pblm17135 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static int[][] copyMap;
    static boolean[][] archerAttackArray;
    static boolean[][] visited;
    
    static int rowNum, colNum, distance;
    static int[] deltaRow = new int[]{0,0,-1};
    static int[] deltaCol = new int[]{1,-1,0};

    public static void copyMap() {
        copyMap = new int[rowNum+1][colNum];
        for (int row = 0; row < rowNum; row++) {
            copyMap[row] = map[row].clone();
        }
    }
    public static void main(String[] args) throws IOException {
        // 1. 맵의 크기, 궁수 범위를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        rowNum = Integer.parseInt(st.nextToken());
        colNum = Integer.parseInt(st.nextToken());
        distance = Integer.parseInt(st.nextToken());

        // 2. 맵을 받는다. (이때 row + 1 : 궁수 배치)
        map = new int[rowNum+1][colNum];
        visited = new boolean[rowNum+1][colNum];
        for (int row = 0; row < rowNum; row++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int col = 0; col < colNum; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        // 3. 궁수를 배치할때마다,
        int maxAnswer = 0;
        for (int archerIndex1 = 0; archerIndex1 < colNum-2; archerIndex1++) {
            for (int archerIndex2 = archerIndex1+1; archerIndex2 < colNum-1; archerIndex2++) {
                for (int archerIndex3 = archerIndex2+1; archerIndex3 < colNum; archerIndex3++) {
                    copyMap();
                    // 각 아쳐들은 본인이 위치한 자리에서 BFS 로 최소거리에 있는걸 잡아주면 됨 ( 그리디 )
                    int tmpMax = getMax(archerIndex1,archerIndex2,archerIndex3);
                    // System.out.println(tmpMax);
                    maxAnswer = Math.max(tmpMax,maxAnswer);
                }
            }
        }
        System.out.println(maxAnswer);
    }

    // 조합을 받아서, 최대점수를 뽑아내는 함수

    public static int getMax(int archerCol1, int archerCol2, int archerCol3){
        int maxPoint = 0;

        // 궁수가 위로 올라가면서
        for (int row = rowNum; row >= 0; row--) {
            // 한명씩 쏘세요
            for (int archerCol : new int[]{archerCol1,archerCol2,archerCol3}) {
                // 성공이면 point +1
                visited = new boolean[rowNum+1][colNum];
                if (shotShortEnemy(row, archerCol)) maxPoint++;
            }
        }
        return maxPoint;
    }
    // 궁수가 최소거리에 있는 적을 잡자. 
    public static boolean shotShortEnemy(int archerRow, int archerCol){
        // 궁수의 좌표를 받아서,
        // 궁수보다 위쪽칸만을 탐색을 하자.
        int dist = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        visited[archerRow][archerCol] = true;
        queue.offer(new int[]{archerRow,archerCol,dist});

        while (!queue.isEmpty()) {
            int[] curInfo = queue.poll();
            int curRow = curInfo[0];
            int curCol = curInfo[1];
            int curdist = curInfo[2];
            
            for (int deltaIndex = 0; deltaIndex < 3; deltaIndex++) {
                int newRow = curRow + deltaRow[deltaIndex];
                int newCol = curCol + deltaCol[deltaIndex];
                
                // 다음 탐색이 맵밖을 벗어나거나 방문 했다면,
                if (newRow < 0 || newCol <0 || newCol >= colNum) continue;
                if (visited[newRow][newCol]) continue;
                if (newRow >= archerRow) continue;
                // 다음 탐색이 거리가 초과된다면 멈춰
                if (curdist+1 > distance) continue;

                // 잡았다
                if (copyMap[newRow][newCol] == 1) {
                    copyMap[newRow][newCol] = 0; 
                    return true;
                }

                visited[newRow][newCol] = true;
                queue.offer(new int[]{newRow,newCol,curdist+1});
            }
        }
        // 잡을게 아무것도 없었다면 
        return false;
    }
}
