package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 보안회사가 운영비용 손해를 보지않고 서비스를 운영하는법.
 * 문제이해를 잘못해서 bfs..
 * 
 * 아이디어
 * 1. 좌표마다 돌면서 BFS 로 뎁스 탐색해서 서비스 가능지역 내에 집을 세기.
 * 2. 세고나서 운영비용, 받을 수 있는 비용 비교해서 받을 수 있는 비용이 클때 true
 * 3. k 를 키울건데 범위는.. 맵왼쪽 상단에서 오른쪽하단까지의 길이 L
 * 4. 시간복잡도가 L*N크기*bfs 에 걸리는 시간
 *  4-1. 시간초과... -> bfs 에서 k depth 를 mapSize*2 -> mapSize 로 바꿨는데 됐..?
 * 
 * 문제풀이
 * 1. 테스트케이스 마다,
 * 2. 맵크기, 비용받기.
 * 3. 맵을 받자.
 * 4. 좌표를 순회하면서,
 *  4-1. 서비스 가능지역내 집 개수 구해서, (bfs) 
 *  4-2. 가능한 서비스영역인지 확인하고 가능하다면 집최대 개수 갱신. 
 * 
 */
public class pblm2117 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int oneHomeMoney, mapSize, map[][];
    static boolean visited[][];

    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            sb = new StringBuilder();
            mapSize = Integer.parseInt(st.nextToken());
            oneHomeMoney = Integer.parseInt(st.nextToken());
            map = new int[mapSize][mapSize];

            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            int maxHome = 0;
            // 4. 좌표를 순회하면서,
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    for (int k = 1; k <= mapSize+1; k++) {
                        maxHome = Math.max(maxHome,getMaxHouse(row,col,k));
                    }
                }
            }
            sb.append("#").append(testCase).append(" ").append(maxHome);
            System.out.println(sb.toString());
        }
    }

    
    public static int getMaxHouse(int row,int col, int k) {
        int depth = 1; 
        int serviceCost = k*k+(k-1)*(k-1);
        int homeCount = 0;
        visited = new boolean[mapSize][mapSize];
        Deque<int[]> queue = new ArrayDeque<>();
        
        if (map[row][col] == 1) homeCount++;
        visited[row][col] = true;
        queue.offer(new int[] {row, col});

        while (!queue.isEmpty()) { 
            if (depth == k) break;
            int tmpQueueSize = queue.size();
            for (int tmpIndex = 0; tmpIndex < tmpQueueSize; tmpIndex++) {
                // 현재 좌표에서
                int[] curCoord = queue.poll();
                int curRow = curCoord[0];
                int curCol = curCoord[1];

                // 4방향 탐색
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    int newRow = curRow + deltaRow[deltaIndex];
                    int newCol = curCol + deltaCol[deltaIndex];
                    if (newRow < 0 || newCol < 0 || newRow >= mapSize || newCol >= mapSize) continue;
                    if (visited[newRow][newCol]) continue;
                    if (map[newRow][newCol] == 1) homeCount++;
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow,newCol});
                }
            }
            depth++;
        }

        // 다끝났을때 이익계산하고
        // 서비스 가능하면 집세수 return
        int earnMoney = homeCount* oneHomeMoney;
        if (earnMoney >= serviceCost)  return homeCount;
        else return 0;      
    }
}
