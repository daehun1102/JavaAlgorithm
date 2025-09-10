package swea.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 앱은 사용하는 메모리 용량과 비용, 두가지 정보를 가지고 있다. 
 * 2. 필요한 메모리를 확보하기 위해 비활성화의 최소비용을 계산해야한다.
 * 
 * 
 * 아이디어
 * 1. 배낭이랑 똑같은데 최대가 아닌 최소의 가치를 찾아야하는 문제
 * 2. 반대로 뒤집으면 똑같다. 가치를 전부 더하고, 최대한 많은 가치를 구하는 방식
 * 
 * 문제풀이
 * 1. 활성화되어 있는 앱의 수와, 필요한 메모리 바이트공간을 받는다.
 * 2. 앱의 정보 바이트 , 비용을 받는다.
 * 3. dp 로 앱의 개수, 바이트를 돌면서 특정 앱의 개수를 고려할때, 특정 바이트일때의 최대비용을 구한다.
 *  3-1. dp[입력 합-메모리 필요한 용량] 을 구하면됨 
 * 
 */
public class pblm7579 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] appInfos; //  0번은 메모리 1번이 비용
    static int[] dp;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        int appNum = Integer.parseInt(st.nextToken());
        int needMemory = Integer.parseInt(st.nextToken());
        appInfos = new int[appNum+1][2];

        // 메모리 받기
        st = new StringTokenizer(br.readLine().trim());
        for (int appIndex = 1; appIndex <= appNum; appIndex++) {
            appInfos[appIndex][0] = Integer.parseInt(st.nextToken());
        }
    
        // 비용 받기
        st = new StringTokenizer(br.readLine().trim());
        for (int appIndex = 1; appIndex <= appNum; appIndex++) {
            appInfos[appIndex][1] = Integer.parseInt(st.nextToken());
        }
        
        int memorySum = 0;
        int costSum = 0;
        for (int[] appInfo : appInfos) {
            memorySum += appInfo[0];
            costSum += appInfo[1];
        }
        
        // dp 배열이 의미 : 앱을 N개 까지 고려했을때 최대 비용 
        // 2차원배열은 메모리초과,, 어차피 현재값을 결정할때 바로 이전 최적해들만 보면 되기 때문에 1차원배열로 만들어서 덮어 쓰자.
        dp = new int[memorySum-needMemory+1];

        // dp 배열을 채우자
        for (int appIndex = 1; appIndex <= appNum; appIndex++) {
            for (int memory = memorySum-needMemory; memory >=1 ; memory--) {
                // dp 는  특정 앱의 개수를 고려할때, 특정 바이트내에서 최대비용을 구한다
                if (appInfos[appIndex][0] > memory) continue;
                // 이번의 메모리 제한에서 최댓값을 구하자. 이번걸 선택했을때와 안했을때 중 최댓값 구하기
                dp[memory] = Math.max(dp[memory] ,dp[memory-appInfos[appIndex][0]]+appInfos[appIndex][1] );
            }
        }
        // 디버깅용
        // System.out.println(Arrays.toString(dp));
        
        // dp 배열 다채워졌으니, dp 마지막이 최대로 뺄 수 있는 비용
        System.out.println(costSum - dp[memorySum-needMemory]);
    }
}
