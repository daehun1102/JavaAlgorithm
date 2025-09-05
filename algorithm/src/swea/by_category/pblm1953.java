package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제개요.
 * 1. 탈주범이 있을 수 있는 위치의 개수를 구해야한다.
 * 2. 구조물이 있고 구조물마다 갈수있는 방향이 정해져있다.
 * 
 * 아이디어
 * 1. bfs 로 queue 뎁스를 타임으로 설정해놓고 queue.size 만큼돌리자.
 *  1-1. 큐에서 꺼낼때마다 좌표확인해서 탐색가능한 방향 배열 생성
 */
public class pblm1953 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static boolean[][] visited; 
    static int height, width, startRow, startCol, timeDepth;

    // 상우하좌
    static int[] deltaRow = {-1,0,1,0};
    static int[] deltaCol = {0,1,0,-1};
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            height = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            startRow = Integer.parseInt(st.nextToken());
            startCol = Integer.parseInt(st.nextToken());
            timeDepth = Integer.parseInt(st.nextToken());

            // 맵을 받자.
            map = new int[height][width];
            visited = new boolean[height][width];
            for (int row = 0; row < height; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < width; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            // bfs 
            int answer = bfs(startRow, startCol);
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(answer);
            System.out.println(sb);
        }
    }
    public static int bfs(int sRow, int sCol){
        Deque<int[]> queue = new ArrayDeque<>();
        visited[sRow][sCol] = true;
        queue.offer(new int[]{sRow,sCol});
        int time = 1;
        int escapeArea = 1;
        while(!queue.isEmpty()){

            // System.out.println("현재 time: "+ time + " 현재 queueSize: "+ queue.size());
            // for (int row = 0; row < height; row++) {
            //     System.out.println(Arrays.toString(visited[row]));
            // }
            // System.out.println();

            if (time == timeDepth) return escapeArea;
            // queue 에서 꺼내서 뎁스만큼 반복문
            int timeQueueSize = queue.size();
            for (int queueIndex = 0; queueIndex < timeQueueSize; queueIndex++) {
                int[] curCoord = queue.poll();
                int curRow = curCoord[0];
                int curCol = curCoord[1];
                boolean[] tmpDirectionArray = getDirectionArray(map[curRow][curCol]);  
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    // 해당 방향으로 갈 수 없다면 멈춰
                    if (!tmpDirectionArray[deltaIndex]) continue;
                    int newRow = curRow + deltaRow[deltaIndex];
                    int newCol = curCol + deltaCol[deltaIndex];
                    // 맵밖 멈춰
                    if (newRow < 0 || newCol < 0 || newRow >= height || newCol >= width) continue;
                    // 0 다음 갈곳을 이미 방문했거나 0이라면 멈춰
                    if (visited[newRow][newCol] ||  map[newRow][newCol] == 0) continue;
                    // 만약에 연결이 안되는곳이라면 멈춰
                    if (!getDirectionArray(map[newRow][newCol])[(deltaIndex+2) % 4]) continue;
                    
                    escapeArea++;
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow,newCol});
                }
            }
            // visited 방문 찍어보기
            time++;
        }
        // time 은 많긴한데 더이상 갈곳이 없을때
        return escapeArea;
    }

    // 상우좌하
    // 현재파이프에서 갈 수 있는 곳을 return 하는 함수
    public static boolean[] getDirectionArray(int tmpCode) {
        if (tmpCode==1) return new boolean[] {true,true,true,true};
        else if (tmpCode==2) return new boolean[] {true,false,true,false};
        else if (tmpCode==3) return new boolean[] {false,true,false,true};
        else if (tmpCode==4) return new boolean[] {true,true,false,false};
        else if (tmpCode==5) return new boolean[] {false,true,true,false};
        else if (tmpCode==6) return new boolean[] {false,false,true,true};
        else if (tmpCode==7) return new boolean[] {true,false,false,true};
        // 0인건 들어올 수가 없다. 
        return null;     
    }
}
