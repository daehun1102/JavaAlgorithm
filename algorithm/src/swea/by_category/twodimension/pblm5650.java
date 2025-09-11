package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. N*N 게임판에, 블록들이 있고, 웜홀 블랙홀 존재.
 * 2. 한방향으로 계속 직진 블록,웜홀,블랙홀 만났을때 상태변화
 * 3. 삼각형은 방향 바꿔줌.. 
 * 4. 웜홀은 들어가면 다른 방향으로 나오게 된다.
 * 5. 블랙홀 만났을때, 처음 위치로 다시 돌아왔을때 게임이 끝난다.
 * 6. 점수의 최댓값 구하기.
 * 
 * 아이디어
 * 1. 방향바꾸는게 중요한듯
 *  1-1. 방향 바꾸는 메서드 (in : dir , out : new dir)
 *  
 * 문제풀이
 * 1. 테스트케이스마다,
 *  1-1. 맵의 크기, 맵을 받는다.
 *  1-2. 탐색을 하면서 0인 곳에서 게임을 시작한다. (in : row,col,dir, out : point)
 *      1-2-1. 시작한곳에 다시오거나, 블랙홀을 만났을때 point 를 return
 *      1-2-2. 방향에 따라 계속 움직일때마다 그자리의 다음값을 확인한다.
 *          1-2-2-1. 웜홀 만났을때, 다른 웜홀로
 *          1-2-2-2. 다른 블록 만났을때, 방향바꿔서 이동한다.
 */
public class pblm5650 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int mapSize;
    static int[][] map;
    static int[] deltaRow = {-1,0,1,0}; // 상우하좌
    static int[] deltaCol = {0,1,0,-1};
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        sb = new StringBuilder();
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            mapSize = Integer.parseInt(br.readLine().trim());
            map = new int[mapSize][mapSize];

            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            int answer = 0;
            // 탐색을 하면서 0인 곳에서 게임 시작
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if(map[row][col] == 0) {
                        for (int dir = 0; dir < 4; dir++) {
                            answer = Math.max(answer,playGame(row,col,dir));
                        }
                    }
                    
                }
            }
            
            sb.append('#').append(testCase).append(' ').append(answer).append('\n');
        }
        System.out.println(sb);
    }

    // 시작좌표에서 해당 방향으로 이동할때 최대값을 얻는다.
    public static int playGame(int sRow, int sCol, int sDir){
        int curRow = sRow + deltaRow[sDir]; // 한번은 움직여 놓기
        int curCol = sCol + deltaCol[sDir];
        int curDir = sDir;
        int point = 0;

        // int debugCount = 0;
        // 1-2-1. 시작한곳에 다시오거나, 블랙홀을 만났을때 point 를 return
        while(true) {

            // 이번이 맵을 나갔다.
            if (curRow < 0 || curCol <0 || curRow >= mapSize || curCol >= mapSize) {
                point++;
                curDir = (curDir + 2) % 4; 
                curRow += deltaRow[curDir]; 
                curCol += deltaCol[curDir];
                continue;
            } 
            
            // 이번이 블랙홀이거나 시작좌표일때
            if ((curRow == sRow && curCol == sCol) || map[curRow][curCol] == -1) break;

            // 웜홀을 만났다.
            if (map[curRow][curCol] >= 6) {
                int tmpValue = map[curRow][curCol];
                int swapRow = curRow;
                int swapCol = curCol;
                map[curRow][curCol] = -10; // 웜홀 만나면 -10 (임시값) 으로 변화시키고
                for (int row = 0; row < mapSize; row++) {
                    for (int col = 0; col < mapSize; col++) {
                        if(map[row][col] == tmpValue) {
                            curRow = row;
                            curCol = col;
                        }
                    }
                }
                map[swapRow][swapCol] = tmpValue; // 다시 돌리자
            }

            // 이번이 블록이라면 방향만 바꾸기
            else if (map[curRow][curCol] >= 1 && map[curRow][curCol] <= 5 ){
                curDir = changeDir(map[curRow][curCol], curDir);
                point++;
            }

            // 다음 좌표로 이동
            curRow += deltaRow[curDir]; 
            curCol += deltaCol[curDir];
        }
        

        return point;
    }
    // 1-2-2-2. 다른 블록 만났을때, 방향바꿔서 이동한다.
    public static int changeDir(int blockType , int dir){
        // 상 우 하 좌
        switch (blockType) {
            // 하 -> 우  좌-> 상
            case 1:
                if (dir == 2 ) return 1; 
                else if (dir == 3 ) return 0; 
                break;
            // 상 좌
            case 2:
                if (dir == 0 ) return 1; 
                else if (dir == 3 ) return 2; 
                break;
            // 상 우
            case 3:
                if (dir == 0 ) return 3; 
                else if (dir == 1 ) return 2; 
                break;
            // 하 우
            case 4:
                if (dir == 2 ) return 3; 
                else if (dir == 1 ) return 0; 
                break;
                
            default:
                return (dir+2)%4;
        }
        // 블록의 평평한 부분 만났을때
        return (dir+2)%4;
    }
}
