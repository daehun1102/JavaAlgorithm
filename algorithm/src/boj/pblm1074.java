package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 맵이 들어오고 특정 요소가 몇번째 방문하는 요소인지 색출
 * 
 * 아이디어.
 * 1. 노드마다 4가지 선택지가 주어진다.
 * 2. 4가지선택 중 대소비교로 한가지 선택지를 선택한다.
 * 
 * 문제풀이
 * 1. N, row, col 를 받는다.
 * 2. N 의 숫자가 탐색해야하는 depth 수이다.
 * 3. dfs 를 구현한다.
 *  3-1. depth 가 1일때 row col 이 해당하는 범위의 숫자를 더하고 return
 *  3-2. row col 이 해당하는 범위의 숫자를 더해나간다.
 *  3-3. 숫자를 더하는건 z 의 구간을 구하고 (해당 구간 번호 -1) * 2^(2*(depth-1))
 */
public class pblm1074 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int targetRow;
    static int targetCol;
    static int targetIndex;

    static void findIndex(int startRow, int startCol, int depth) {
        int tempQuaterSize = (int) Math.pow(2, depth-1);
        int accIndex = (int) Math.pow(tempQuaterSize, 2);
        // 3-1. depth 가 1일때 row col 이 해당하는 범위의 숫자를 더하고 return
        if (depth == 1) {
            if (startRow == targetRow && startCol+1 == targetCol) targetIndex = targetIndex+1;
            else if (startRow+1 == targetRow && startCol == targetCol) targetIndex = targetIndex+2;
            else if (startRow+1 == targetRow && startCol+1 == targetCol) targetIndex = targetIndex+3;
            return;
        }
        // *  3-2. row col 이 해당하는 범위의 숫자를 더해나간다.
        if (startRow + tempQuaterSize > targetRow && startCol+tempQuaterSize > targetCol) findIndex(startRow, startCol, depth-1);
        else if (startRow + tempQuaterSize > targetRow && startCol+2*tempQuaterSize > targetCol) {targetIndex = targetIndex+accIndex; findIndex(startRow, startCol+tempQuaterSize, depth-1);}
        else if (startRow + 2*tempQuaterSize > targetRow && startCol+tempQuaterSize > targetCol) {targetIndex = targetIndex+2*accIndex; findIndex(startRow+tempQuaterSize, startCol, depth-1);}
        else if (startRow + 2*tempQuaterSize > targetRow && startCol+2*tempQuaterSize > targetCol) {targetIndex = targetIndex+3*accIndex; findIndex(startRow+tempQuaterSize, startCol+tempQuaterSize, depth-1);}
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        targetRow = Integer.parseInt(st.nextToken());
        targetCol = Integer.parseInt(st.nextToken());
        targetIndex = 0;
        findIndex(0,0, N);
        System.out.println(targetIndex);
    }
}
