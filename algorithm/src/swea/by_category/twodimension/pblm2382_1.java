package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제풀이
 * 
 * 1. 군집 이동 메서드 (int : x  return : x)
 *  1-1. 군집리스트를 돌면서 군집의 방향에 따라,
 *      1-1-1. 해당방향으로 이동시킨값을 저장한다.
 * 
 * 2. 군집 확인 메서드 (int : x return : x)
 *  2-1. 군집리스트를 돌면서 겹치는 좌표가 있는지 확인
 *      2-1-1. 겹친다면 제일큰 군집 방향으로 이동 -> hash Map 으로 확인
 * 3. 테스트케이스 수를 받고
 * 4. 셀의 수, 격리 시간, 미생물 군집의 개수를 받는다.
 * 5. 미생물 군집 개수만큼,
 *  5-1. 미생물 리스트에 등록한다.
 * 6. 격리 시간만큼,
 *  6-1. 군집을 이동시키고, 
 *  6-2. 군집들을 확인한다.
 */
public class pblm2382_1 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    
    static class Micro{
        int row,col,misaengNum,direction, total;
        boolean isDead;
        
        public Micro(int row, int col, int misaengNum, int direction, boolean isDead) {
            this.row = row;
            this.col = col;
            this.misaengNum = misaengNum;
            this.direction = direction;
            this.total = misaengNum;
            this.isDead = isDead;
        }
    }
    static Micro[] groupList;

    static int mapSize;
    static int time;
    static int groupCount;

    static final int[] deltaRow = {0,-1,1,0,0};
    static final int[] deltaCol = {0,0,0,-1,1};
    
    static Micro[][] visited;
    
    public static void main(String[] args) throws  IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            time = Integer.parseInt(st.nextToken());
            groupCount = Integer.parseInt(st.nextToken());

            groupList = new Micro[groupCount];
            
            visited = new Micro[groupCount][groupCount];
            // 전체 군집 리스트
            for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                groupList[groupIndex] = new Micro(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken()), false);
            
            }
            moving();
        }
    }

    public static int moving() {
        int newRow, newCol, remainCnt = 0;
        while (time --> 0 ){
            for (Micro cur: groupList) {
                newRow = cur.row += deltaRow[cur.direction];
                newCol = cur.col += deltaCol[cur.direction];
                
                // 약품칸 처리
                if(newRow == 0 || newRow == mapSize-1 ||newCol == 0 ||newCol == mapSize-1){
                    // 군집크기 줄이고 방향 반대로
                    cur.total = cur.misaengNum = cur.misaengNum / 2;
                    if (cur.misaengNum == 0) {
                        cur.isDead = true;
                        continue;
                    }

                    // 방향 반대로
                    if (cur.direction % 2 == 1) {
                        cur.direction ++;
                    } else {
                        cur.direction --;
                    }
                }

                // 군집 병합 처리
                if (visited[newRow][newCol] == null) {
                    visited[newRow][newCol] = cur; 
                } else {
                    if (visited[newRow][newCol].misaengNum > cur.misaengNum) {
                        // 미리있던놈의 군집크기가 크다면 더해주기만 
                        visited[newRow][newCol].total += cur.misaengNum;
                        cur.isDead = true;
                    } else {
                        // 미리있던놈의 군집크기가 작다면 더해주고 움직이기
                        cur.total += visited[newRow][newCol].total;
                        visited[newRow][newCol].isDead = true;
                        visited[newRow][newCol] = cur;
                    }
                }
            }   // end for
            remainCnt = reset(); // 현재 시간에 이동한 군집 정리후 살아있는 미생물의 수를 받자.
            time--;
        } // end while
        return remainCnt;
    }

    
    public static int reset() {
        int total = 0;
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                if (visited[row][col] == null ) continue;
                visited[row][col].misaengNum = visited[row][col].total;
                total += visited[row][col].misaengNum;
                visited[row][col] = null;
            }
        }
        return total;
    }
}
