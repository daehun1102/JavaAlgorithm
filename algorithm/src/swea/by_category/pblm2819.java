package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * 1. 시작점에서 시작해서 6번을 이동한다.
 * 2. 이동할때 한번 거쳤던 격자칸을 다시 거쳐도 된다.
 * 3. 원하는만큼 채웠다면 집합에 String 추가.
 *  
 */
public class pblm2819 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int mapSize = 4;
    static char[][] map;
    static char[] stringArray = new char[7]; 
    static Set<String> uniqueString;

    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        
        
        // 맵을 받는다.
        for (int teseCase = 1; teseCase <= testCaseNum; teseCase++) {
            map = new char[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = st.nextToken().charAt(0);
                }
            }

            uniqueString = new HashSet<>();

            // 좌표마다 dfs 탐색
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    dfs(row,col,0);
                }
            }

            sb = new StringBuilder();
            sb.append("#").append(teseCase).append(" ").append(uniqueString.size());
            System.out.println(sb);
        }

        
    }

    public static void dfs(int row, int col, int depth){
        // 기저조건 : 다 골랐으면 set 에 넣자
        if (depth == 7) {
            uniqueString.add(new String(stringArray));
            return;
        }

        // 아직 더 채워야 한다면 4방향 탐색
        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
            int newRow = row + deltaRow[deltaIndex];
            int newCol = col + deltaCol[deltaIndex];

            if (newRow < 0 || newCol < 0 || newRow >=mapSize || newCol >=mapSize) continue;
            
            // 다음거 넣어주고
            stringArray[depth] = map[newRow][newCol];
            dfs(newRow,newCol, depth+1);
        }
    }
}
