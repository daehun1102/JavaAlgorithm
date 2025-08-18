package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 
 * 문제 개요
 * 1. 보드가 주어지고, 프로세스가 주어지면,
 * 2. 최대한 많은 코어를 연결하고, 그때 최소의 전선 길이 합을 구해보자.
 * 
 * 아이디어
 * 1. 코어를 선택할지 말지 부분집합으로 완전탐색,
 * 2. 코어 선택할때마다 4방향으로 전선 놓아보기
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 보드의 사이즈를 받고, 보드 초기화.
 * 3. 보드배열을 받고, 탐색해봐야할 코어 (가장자리가 아닌 코어) 를 리스트에 담기.
 * 4. dfs 를 구현한다.
 *  4-1. 백트래킹 : 조건 남은 코어를 다 연결해도 best core 보다 적으면 return
 *  4-2. 기저조건 : 끝까지 다 코어를 탐색하면,
 *      4-2-1. 코어 개수를 세고 최대라면, 최대 코어개수, 최소 전선길이 최신화.
 *      4-2-2. 코어 개수가 이미 최대값과 동률이라면, 최소 전선길이 최신화.
 *  4-3. 현재 코어의 좌표 가져와서,
 *      4-3-1. 4방향 중 전선 놓을 수 있다면, 
 *      4-3-2. 전선 놓아보고 전선길이를 가지고 다음거 탐색 후 돌아올땐 전선 치우기
 */
public class pblm1767 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int boardSize;
    static int bestConnectedCount;
    static int coreCount;
    static int minWire;

    static int[][] board;
    static final int[] deltaRow = {-1,1,0,0};
    static final int[] deltaCol = {0,0,1,-1};
    static ArrayList<int[]> cores;

    public static void main(String[] args) throws IOException {
        // 1. 테스트케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        int testCaseNum = Integer.parseInt(st.nextToken());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 보드의 사이즈를 받고, 보드 초기화.
            st = new StringTokenizer(br.readLine().trim());
            boardSize = Integer.parseInt(st.nextToken());
            board = new int[boardSize][boardSize];
            // 3. 보드배열을 받고, 탐색해봐야할 코어 (가장자리가 아닌 코어) 를 리스트에 담기.
            cores = new ArrayList<>();
            coreCount = 0;
            for (int row = 0; row < boardSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < boardSize; col++) {
                    board[row][col] = Integer.parseInt(st.nextToken());
                    if (row > 0 && row < boardSize -1 && col > 0 && col < boardSize -1 && board[row][col] == 1) {
                        int[] index = {row,col};
                        cores.add(index);
                        coreCount++;
                    }
                }
            }
            // 4. dfs 를 구현한다.
            bestConnectedCount = 0;
            minWire = Integer.MAX_VALUE;
            dfs(0, 0, 0);
            System.out.println("#" + testCase + " " + minWire);

        }
    }

    static void dfs(int coreIndex, int connectedCount, int wireSum) {
        // 4-1. 백트래킹 : 조건 남은 코어를 다 연결해도 best core 보다 적으면 return
        if (connectedCount + coreCount - coreIndex < bestConnectedCount ) return;

        // 4-2. 기저조건 : 끝까지 다 코어를 탐색하면,
        if (coreIndex == coreCount) {
            // 4-2-1. 코어 개수를 세고 최대라면, 최대 코어개수, 최소 전선길이 최신화.
            if ( connectedCount > bestConnectedCount) {
                bestConnectedCount = connectedCount;
                minWire = wireSum;
            } else if (connectedCount == bestConnectedCount && wireSum < minWire) {
                minWire = wireSum;
            }
            return;
        }

        int currentRow = cores.get(coreIndex)[0];
        int currentCol = cores.get(coreIndex)[1];

        //4-3. 현재 코어의 좌표 가져와서,
        for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
            // 4-3-1. 4방향 중 전선 놓을 수 있다면,
            if (!canLayWire(currentRow,currentCol,deltaIndex)) continue;
            // 4-3-2. 전선 놓아보고 전선길이를 가지고 다음거 탐색 후 돌아올땐 전선 치우기
            int len = layWire(currentRow,currentCol,deltaIndex,2);
            dfs(coreIndex+1, connectedCount+1, wireSum+len);
            layWire(currentRow,currentCol,deltaIndex,0);
        }

        dfs(coreIndex+1, connectedCount, wireSum);
    }

    static boolean canLayWire(int row, int col, int deltaIndex) {
        int newRow = row;
        int newCol = col;
        while (true) {
            newRow += deltaRow[deltaIndex];
            newCol += deltaCol[deltaIndex];
            if (newRow < 0 || newRow >= boardSize || newCol < 0 || newCol >= boardSize) break;
            if (board[newRow][newCol] >=1) return false;
        }
        return true;
    }

    static int layWire(int row, int col, int deltaIndex, int wire) {
        int newRow = row;
        int newCol = col;
        int len = 0;
        while (true) {
            newRow += deltaRow[deltaIndex];
            newCol += deltaCol[deltaIndex];
            if (newRow < 0 || newRow >= boardSize || newCol < 0 || newCol >= boardSize) break;
            board[newRow][newCol] = wire;
            len++;
        }
        return len;
    }
}
