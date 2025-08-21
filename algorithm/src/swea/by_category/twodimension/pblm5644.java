package swea.by_category.twodimension;

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
 *  4-1. BC 가 적용되는 지역은 리스트에 추가한다.
 * 5. 사용자 이동정보를 순회하면서,
 *  5-1. 만약에 사용자의 위치에 겹치는 영역이 위치한다면,
 *  5-2. 겹치는 영역의 경우의 수를 고려해서 최대인 값을 더한다.
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

    static final int[] deltaUserRow = {0,-1,0,1,0};
    static final int[] deltaUserCol = {0,0,1,0,-1};

    static int[] userAIndex;
    static int[] userBIndex;

    static int totalCharging;

    public static void main(String[] args) throws IOException {
        //1. 테스트케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 총 이동시간, BC 개수를 받고,
            st = new StringTokenizer(br.readLine().trim());
            timeSize = Integer.parseInt(st.nextToken());
            bcNum = Integer.parseInt(st.nextToken());
            
            userA = new int[timeSize];
            userB = new int[timeSize];

            userAIndex = new int[]{0,0};
            userBIndex = new int[]{9,9};
            map = new ArrayList[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = new ArrayList<>();
                }
            }
            
            // 3. 사용자 AB 이동 정보를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            for (int timeIndex = 0; timeIndex < timeSize; timeIndex++) {
                userA[timeIndex] = Integer.parseInt(st.nextToken()); 
            }
            
            st = new StringTokenizer(br.readLine().trim());
            for (int timeIndex = 0; timeIndex < timeSize; timeIndex++) {
                userB[timeIndex] = Integer.parseInt(st.nextToken()); 
            }

            bcInfoArray = new int[bcNum+1][4];
            // 3-1. BC 개수만큼,
            for (int bcIndex = 1; bcIndex <= bcNum; bcIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                // 3-1-1. BC 좌표, BC 범위, BC 차저값을 받는다.
                bcInfoArray[bcIndex][1] = Integer.parseInt(st.nextToken())-1; 
                bcInfoArray[bcIndex][0] = Integer.parseInt(st.nextToken())-1;
                bcInfoArray[bcIndex][2] = Integer.parseInt(st.nextToken());
                bcInfoArray[bcIndex][3] = Integer.parseInt(st.nextToken());
            }
            
            // 4. BC 좌표를 순회하면서,
            for (int bcIndex = 1; bcIndex <= bcNum; bcIndex++) {
                addInfo(bcInfoArray[bcIndex][0],bcInfoArray[bcIndex][1],bcInfoArray[bcIndex][2], bcIndex );
            }

            // 5. 사용자 이동정보를 순회하면서 
            totalCharging = getMax(userAIndex[0],userAIndex[1],userBIndex[0], userBIndex[1]);
            for (int timeIndex = 0; timeIndex < timeSize; timeIndex++) {
                move(timeIndex);
                totalCharging += getMax(userAIndex[0],userAIndex[1],userBIndex[0], userBIndex[1]);
            }
            
            sb = new StringBuilder();
            sb.append("#").append(testCase).append(" ").append(totalCharging);
            System.out.println(sb);
        }
    }
    
    // 정보를 추가하는 메서드.
    public static void addInfo(int tmprow, int tmpcol, int chargerRange, int bcIndex){
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                // 4-1. 거리를 재보고 BC 가 적용되는 지역은 리스트에 추가한다.
                int distance = Math.abs(row-tmprow) + Math.abs(col-tmpcol);
                if (chargerRange >= distance) {
                    map[row][col].add(bcIndex);
                }
            }
        }
    }
    
    // 사용자 좌표를 움직이는 메서드
    public static void move(int timeIndex){
        int userADirectionIndex = userA[timeIndex];
        int userBDirectionIndex = userB[timeIndex];
        
        // 새로운 user 좌표를 불러와서
        int newRowA = userAIndex[0] + deltaUserRow[userADirectionIndex]; 
        int newColA = userAIndex[1] + deltaUserCol[userADirectionIndex];
        
        int newRowB = userBIndex[0] + deltaUserRow[userBDirectionIndex]; 
        int newColB = userBIndex[1] + deltaUserCol[userBDirectionIndex];
        
        // 좌표 초기화
        userAIndex[0] = newRowA;
        userAIndex[1] = newColA;
        userBIndex[0] = newRowB;
        userBIndex[1] = newColB;
    }

    // 매번 점수의 최대값을 기록하는 메서드
    public static int getMax(int rowA, int colA, int rowB, int colB){
        int maxCharging = 0;
        int tmpCharging = 0;
        
        // 두개의 배터리 리스트를 확인하고
        ArrayList<Integer> userAbcList = map[rowA][colA];
        ArrayList<Integer> userBbcList = map[rowB][colB]; 
        
        // 최대인 경우의 수를 구하기
        // 5-1. 만약에 사용자의 위치에 겹치는 영역이 위치한다면,
        // 5-2. 겹치는 영역의 경우의 수를 고려해서 최대인 값을 더한다.
        if (!userBbcList.isEmpty() && !userAbcList.isEmpty()) {
            for (Integer userBbc : userBbcList) {
                for (Integer userAbc : userAbcList) {
                    tmpCharging = 0;
                    if (userAbc == userBbc) {
                        tmpCharging = bcInfoArray[userAbc][3];
                    } else {
                        tmpCharging = bcInfoArray[userAbc][3] + bcInfoArray[userBbc][3];
                    }
                    maxCharging = Math.max(tmpCharging, maxCharging);
                }
            }
        }

        else if (userBbcList.isEmpty()) {
            for (Integer userAbc : userAbcList) {
                tmpCharging = bcInfoArray[userAbc][3];
                maxCharging = Math.max(tmpCharging, maxCharging);
            }
        }
        
        else if (userAbcList.isEmpty()) {
            for (Integer userBbc : userBbcList) {
                tmpCharging = bcInfoArray[userBbc][3];
                maxCharging = Math.max(tmpCharging, maxCharging);
            }
        }

        return maxCharging;
    }
}
