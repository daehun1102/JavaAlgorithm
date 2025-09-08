package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. bfs 를 하는데 뎁스마다 탐색해야될걸 리스트에 담고 확장하고 리스트에 있던걸 큐에 넣기
 * 2. 악마의 손아귀 배열은 매번 4방향으로 퍼뜨리면 된다.
 * 
 */
public class pblm7793 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int height;
    static int width;
    static char[][] map;
    static boolean[][] visited;
    static boolean[][] devil; 
    static List<int[]> devilList;

    static int[] deltaRow = {0,0,1,-1};
    static int[] deltaCol = {1,-1,0,0};

    static int startRow,startCol,endRow,endCol;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            height = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            map = new char[height][width];
            visited = new boolean[height][width];
            for (int row = 0; row < height; row++) {
                map[row] = br.readLine().toCharArray();
            }
            devilList = new ArrayList<>();
            // 좌표 기록을 하자.
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (map[row][col] == 'S') {
                        startRow = row;
                        startCol = col;
                    } else if (map[row][col] == 'D') {
                        endRow = row;
                        endCol = col;
                    } else if (map[row][col] == '*') {
                        devilList.add(new int[]{row,col});
                    } 
                }
            }

            // 초기 좌표 설정
            devil = new boolean[height][width];
            for (int[] cord  : devilList) {
                devil[cord[0]][cord[1]] = true;
            }

            
            int answer = bfs(startRow, startCol);
            if (answer > 0) {
                System.out.println("#" + testCase + " " + answer);
            } else {
                System.out.println("#" + testCase + " " + "GAME OVER");
            }
        
        }
    }
    // 죽음의 손아귀
    public static void devilHand() {
        List<int[]> newDevilList = new ArrayList<>();
        for (int[] devilCord : devilList) {
            int row = devilCord[0];
            int col = devilCord[1];
            if (devil[row][col]) {
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    int newRow = row + deltaRow[deltaIndex];
                    int newCol = col + deltaCol[deltaIndex];
                    if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) continue;
                    if (devil[newRow][newCol]) continue;
                    if (newRow == endRow && newCol == endCol) continue;
                    if (map[newRow][newCol] == 'X') continue;

                    devil[newRow][newCol] = true;
                    newDevilList.add(new int[]{newRow,newCol});
                }
            }
        }
        devilList = newDevilList;
    }

    public static int bfs(int initRow, int initCol) {
        int depth = 0;
        Deque<int[]> queue = new ArrayDeque<>();
        visited[initRow][initCol] = true;
        queue.offer(new int[] {initRow, initCol, depth});
        // 1. bfs 를 하는데 뎁스변할때마다 확장하고 큐에 넣기
        while (!queue.isEmpty()) { 
            // 확장하기 
            devilHand();

            int queSize = queue.size();
            for (int q = 0; q < queSize; q++) {
                int[] curCord = queue.poll();
                    
                if (curCord[0] == endRow && curCord[1] == endCol ) {
                    return curCord[2];
                }
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    int newRow = curCord[0] + deltaRow[deltaIndex];
                    int newCol = curCord[1] + deltaCol[deltaIndex];
                    if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) continue;
                    if (visited[newRow][newCol]) continue;
                    if (map[newRow][newCol] == 'X') continue;
                    if (devil[newRow][newCol]) continue;
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow,newCol,depth+1});
                }
            }

            // 한뎁스 끝나면 다음 뎁스
            depth++;
        }
        return 0;
    }
}
