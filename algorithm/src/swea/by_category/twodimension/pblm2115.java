package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1.최대한 많은 수익을 얻어야한다.
 * 2.두명의 일꾼이 일자로 벌꿀을 체취해야한다. (제한이 있다) 
 * 3.수익은 일꾼들이 얻을 수 있는 꿀 양의 제곱의 합이다.
 * 
 * 아이디어
 * 1. 무조건 일꾼들은 일자로 선택할수 있는 최대 개수로 선택
 * 2. 맵이 크지않아서 완전탐색을 하자
 *  2-1. 일꾼이 선택할 수 있는 모든 경우의 수를 구하고
 *  2-2. 각각 선택한 것들 안에서 최대의 값을 산출하고 갱신. (부분집합으로).
 * 
 */
public class pblm2115 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int maxResult;
    static int[][] map;
    static int[] group1,group2;

    static int mapSize, beeLen, maxHoney;
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            beeLen = Integer.parseInt(st.nextToken());
            maxHoney = Integer.parseInt(st.nextToken());
            maxResult = 0;
            // 맵을 받는다.
            map = new int[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            
            
            // 한개의 범위를 선택
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col <= mapSize-beeLen; col++) {
                    group1 = Arrays.copyOfRange(map[row], col, col+beeLen);
                    tmpMaxA = 0;
                    tmpMaxB = 0;
                    powerSet(group1, 0, 0, 0, false); 
                    // 다른 범위를 선택
                    for (int row2 = row; row2 < mapSize; row2++) {
                        for (int col2 = 0; col2 <= mapSize-beeLen; col2++) {
                            if (row2 == row && col2 < col+beeLen) continue; // 같은 행일때 섞이면 안됨.
                            group2 = Arrays.copyOfRange(map[row2], col2, col2+beeLen);
                            powerSet(group2, 0, 0, 0, true);
                            maxResult = Math.max(tmpMaxA+tmpMaxB, maxResult);
                        }
                    }
                }
            }
            
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(maxResult);
            System.out.println(sb);
        }
    }
    
    static int tmpMaxA;
    static int tmpMaxB;
    static void  powerSet(int[] select, int curIdx, int curSum, int curProfit, boolean abFlag) {
        
        // 벌꿀 초과다
        if (curSum > maxHoney) return;
        
        // 모두 골랐다.
        if (curIdx == select.length){
            if (abFlag) {
                tmpMaxB = Math.max(tmpMaxB, curProfit);
            } else {
                tmpMaxA = Math.max(tmpMaxA, curProfit);
            }
            
            return;
        } 
        // 현재 선택
        powerSet(select, curIdx+1, curSum+select[curIdx], curProfit+select[curIdx]*select[curIdx], abFlag);
        // 현재 선택 안함
        powerSet(select, curIdx+1, curSum, curProfit, abFlag);
    }
}
