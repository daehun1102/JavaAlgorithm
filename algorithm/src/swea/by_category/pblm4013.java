package swea.by_category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * 문제 풀이
 * 1. 회전방향 결정 메서드
 *  1-1. 인덱스 2 와 6을 번갈아가면서 확인해서 같은지 안같은지 여부 배열 만들기
 *  1-2. 특정 인덱스와 회전방향이 들어오니까, 다른 인덱스 자리와의 인덱스 차이로
 *      1-2-1. 여부배열 탐색 결과 회전방향과 같게 돌릴지 말지 결정
 * 2. 회전방향으로 회전메서드
 *  2-1. 1,-1 나눠서 deque 돌리기
 * 3. 포인트 계산 메서드 
 * 4. 입력받아서 회전 돌리다가 마지막에 포인트구하기
 */
public class pblm4013 {
    static  BufferedReader br;
    static  StringTokenizer st;
    static  StringBuilder sb;

    static List<LinkedList<Integer>> magneticList;
    public static void main(String[] args) throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            int turnCount = Integer.parseInt(br.readLine().trim());
            
            magneticList = new ArrayList<>();
            for (int mIndex = 0; mIndex < 4; mIndex++) {
                magneticList.add(new LinkedList<>());
            }

            // 자석 List 만들기
            for (int mIndex = 0; mIndex < 4; mIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int index = 0; index < 8; index++) {
                    magneticList.get(mIndex).add(Integer.parseInt(st.nextToken()));
                }
            }

            // getDirection, turning, poingting 메서드 구현  
            for (int turn = 0; turn < turnCount; turn++) {
                st = new StringTokenizer(br.readLine().trim());
                
                int[] direction = getDirection(Integer.parseInt(st.nextToken())-1 ,Integer.parseInt(st.nextToken()) );
                turning(direction);
            }


            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(poingting());
            System.out.println(sb);
        }
    }

    static int[] getDirection(int magneticIndex, int turnDirection) {
        // 1-1. 인덱스 2 와 6을 번갈아가면서 확인해서 방향 같은지 안같은지 여부 배열 만들기
        boolean[] isSame = new boolean[3];
        int[] turnDirections = new int[4]; // 각 자석별 방향 제시
        turnDirections[magneticIndex] = turnDirection; 

        for (int mIndex = 0; mIndex < 3; mIndex++) {
            // mIndex (0 1 2 3 ) 가 짝수라면 현재 2랑 다음 6 비교
            if (magneticList.get(mIndex).get(2).equals(magneticList.get(mIndex+1).get(6)) ){
                isSame[mIndex] = true;
            } 
        }

        // System.out.println(Arrays.toString(isSame));
        // 1-2. 특정 인덱스와 회전방향이 들어오니까, 다른 인덱스 자리와의 인덱스 차이로
        // 특정 인덱스에서 시작해서 끝까지
        for (int curIndex = magneticIndex; curIndex < 3; curIndex++) {
            // 다음거랑 같은극이라면 종료
            if (isSame[curIndex]) break;
            // 다음거랑 다른극이면 다음 자석은 방향 바꿔서 기록
            else turnDirections[curIndex+1] = (turnDirections[curIndex] == 1) ? -1 : 1;  
        }

        // 특정 인덱스에서 시작해서 처음까지
        for (int curIndex = magneticIndex; curIndex > 0 ; curIndex--) {
            // 이전랑 같은극이라면 종료
            if (isSame[curIndex-1]) break;
            // 이전랑 다른극이면 이전 자석은 방향 바꿔서 기록
            else turnDirections[curIndex-1] = (turnDirections[curIndex] == 1) ? -1 : 1;  
        }

        return turnDirections;
    }

    // 2. 회전방향으로 회전
    static void turning (int[] turnDirections) {
        // 2-1. 1,-1 나눠서 deque 돌리기
        // 1이 시계방향 마지막거를 빼서 앞에다가
        // -1 이 반시계방향 앞에거를 빼서 마지막
        for (int mIndex = 0; mIndex < 4 ; mIndex++) {
            if (turnDirections[mIndex] == 1) { // 마지막거를 빼서 앞에다가
                int lastElement = magneticList.get(mIndex).pollLast();
                magneticList.get(mIndex).offerFirst(lastElement);
            } else if (turnDirections[mIndex] == -1 ){
                int lastElement = magneticList.get(mIndex).pollFirst();
                magneticList.get(mIndex).offerLast(lastElement);
            }
        }
    }

    // 3. 포인트 계산
    static int poingting () {
        int totalPoint = 0;
        for (int mIndex = 0; mIndex < 4; mIndex++) {
            int tempCode = magneticList.get(mIndex).peekFirst();
            if (tempCode == 1) {
                totalPoint += Math.pow(2, mIndex);
            }
        }

        return totalPoint;
    }

}
