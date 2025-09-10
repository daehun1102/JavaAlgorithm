package swea.by_category.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 벽돌로 가장높은 탑을 쌓자.
 * 2. 벽돌 : 밑면 넓이, 무게, 높이, 고유번호
 * 3. 아래에 밑면이 넓어야하고 무게가 무거워야 한다.
 * 
 * 아이디어
 * 1. 벽돌수가 최대 100이라 부분집합이 너무 오래걸릴듯
 * 2. 조건이 2개이고 넓이는 고유한 값이니까 정렬하자.
 * 3. DP 를 생각해볼때,
 *  2-1. DP[stoneIndex] : stoneIndex 까지 고려했을때 stoneIndex 가 맨 위라면, 그때 구할 수 있는 최대 높이
 * 
 * 문제 풀이
 * 1. 벽돌의 수를 받고
 * 2. 클래스에 저장한다.
 * 3. 벽돌 List 를 만들고
 *  3-1. 넓이를 기준으로 내림차순으로 정렬을 한다. (큰게 아래오기 때문에 초기값은 제일 밑면 넓이가 큰것이다.)   
 * 4. 벽돌을 하나씩 순서대로 골라서
 *  4-0. 점화식 적용 전 조건 이번꺼가 이전벽돌보다 무게가 작아야한다.
 *  4-1. 점화식을 적용한다. 점화식 : dp[curIndex] = max(dp[prevIndex] + cur.height) -> 기록해야하기 때문에 prev 배열 추가
 */
public class pblm2655 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] dp, prev;
    static List<Brick> bricks;
    static class Brick implements Comparable<Brick>{
        int area, weight, height, number;

        public Brick(int area, int weight, int height, int number) {
            this.area = area;
            this.weight = weight;
            this.height = height;
            this.number = number;
        }

        @Override
        public int compareTo(Brick o) {
            return o.area - this.area;
        }
        
        
    }
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        int brickNum = Integer.parseInt(br.readLine().trim());
        dp = new int[brickNum];
        prev = new int[brickNum];
        Arrays.fill(prev, -1);
        bricks = new ArrayList<>();

        // 입력을 받고
        for (int brickIndex = 1; brickIndex <= brickNum ; brickIndex++) {
            st = new StringTokenizer(br.readLine().trim());
            int tmpArea = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            Brick tmpBrick = new Brick(tmpArea, weight, height, brickIndex);
            bricks.add(tmpBrick);
        }

        // 넓이로 정렬을 하자.
        Collections.sort(bricks);

        // 최대 높이,
        // 그때 맨위 벽돌의 인덱스
        int bestVal = 0;
        int bestTop = -1;

        // 벽돌을 하나씩 골라서
        for (int curIndex = 0; curIndex < brickNum; curIndex++) {
            dp[curIndex] = bricks.get(curIndex).height;
            for (int prevIndex = 0; prevIndex < curIndex; prevIndex++) {
                // 점화식 적용 전 조건 : 이번꺼가 이전벽돌보다 무게가 작아야한다.
                if (bricks.get(prevIndex).weight < bricks.get(curIndex).weight) continue;
                
                //점화식을 적용한다. 점화식 : dp[curIndex] = dp[prevIndex] + cur.height
                if (dp[curIndex] < dp[prevIndex] + bricks.get(curIndex).height){
                    dp[curIndex] = dp[prevIndex] + bricks.get(curIndex).height;
                    prev[curIndex] = prevIndex; 
                }
            }
            if (dp[curIndex] > bestVal){
                bestVal = dp[curIndex];
                bestTop = curIndex;
            }
        } 

        ArrayList<Integer> answer = new ArrayList<>();
        int cur = bestTop;
        while (cur != -1){
            answer.add(bricks.get(cur).number);
            cur = prev[cur];
        }

        sb = new StringBuilder();
        sb.append(answer.size()).append('\n');
        for (int id : answer) {
            sb.append(id).append('\n');
        }
        System.out.println(sb.toString());
    }
}
