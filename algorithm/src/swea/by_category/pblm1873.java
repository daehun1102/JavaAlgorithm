package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
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

    static int[][] map;
    static int battleCharLen;
    static char[] battleCharArray;
    static char[] tankList = {'>','<','^','v'};
    static char[] moveList = {'U','D','L','R'};

    public static void main(String[] args) throws IOException {
        // 1. 테스트 케이스를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());

        // 2. 맵 크기가 주어진다.
        st = new StringTokenizer(br.readLine().trim());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());

        // 3. 맵을 받는다.
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < height; col++) {
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        // 4. 동작 문자열 길이를 받는다.
        battleCharLen = Integer.parseInt(br.readLine().trim());

        // 5. 동작 배열을 받는다.
        battleCharArray = br.readLine().toCharArray();

        // + 전차의 현재 위치 찾기
        int[] startIndex = new int[2];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (char tank : tankList) {
                    if(tank == map[row][col]) {
                        startIndex[0] = row;
                        startIndex[1] = col;
                    }
                }
            }
        }

        // 8. 동작 배열을 순회하면서 문자를 구별해서 함수를 호출한다.
        for (char battle : battleCharArray) {
            if (battle == 'S') {
                shouting(battle, battle, battle); // 파라미터부터 다시
                continue;
            }
            for (int move : moveList) {
                if (move == battle) {
                    shouting(battle, move, battle); // 파라미터부터 다시
                }
            }

        }
    }
    // 6. shouting method 만들기 (in : 좌표, 방향 / out : void )
    public static void shouting (int row,int col, char direction){

    }

    // 7. move method 만들기 (in : 좌표, 문자 / out : void )
    public static void moving (int row, int col, char direction) {

    }
}
