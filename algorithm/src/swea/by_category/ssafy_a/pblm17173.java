package swea.by_category.ssafy_a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *   
 */
public class pblm17173 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int mapSize, days;
    static int[][] map;
    static int[][] plantMap;

    static int[] deltaRow = {0,-1,0,1}; // 오앞왼뒤
    static int[] deltaCol = {1,0,-1,0};

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            days = Integer.parseInt(st.nextToken());
            map = new int[mapSize][mapSize];
            plantMap = new int[mapSize][mapSize]; // 농작물을 표시하는 맵
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            int maxResult = Integer.MIN_VALUE;
            //모든 배열에서 0인곳에다가 로봇 배치해보기
            for (int row = 0; row < mapSize; row++) {
                for (int col = 0; col < mapSize; col++) {
                    if (map[row][col] == 0) {
                        for (int direction = 0; direction < 4; direction++) { 
                            for (int prow = 0; prow < mapSize; prow++) {
                                Arrays.fill(plantMap[prow], 0);
                            }
                            int tmpResult = getPlant(row, col, direction);
                            maxResult = Math.max(tmpResult, maxResult);
                        }
                    }
                }
            }
            
            
            System.out.println("#" + testCase + " " +maxResult);
        }
    }

    public static int getPlant(int row, int col, int direction) {
        // 일수가 끝나면 return depth
        
        int tmpDay = 1, plantcount = 0;
        int tmpRow = row;
        int tmpCol = col;
        while(tmpDay <= days*2) {

            // 만약에 tmpDay 가 홀수라면 오전
            // 씨앗을 심거나 농작물을 수확한다.
            if (tmpDay%2 == 1) {
                
                
                // 농작물 1씩 추가
                for (int plantRow = 0; plantRow < mapSize; plantRow++) {
                    for (int plantCol = 0; plantCol < mapSize; plantCol++) {
                        if (plantMap[plantRow][plantCol] >= 1) plantMap[plantRow][plantCol]++; 
                    }
                }

                // 오후에 움직일 수 있는지 확인
                boolean canMove = false;
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    int newRow = tmpRow + deltaRow[deltaIndex];
                    int newCol = tmpCol + deltaCol[deltaIndex];

                    if (newRow< 0 || newCol < 0 || newRow>= mapSize || newCol >= mapSize) continue;
                    if (map[newRow][newCol] == 1) continue;
                    if (plantMap[newRow][newCol] >=1 ) continue;
                    canMove = true;
                    break;
                }
                
                if (canMove) {
                    if (plantMap[tmpRow][tmpCol] >=4) { // 씨앗 거두기
                        plantMap[tmpRow][tmpCol] = 0;
                        plantcount++;
                    } else if (plantMap[tmpRow][tmpCol] == 0) { // 없다면 씨앗 심기
                        plantMap[tmpRow][tmpCol] = 1;
                    }
                }
                

                
            } else {
                // 만약에 tmpDay 가 짝수라면 오후
                // 오른쪽 앞 왼쪽 아래 순서로 탐색
                for (int deltaIndex = 0; deltaIndex < 4; deltaIndex++) {
                    int tmpIndex = 0;
                    // 기준에 따라 인덱스 수정
                    if (direction == 1) tmpIndex = deltaIndex;
                    else if (direction == 0) tmpIndex = (deltaIndex + 3) % 4;
                    else if (direction == 2) tmpIndex = (deltaIndex + 1) % 4;
                    else if (direction == 3) tmpIndex = (deltaIndex + 2) % 4;

                    int newRow = tmpRow + deltaRow[tmpIndex];
                    int newCol = tmpCol + deltaCol[tmpIndex];

                    if (newRow< 0 || newCol < 0 || newRow>= mapSize || newCol >= mapSize) continue;
                    if (map[newRow][newCol] == 1) continue;
                    if (plantMap[newRow][newCol] >=1 && plantMap[newRow][newCol] <=4 ) continue;
                    
                    tmpRow = newRow;
                    tmpCol = newCol;
                    direction = tmpIndex;
                    break;
                }
            }
            
            // System.out.println((tmpDay%2 == 1) ? "오전" : "오후" );
            // for (int test = 0; test < mapSize; test++) {
            //     System.out.println(Arrays.toString(plantMap[test]));
            // }
            // System.out.println();
            tmpDay++;
        }

        return plantcount;
    }

}
