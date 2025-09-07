package swea.by_category;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class pblm2477 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    // 시각, 고객번호, 받는 창구번호
    static final int IDX_T  = 0;
    static final int IDX_ID = 1;
    static final int IDX_R  = 2;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            
            // 입력을 받자.
            st = new StringTokenizer(br.readLine().trim());
            int receptCount = Integer.parseInt(st.nextToken()); 
            int repairCount = Integer.parseInt(st.nextToken()); 
            int custCount = Integer.parseInt(st.nextToken());
            int targetRecept = Integer.parseInt(st.nextToken()) -1; 
            int targetRepair = Integer.parseInt(st.nextToken()) -1; 

            // 접수/정비 처리시간
            int[] recTime = new int[receptCount];
            st = new StringTokenizer(br.readLine());
            for (int d = 0; d < receptCount; d++) recTime[d] = Integer.parseInt(st.nextToken());

            int[] repTime = new int[repairCount];
            st = new StringTokenizer(br.readLine());
            for (int d = 0; d < repairCount; d++) repTime[d] = Integer.parseInt(st.nextToken());

            // 고객 도착
            int[][] tk = new int[custCount][3]; // 시각, 고객번호, 이용 접수창구
            st = new StringTokenizer(br.readLine());
            for (int custIndex = 0; custIndex < custCount; custIndex++) {
                tk[custIndex][IDX_T] = Integer.parseInt(st.nextToken()); // 도착시간
                tk[custIndex][IDX_ID] = custIndex + 1;    // 고객번호
            }

            // 접수 창구에 배치
            int[] recEnd = new int[receptCount]; 
            for (int custIndex = 0; custIndex < custCount; custIndex++) {
                int arrive = tk[custIndex][IDX_T];

                // 도착한 시각에 비어있는 접수처 중 번호가 가장 작은 창구
                int pick = 0;
                for (int d = 0; d < receptCount; d++) {
                    if (recEnd[d] <= arrive) { pick = d; break; }
                    if (recEnd[d] < recEnd[pick]) pick = d;
                }

                int start = Math.max(recEnd[pick], arrive);
                recEnd[pick] = start + recTime[pick];

                // 종료시각이랑 접수창구 기록
                tk[custIndex][IDX_T] = recEnd[pick]; 
                tk[custIndex][IDX_R] = pick;         
            }

            // 접수 먼저끝난순서, 접수했던 창구번호로 정렬
            Arrays.sort(tk, (a, b) -> {
                if (a[IDX_T] != b[IDX_T]) return a[IDX_T] - b[IDX_T];
                return a[IDX_R] - b[IDX_R];
            });

            // 정비
            int[] repEnd = new int[repairCount]; 
            long sum = 0;

            // 우선순위인 손님부터 처리
            for (int custIndex = 0; custIndex < custCount; custIndex++) {
                int ready   = tk[custIndex][IDX_T];
                int recUsed = tk[custIndex][IDX_R];

                // 정렬 해놨기 때문에 접수랑 동일
                int pick = 0;
                for (int d = 0; d < repairCount; d++) {
                    if (repEnd[d] <= ready) { pick = d; break; }
                    if (repEnd[d] < repEnd[pick]) pick = d;
                }

                int start = Math.max(repEnd[pick], ready);
                repEnd[pick] = start + repTime[pick];

                if (recUsed == targetRecept && pick == targetRepair) {
                    sum += tk[custIndex][IDX_ID];
                }
            }
            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ')
                .append(sum == 0 ? -1 : sum);
            System.out.println(sb);
        }
    }
}
