package swea.by_category.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 식재료가 들어오면 각자 절반씩 재료를 가지고 요리
 * 2. 요리하고 시너지 점수를 시너지 표를 보고 계산
 * 3. 계산해서 두음식 점수 차이가 제일 적은 값 계산
 * 
 * 아이디어
 * 1. 조합으로 완전탐색 (순서 의미 x)
 * 2. 
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받고
 * 2. 재료개수를 받는다.
 * 3. 시너지 표를 채워넣는다.
 * 4. 조합을 구성해본다.
 * 5. 조합을 구현한다.
 *  5-1. 다뽑았으면,
 *  5-2. 시너지 점수 계산해본다.
 * 6. 시너지 표를 따라서 선택한것과 안한것 나눠서 점수 더해보고 차이 계산
 *
 */
public class pblm4012 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] synergyMap; 
    static int foodNum;
    static int minScore;
    static boolean[] selectedArray;

    public static void main(String[] args) throws IOException {
        // 1. 테스트케이스 개수를 받고
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        int testCaseNum = Integer.parseInt(st.nextToken());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 재료개수를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            foodNum = Integer.parseInt(st.nextToken());

            synergyMap = new int[foodNum][foodNum];
            selectedArray = new boolean[foodNum];
            // 3. 시너지 표를 채워넣는다.
            for (int row = 0; row < foodNum; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < foodNum; col++) {
                    synergyMap[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            minScore = Integer.MAX_VALUE;
            // 4. 조합을 구성해본다.
            combination(0,0);
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(minScore);
            System.out.println(sb);
        }
    }

    // 5. 조합을 구현한다.
    static void combination(int elementIndex, int selectCount) {
        // 5-1. 다뽑았으면,
        if (selectCount == foodNum/2) {
            // 5-2. 시너지 점수 계산해본다.
            minScore = Math.min(calculateScore(), minScore);
            return;
        }

        if (elementIndex == foodNum) return;
        
        selectedArray[elementIndex] = true;
        combination(elementIndex+1, selectCount+1);

        selectedArray[elementIndex] = false;
        combination(elementIndex+1, selectCount);
    }

    static int calculateScore(){
        int synergy1 = 0, synergy2 = 0;
        // 6. 시너지 표를 따라서 선택한것과 안한것 나눠서 점수 더해보고 차이 계산
        for (int row = 0; row < foodNum; row++) {  
            for (int col = row + 1; col < foodNum; col++) {
                int synergyScore = synergyMap[row][col] + synergyMap[col][row];
                if (selectedArray[row] && selectedArray[col]) synergy1 += synergyScore;         
                else if (!selectedArray[row] && !selectedArray[col]) synergy2 += synergyScore;    
            }
        }
        return (int) Math.abs(synergy1 - synergy2);
    }
}
