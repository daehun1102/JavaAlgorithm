package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class pblm4014 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] map;
    static int mapSize;
    static int flightWidth;
    
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            flightWidth = Integer.parseInt(st.nextToken());
            
            map = new int[mapSize][mapSize];
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }
            int answer = 0;

            for (int row = 0; row < mapSize; row++) {
                int[] tmpArray = map[row];
                if (isFlightLine(tmpArray)) answer++;
            }

            for (int col = 0; col < mapSize; col++) {
                int[] tmp = new int[mapSize];
                for (int row = 0; row < mapSize; row++) {
                    tmp[row] = map[row][col];
                }
                if (isFlightLine(tmp)) answer++;
            }

            System.out.println("#" + testCase + " " + answer);
        
        }
    }

    public static boolean  isFlightLine(int[] arr) {
        int lt = 0, rt = 0;
        
        while(rt<mapSize-1){
            //1. 다음높이랑 비교
            int diff = arr[rt+1] - arr[rt];
            // 1-1. 같은거면 rt 만추가
            if( diff == 0 ) {
                rt++;
            }
            else if (diff == 1) {
                // 다음이 1높으면 이전 길이 활주로 비교
                if ( rt-lt + 1 >= flightWidth ) {
                    rt++;
                    lt = rt;
                } else {
                    // 이전길이 활주로보다 작으면 종료
                    return false;
                }
            }

            else if (diff == -1) {
                if (rt+flightWidth >= mapSize) return false;
                int base = arr[rt + 1];
                // 다음이 1작으면 다음 길이 활주로랑 비교
                for (int i = 1; i <= flightWidth; i++) {
                    int idx = rt + i;
                    if (idx >= mapSize || arr[idx] !=base) {
                        // 하나라도 다르면 종료
                        return false;
                    }
                }
                rt += flightWidth;
                lt = rt + 1;
                rt--; // 겹치는경우를 제거하기 위해 --
            }
            else {
                return false;
            }
        }
        // 위 과정을 무사히 통과하면 true
        return true;
    }
}
