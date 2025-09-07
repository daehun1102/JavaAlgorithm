package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class pblm2112 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int height, width;   
    static int passCount;     

    static int[][] film;
    static int minCount; 

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        
        int testCaseCount = Integer.parseInt(br.readLine().trim());
        for (int testCaseIndex = 1; testCaseIndex <= testCaseCount; testCaseIndex++) {
            // 입력을 받자
            st = new StringTokenizer(br.readLine());
            height = Integer.parseInt(st.nextToken());
            width = Integer.parseInt(st.nextToken());
            passCount = Integer.parseInt(st.nextToken());
            
            film = new int[height][width];
            
            for (int row = 0; row < height; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < width; col++) {
                    film[row][col] = Integer.parseInt(st.nextToken());
                }
            }
        
            minCount = Integer.MAX_VALUE;
            // 부분집합으로 film 행을 선택
            dfs(0, 0);
            
            sb = new StringBuilder();
            sb.append("#").append(testCaseIndex).append(" ").append(minCount);
            System.out.println(sb);
        }

    }

    static void dfs(int curRow, int tmpCount) {
        // 현재까지가 이미 최적해 이상이면 더 볼 필요가 없음
        if (tmpCount >= minCount) return;

        // 모든 행을 결정했으면 성능 검사
        if (curRow == height) {
            if (passes()) minCount = tmpCount;
            return;
        }

        // 변경 안하는 경우
        dfs(curRow + 1, tmpCount);

        int[] backupRow = film[curRow].clone();
        // 행 0으로 변경하는 경우
        Arrays.fill(film[curRow], 0);
        dfs(curRow + 1, tmpCount + 1);

        // 행 1로 변경하는 경우
        Arrays.fill(film[curRow], 1);
        dfs(curRow + 1, tmpCount + 1);

        film[curRow] = backupRow;
    }

    
    static boolean passes() {
        // 열 하나씩 검사
        for (int col = 0; col < width; col++) {
            int sameCount = 1;
            int preCell = film[0][col]; 
            boolean columnPasses = false;

            // 아래로 가면서,
            for (int row = 1; row < height; row++) {
                int curCell = film[row][col];

                if (curCell == preCell) { // 이전꺼랑 같으면 ++
                    sameCount++;
                } else {
                    // 연속이 끊기면 현재 셀부터 새로 카운트
                    sameCount = 1;
                    preCell    = curCell;
                }

                // 조건 만족
                if (sameCount >= passCount) {
                    columnPasses = true;
                    break;
                }
            }

            // 한 열이라도 실패하면 전체 실패
            if (!columnPasses) return false;
        }
        return true;
    }
}
