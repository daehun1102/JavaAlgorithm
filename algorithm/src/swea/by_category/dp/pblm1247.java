package swea.by_category.dp;

import java.io.*;
import java.util.*;

/**
 * 
 * 메모제이션 기법 사용해서 같은 결과 있으면 바로 반환
 * 
 */
public class pblm1247 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int customerCount;
    static int[] companyPos, homePos;    
    static int[][] customerPos;          
    static int[][] distanceMatrix;         
    static boolean[] usedArray;            
    static Map<String, Integer> memo;     

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseCount = Integer.parseInt(br.readLine().trim());

        for (int testCaseIndex = 1; testCaseIndex <= testCaseCount; testCaseIndex++) {
            customerCount = Integer.parseInt(br.readLine().trim());
            // 회사, 집, 고객들 좌표 입력
            st = new StringTokenizer(br.readLine().trim());
            companyPos = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };
            homePos    = new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };

            customerPos = new int[customerCount][2];
            for (int customerIndex = 0; customerIndex < customerCount; customerIndex++) {
                customerPos[customerIndex][0] = Integer.parseInt(st.nextToken());
                customerPos[customerIndex][1] = Integer.parseInt(st.nextToken());
            }

            // 거리 행렬 구성
            int totalNodes = customerCount + 2;
            distanceMatrix = new int[totalNodes][totalNodes];

            for (int from = 0; from < customerCount; from++) {
                distanceMatrix[0][from + 1] = getDistance(companyPos, customerPos[from]); 
                distanceMatrix[from + 1][customerCount + 1] = getDistance(customerPos[from], homePos);
                for (int to = 0; to < customerCount; to++) {
                    distanceMatrix[from + 1][to + 1] = getDistance(customerPos[from], customerPos[to]); 
                }
            }

            usedArray = new boolean[customerCount + 1]; 
            memo = new HashMap<>();

            int answer = dfs(0, 0); 
            System.out.println("#" + testCaseIndex + " " + answer);
        }
    }

    // dfs 로 탐색하면서
    static int dfs(int currentNode, int visitCount) {
        if (visitCount == customerCount) {
            // 모든 고객 방문 끝 -> 집으로
            return distanceMatrix[currentNode][customerCount + 1];
        }

        // 만약에 기록해둔거 있으면 바로 return
        String key = makeKey(currentNode, usedArray);
        Integer cached = memo.get(key);
        if (cached != null) return cached;

        int best = Integer.MAX_VALUE;

        for (int next = 1; next <= customerCount; next++) {
            if (usedArray[next]) continue;
            usedArray[next] = true;

            int candidate = distanceMatrix[currentNode][next] + dfs(next, visitCount + 1);
            if (candidate < best) best = candidate;

            usedArray[next] = false;
        }

        memo.put(key, best);
        return best;
    }

    // 키만들기
    static String makeKey(int currentNode, boolean[] visited) {
        StringBuilder sb = new StringBuilder();
        sb.append(currentNode).append(',');
        for (int i = 1; i <= customerCount; i++) sb.append(visited[i] ? '1' : '0');
        return sb.toString();
    }

    // 거리재는 함수
    static int getDistance(int[] posA, int[] posB) {
        return Math.abs(posA[0] - posB[0]) + Math.abs(posA[1] - posB[1]);
    }
}
