package boj.by_category.twopointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 1. 투포인터로 Array 만들어서 unique 원소 찾고 갱신 -> 시간초과
 * 2. 투포인터로 하는데 앞을 넣고 빼주는 식으로 진행
 */
public class pblm15961 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sushiNum, sushiRange, windowLen, coupon;
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        sushiNum = Integer.parseInt(st.nextToken());
        sushiRange = Integer.parseInt(st.nextToken());
        windowLen = Integer.parseInt(st.nextToken());
        coupon = Integer.parseInt(st.nextToken());

        int[] sushiArray = new int[sushiNum*2];

        // 초밥을 받아서 두번 넣자. 투포인터 돌릴건데 rt 가 인덱스 밖으로 가면 앞쪽 원소를 뽑아야하기 땐문
        for (int sushi = 0; sushi < sushiNum; sushi++) {
            sushiArray[sushi] = Integer.parseInt(br.readLine().trim());
        }

        for (int sushi2 = sushiNum; sushi2 < sushiNum*2; sushi2++) {
            sushiArray[sushi2] = sushiArray[sushi2%sushiNum];
        }
        // 투포인터를 돌리자
        int lt,rt;
        lt = 0;
        rt = windowLen-1;
        int answer = 0;
        
        // 초기 Map
        Map<Integer,Integer> sushiMap = new HashMap<>();
        // 쿠폰은 일단 넣어주고
        sushiMap.put(coupon, 1);
        // 초기 Map 을 채워주자
        for (int sushiIndex = lt; sushiIndex <= rt; sushiIndex++) {
            // 있으면 기존갯수+1, 없으면 등록
            if (!sushiMap.containsKey(sushiArray[sushiIndex])) {
                sushiMap.put(sushiArray[sushiIndex], 1);
            } else {
                sushiMap.put( sushiArray[sushiIndex] ,sushiMap.get(sushiArray[sushiIndex]) + 1);
            }
        }
                
        while(lt<sushiNum){
            // 현재 손님이 먹는 스시 집합 만들어서 unique 한 스시 개수 갱신
            ++lt;
            ++rt;
            
            // rt 를 넣을때
            // 있으면 기존갯수+1, 없으면 등록
            if (!sushiMap.containsKey(sushiArray[rt])) {
                sushiMap.put(sushiArray[rt], 1);
            } else {
                sushiMap.put( sushiArray[rt] ,sushiMap.get(sushiArray[rt]) + 1);
            }

            // 이전거 (lt-1 ) 를 뺄때
            // 1이면 제거, 2이상이면 -1
            if (sushiMap.get(sushiArray[lt-1]) == 1) {
                sushiMap.remove(sushiArray[lt-1]);
            } else {
                sushiMap.put(sushiArray[lt-1], sushiMap.get(sushiArray[lt-1])-1);
            }
            answer = Math.max(answer, sushiMap.size());
        }
        System.out.println(answer);
    }
}
