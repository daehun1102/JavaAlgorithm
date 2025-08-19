package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 사용자 2명이 움직이는데
 * 2. 둘의 충전량 합의 최대값을 구한다.
 * 
 * 아이디어
 * 1. 둘이 같은 영역에 들어왔을때,
 *  1-1. 만약에 두 사용자중에 겹치는 영역에 있는 사용자가 있다면,
 *  1-2. 두 사용자 각각 영역이 만들어지는 경우의 수를 나눠서 최대값을 찾는다.
 * 
 * 문제 풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 총 이동시간, BC 개수를 받고,
 * 3. 사용자 AB 이동 정보를 받는다.
 *  3-1. BC 개수만큼,
 *  3-1-1. BC 좌표, BC 범위, BC 차저값을 받는다.
 * 4. BC 좌표를 순회하면서,
 *  4-1. BC 가 적용되는 지역은 리스트에 추가한다. (높은 거 2개까지만 저장)
 * 5. 사용자 이동정보를 순회하면서,
 *  5-1. 만약에 사용자의 위치에 겹치는 영역이 위치한다면,
 *  5-2. 겹치는 영역의 4가지 경우의 수를 고려해서 최대인 값을 더한다.
 *  
 */
public class pblm5644 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static ArrayList<Integer>[][] map;
    static int testCaseNum;
    
    static final int mapSize = 10;
    static int timeSize;
    static int bcNum;
    static int[][] bcInfoArray;
    static int[] userA;
    static int[] userB;
    
    static final int[] deltaRow = {0,1,0,-1};
    static final int[] deltaCol = {-1,0,1,0};

    static final int[] deltaUserRow = {0,0,1,0,-1};
    static final int[] deltaUserCol = {0,-1,0,1,0};

    static int[] userAIndex = {0,0};
    static int[] userBIndex = {9,9};

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            timeSize = Integer.parseInt(st.nextToken());
            bcNum = Integer.parseInt(st.nextToken());
            
            userA = new int[timeSize];
            userB = new int[timeSize];

            map = new ArrayList[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = new ArrayList<>();
                }
            }

            st = new StringTokenizer(br.readLine().trim());
            for (int timeIndex = 0; timeIndex < timeSize; timeIndex++) {
                userA[timeIndex] = Integer.parseInt(st.nextToken()); 
            }
            
            st = new StringTokenizer(br.readLine().trim());
            for (int timeIndex = 0; timeIndex < timeSize; timeIndex++) {
                userB[timeIndex] = Integer.parseInt(st.nextToken()); 
            }

            bcInfoArray = new int[bcNum][4];
            for (int bcIndex = 0; bcIndex < bcNum; bcIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                
                bcInfoArray[bcIndex][1] = Integer.parseInt(st.nextToken()); 
                bcInfoArray[bcIndex][0] = Integer.parseInt(st.nextToken());
                bcInfoArray[bcIndex][2] = Integer.parseInt(st.nextToken());
                bcInfoArray[bcIndex][3] = Integer.parseInt(st.nextToken());
            }

            for (int bcIndex = 1; bcIndex <= bcNum; bcIndex++) {
                addInfo(bcInfoArray[bcIndex][0],bcInfoArray[bcIndex][1],bcInfoArray[bcIndex][2], bcIndex );
            }
        }
    }

    // 정보를 추가하는 메서드.
    public static void addInfo(int tmprow, int tmpcol, int chargerRange, int bcIndex){
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                int distance = Math.abs(row-tmprow) + Math.abs(col-tmpcol);
                if (chargerRange >= distance) {
                    map[row][col].add(bcIndex);
                }
            }
        }
    }

    // 매번 점수의 최대값을 기록하는 메서드
    public static int getMax(int timeIndex){
        int maxCharging = 0;

        // 새로운 user 좌표를 불러와서
        int newRowA = userAIndex[0] + deltaUserRow[timeIndex]; 
        int newColA = userAIndex[1] + deltaUserCol[timeIndex];

        int newRowB = userBIndex[0] + deltaUserRow[timeIndex]; 
        int newColB = userBIndex[1] + deltaUserCol[timeIndex];

        // 두개의 배터리 리스트를 확인하고
        ArrayList<Integer> userAbcList = map[newRowA][newColA];
        ArrayList<Integer> userBbcList = map[newRowB][newColB]; 

        // 최대인 경우의 수를 구하기
        for (Integer userBbc : userBbcList) {
            for (Integer userAbc : userBbcList) {
                if (userAbc == userBbc) {
                    int tmpCharging = bcInfoArray[userAbc][3];
                    maxCharging = Math.max(tmpCharging, maxCharging);
                } else {
                    int tmpCharging = bcInfoArray[userAbc][3] + bcInfoArray[userBbc][3];
                    maxCharging = Math.max(tmpCharging, maxCharging);
                }
            }
        }

    }
}
