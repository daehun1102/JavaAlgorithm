package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 맵이 주어지면 결과맵을 뽑아보자.
 * 
 * 아이디어. 
 * 1. shouting method 만들기 (in : 좌표, 방향 / out : void )
 * 2. move method 만들기 (in : 좌표, 문자 / out : void )
 * 
 * 문제 풀이
 * 1. 테스트 케이스를 받는다.
 * 2. 맵 크기가 주어진다.
 * 3. 맵을 받는다.
 * 4. 동작 문자열 길이를 받는다.
 * 5. 동작 배열을 받는다.
 * 6. shouting method 만들기 (in : 좌표, 방향 / out : void )
 *      6-1. 현재 방향을 오른, 왼 // 위, 아래를 구분하고,   
 *      6-2. 방향 따라 가다가 벽돌을 만나면 맵의 벽돌을 . 으로 바꾸기
 * 7. move method 만들기 (in : 좌표, 문자 / out : void )
 *      7-1. 움직이는데 맵 밖이 아니고,
 *      7-2. 평지일때만 움직이기
 * 
 * 8. 동작 배열을 순회하면서 문자를 구별해서 함수를 호출한다.
 */
public class pblm1873 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static int width, height;

    static char[][] map;
    static int battleCharLen;
    static char[] battleCharArray;
    static char[] tankList = {'^','v','<','>'};
    static char[] moveList = {'U','D','L','R'};

    static final int[] deltaRow = {-1,1,0,0};
    static final int[] deltaCol = {0,0,-1,1};

    static int[] tankIndex;
    public static void main(String[] args) throws IOException {
        // 1. 테스트 케이스를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 맵 크기가 주어진다.
            st = new StringTokenizer(br.readLine().trim());
            height = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            map = new char[height][width];

            // 3. 맵을 받는다.
            for (int row = 0; row < height; row++) {
                map[row] = br.readLine().trim().toCharArray();
            }
    
            // 4. 동작 문자열 길이를 받는다.
            battleCharLen = Integer.parseInt(br.readLine().trim());
    
            // 5. 동작 배열을 받는다.
            battleCharArray = br.readLine().trim().toCharArray();
    
            // + 전차의 현재 위치 찾기
            tankIndex = new int[2];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    for (char tank : tankList) {
                        if(tank == map[row][col]) {
                            tankIndex[0] = row;
                            tankIndex[1] = col;
                        }
                    }
                }
            }
    
            // 8. 동작 배열을 순회하면서 문자를 구별해서 함수를 호출한다.
            for (char battle : battleCharArray) {
                if (battle == 'S') {
                    shouting(tankIndex[0], tankIndex[1]);
                    continue;
                }
                for (char move : moveList) {
                    if (move == battle) {
                        moving(tankIndex[0], tankIndex[1], battle);
                    }
                }
            }
    
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ");
            for (int row = 0; row < height; row++) {
                if (row == height-1){
                    sb.append(map[row]);
                } else {
                    sb.append(map[row]).append("\n");
                }
            }
            System.out.println(sb);
        }
    }
    // 6. shouting method 만들기 (in : 좌표, 방향 / out : void )
    public static void shouting (int row,int col){
        // 탱크의 현재 위치에서, 방향을 결정하고
        int shoutingDirection = map[row][col];
        int directionIndex = 0;

        switch (shoutingDirection) {
            case '^':    
                directionIndex = 0;
                break;
            case 'v': 
                directionIndex = 1;   
                break;
            case '<':    
                directionIndex = 2;
                break;
            case '>':    
                directionIndex = 3;
                break;
        }
        
        // 맵밖으로 나갈때까지, 이동하는데 
        while (true) {
            row += deltaRow[directionIndex];
            col += deltaCol[directionIndex];
            if (row<0 || row>=height || col<0 || col>=width) break;
            // 가다가 중간에 * 이 있다면 . 로 바꾸기
            if (map[row][col] == '#') break;
            if (map[row][col] == '*') {
                map[row][col] = '.';
                break;
            }
        }

    }

    // 7. move method 만들기 (in : 좌표, 문자 / out : void ) 평지일때만 이동 가능
    public static void moving (int row, int col, char direction) {
        int newRow = 0;
        int newCol = 0;
        switch (direction) {
            case 'U':
                map[row][col] = '^';
                newRow = row + deltaRow[0];
                newCol = col + deltaCol[0];

                break;
        
            case 'D':
                map[row][col] = 'v';
                newRow = row + deltaRow[1];
                newCol = col + deltaCol[1];     
                break;
        
            case 'L':
                map[row][col] = '<';
                newRow = row + deltaRow[2];
                newCol = col + deltaCol[2];    
                break;
                
            case 'R':
                map[row][col] = '>';
                newRow = row + deltaRow[3];
                newCol = col + deltaCol[3];
                break;
            }
        if (newRow<0 || newRow>=height || newCol<0 || newCol>=width) return;
        
        // 평지라면 이동
        if (map[newRow][newCol] == '.') {
            tankIndex[0] = newRow;
            tankIndex[1] = newCol;
            map[newRow][newCol] = map[row][col];
            map[row][col] = '.';
        }
        }
}
