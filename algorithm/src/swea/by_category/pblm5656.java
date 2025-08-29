package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 이차원 배열로 풀자
 * 
 * 1. 구슬 놓는 배열 만들기.
 *  1-1. 크기를 받고 배열 생성하기.
 * 2. 터지는 함수 
 *  2-1. 터지면서 bfs 십자가 모양으로 탐색 좌표중에 1이상인값이 있다면 배열에 넣기 
 *  2-2. 다시 터뜨리기.
 * 3. 맵 정리
 *  3-1. 아래방향으로 정렬하기
 * 
 * 
 * 
 */
public class pblm5656 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static int[][] copyMap;
    static boolean[][] visited; 
    static int stoneNum;
    static int width, height;

    static int answer;
    static int[] perm;
    static List<int[]> boomList;

    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            stoneNum = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            height = Integer.parseInt(st.nextToken());

            map = new int[height][width];
            visited = new boolean[height][width];
            perm = new int[stoneNum];
            for (int row = 0; row < height; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < width; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            boomList = new ArrayList<>();
            answer = Integer.MAX_VALUE;
            // 구슬 중복순열 만들기
            getDPermutation(0); 
            
            System.out.println("#"+ testCase + " " + answer);
        }
    }

    public static void  getDPermutation(int depth){
        // 구슬 다 골랐으면,
        if (depth == stoneNum) {
            // 게임 ㄱㄱ
            int tmpResult = playGame();
            answer = Math.min(tmpResult, answer);
            return;
        }

        for (int curNum = 0; curNum < width; curNum++) {
            perm[depth] = curNum;
            getDPermutation(depth + 1);
        }
    }

    public static int playGame() {
        int count = 0;
        // 공을 떨어뜨려서,  
        copyMap = new int[height][width];
        for (int row = 0; row < height; row++) {
            copyMap[row] = map[row].clone();
        }

        for (int widthIndex : perm) {
            visited = new boolean[height][width];
            throwing(widthIndex); // 중복순열로 만든 colIndex 에 구슬 던지고
            clean(); // 맵 정리하기
        }

        // 한번 게임 끝나면 점수카운팅
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (copyMap[row][col] != 0) count++;
            }
        }

        return count;
    }

    public static void throwing(int throwingCol) {
        for (int row = 0; row < height; row++) {
            // row 돌다가 map 0 아닌거 만나면,
            if (copyMap[row][throwingCol]!=0) {
                boomList.add(new int[]{row, throwingCol});
                while ( !boomList.isEmpty()){
                    int[] cur = boomList.remove(0);
                    // 현재좌표값 받아서 터뜨리자
                    int curRange = copyMap[cur[0]][cur[1]];
                    visited[cur[0]][cur[1]] = true;
                    // 4방향으로 탐색하면서 색칠
                    for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                        for (int range = 1; range < curRange; range++) {
                            int newRow = cur[0] + deltaRow[deltaIndex]*range;
                            int newCol = cur[1] + deltaCol[deltaIndex]*range;
                
                            if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width ) continue;
                            if (visited[newRow][newCol]) continue;
                            visited[newRow][newCol] = true;
                
                            // 1이상이면 다시 탐색 해야함.
                            if (copyMap[newRow][newCol] > 1 ){
                                boomList.add(new int[]{newRow,newCol});
                            }
                        }
                    }  
                }
                break; // 만났으면 1번만
            }
        }
    }

    public static void clean () {
        // 방문한거 맵 전부 0으로 바꾸고,
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (visited[row][col]) copyMap[row][col] = 0;
            }
        }
        // 열을 탐색하면서 뒤에서부터 채워주기
        for (int col = 0; col < width; col++) {
            int writeRow = height -1;
            for (int row = height-1 ; row >= 0; row--) {
                if (copyMap[row][col] != 0) {
                    copyMap[writeRow][col] = copyMap[row][col];
                    if (writeRow != row) {
                        copyMap[row][col] = 0;
                    }
                    writeRow--;
                }
            }
        }
    }
}
