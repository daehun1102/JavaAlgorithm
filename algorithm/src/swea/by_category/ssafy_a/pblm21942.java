package swea.by_category.ssafy_a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제풀이
 * 시뮬레이션
 * 
 * 1. 배열의 첫열, 첫행을 돌면서 1이 있다면 시뮬레이션 시작
 * 2. 자석의 첫시작 인덱스와 끝 인덱스를 만든다.
 *  2-1. 자석의 끝 인덱스가 맵끝까지 갔을때까지 탐색한다.
 *      2-1-1. 다음 인덱스가 자석이라면,
 *          2-1-1-1. 자석세기 구하기 
*       2-1-2. 자석세기보다 크다면 세기, 끝인덱스 바꿔주기, 작다면 끝--  
 */
public class pblm21942 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int mapSize;
    static int[][] map;
    public static void main(String[] args) throws  IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());

        int testCaseNum = Integer.parseInt(st.nextToken());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            mapSize = Integer.parseInt(br.readLine().trim());
            map = new int[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            
            // 자석아래 위치
            for (int col = 0; col < mapSize; col++) {
                if (map[0][col] == 1) {
                    simulationCol(col);
                }
            }
            
            // 자석오른쪽 위치
            for (int row = 0; row < mapSize; row++) {
                if (map[row][0] == 1) {
                    simulationRow(row);
                }
            }

            int countLastRow = 0;
            for (int col = 0; col < mapSize; col++) {
                if (map[mapSize-1][col] == 1) countLastRow++;
            }

            int countLastCol = 0;
            for (int row = 0; row < mapSize; row++) {
                if (map[row][mapSize-1] == 1) countLastCol++;
            }
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(countLastRow).append(' ').append(countLastCol);
            System.out.println(sb);
        }
    }

    public static void simulationCol(int simulationCol){
        // 자석을 아래에 뒀을때부터 일단 구현
        int leftIndex = 0 , rightIndex = 0;
        double myPower = 1;
        while (rightIndex < mapSize-1 ) {
            if (map[rightIndex+1][simulationCol] == 1) { // 다음이 자석이라면
                
                // 다음 자석 세기를 구하고,
                int tmpRightIndex = rightIndex + 1;
                int tmpPower = 1;
                while (tmpRightIndex < mapSize-1) { 
                    if (map[tmpRightIndex+1][simulationCol] == 1){
                        tmpRightIndex++;
                        tmpPower++;
                    } else {
                        break;
                    }
                } 

                // 내 파워와 비교
                if (myPower > tmpPower) {
                    myPower += tmpPower;
                    rightIndex = tmpRightIndex;
                } else {
                    break;
                }

            } else {
                myPower *= 1.9;
                leftIndex++;
                rightIndex++;
            }
        }

        // 다끝났으면 leftIndex 기준으로 맵정리
        for (int row = 0; row < leftIndex; row++) {
            map[row][simulationCol] = 0;
        }

        for (int row = leftIndex; row <= rightIndex; row++) {
            map[row][simulationCol] = 1;
        }
    }

    // 자석을 오른쪽에 뒀을때
    public static void simulationRow(int simulationRow){
        int leftIndex = 0 , rightIndex = 0;
        double myPower = 1;
        while (rightIndex < mapSize-1 ) {
            if (map[simulationRow][rightIndex+1] == 1) { // 다음이 자석이라면
                
                // 다음 자석 세기를 구하고,
                int tmpRightIndex = rightIndex + 1;
                int tmpPower = 1;
                while (tmpRightIndex < mapSize-1) { 
                    if (map[simulationRow][tmpRightIndex+1] == 1){
                        tmpRightIndex++;
                        tmpPower++;
                    } else {
                        break;
                    }
                } 

                // 내 파워와 비교
                if (myPower > tmpPower) {
                    myPower += tmpPower;
                    rightIndex = tmpRightIndex;
                } else {
                    break;
                }

            } else {
                myPower *= 1.9;
                leftIndex++;
                rightIndex++;
            }
        }

        // 다끝났으면 leftIndex 기준으로 맵정리
        for (int col = 0; col < leftIndex; col++) {
            map[simulationRow][col] = 0;
        }

        for (int col = leftIndex; col <= rightIndex; col++) {
            map[simulationRow][col] = 1;
        }
    }
}
