package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 문제 개요
 * 1. 모든 사람을 아래로 내려보내는 최소시간을 찾자.
 * 
 * 아이디어
 * 1. 조합 + 시뮬레이션,,,, 
 *  1-1. 조합은 재귀.. 매선택마다 0 or 1 선택 다 선택하면 시뮬레이션
 * 2. 시뮬레이션은 큐 2개로
 * 
 * 문제 풀이
 * 1. 테스트케이스를 받는다.
 * 2. 맵의 크기를 받고
 * 3. 맵을 받자.
 * 4. 거리를 구해서 한개의 배열에 담자. (인덱스가 사람번호)
 * 5. 재귀로 모든 원소가 2중에 하나의 그룹을 선택해 들어가자.
 *  5-1. 그룹이 완성됐을때 시뮬레이션
 * 6. 시뮬레이션은 1분마다 진행, stairQueue, floorQueue 가 존재
 *  6-1. 1분마다, stairQueue 에 있는값 -1
 *      6-1-1. starirQueue 에 있는값에서 0인게 있으면 빼고 peopleCnt +1
 *  6-2. 1분마다, floorQueue 있는거 전부다 -1, 0 됐으면 stairQueue 에 넣기
 * 
 */
public class pblm2383 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int map[][], mapSize, peopleNum, peopleArray[][];
    static List<int[]> peopleInfoList; // 사람마다 두개의 입구까지의 거리등록
    static List<int[]> stairInfoList; // 계단 좌표, 길이
    static int bestAnswer;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        int testCaseNum = Integer.parseInt(br.readLine().trim());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            mapSize = Integer.parseInt(br.readLine().trim());
            map = new int[mapSize][mapSize];

            // 입력을 받자. 받으면서 people 숫자 생성
            stairInfoList = new ArrayList<>();
            peopleInfoList = new ArrayList<>();
            peopleNum = 0;
            for (int row = 0; row < mapSize; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < mapSize; col++) {
                    int tmpInput = Integer.parseInt(st.nextToken());
                    if (tmpInput == 1) {
                        peopleInfoList.add(new int[] {row,col}); 
                        peopleNum++;       
                    } 
                    if (tmpInput != 0 && tmpInput != 1) stairInfoList.add(new int[]{row,col,tmpInput});
                    map[row][col] = tmpInput;
                }
            }
            
            peopleArray = new int[peopleNum][2];
            // 리스트 돌면서 배열 생성하기
            for (int peopleIndex = 0; peopleIndex < peopleNum; peopleIndex++) {
                for (int stairIndex = 0; stairIndex < 2; stairIndex++) {
                    // 거리를 구해서 넣자
                    int[] peopleCoord = peopleInfoList.get(peopleIndex);
                    int[] stairCoord = stairInfoList.get(stairIndex);
                    peopleArray[peopleIndex][stairIndex] = Math.abs(peopleCoord[0] - stairCoord[0]) + Math.abs(peopleCoord[1] - stairCoord[1]);   
                }
            }
            
            // 디버깅용
            // for (int test = 0; test < peopleNum; test++) {
            //     System.out.println(Arrays.toString(peopleArray[test]));
            // }
            Deque<Integer> group1 = new ArrayDeque<>();
            Deque<Integer> group2 = new ArrayDeque<>();
            bestAnswer = Integer.MAX_VALUE;
            makeGroup(0, group1, group2 );

            sb.append('#').append(testCase).append(' ').append(bestAnswer).append('\n');
        }
        System.out.print(sb.toString());
    }

    
    public static void makeGroup(int selectIndex , Deque<Integer> group1, Deque<Integer> group2){
        // 매번 1번계단 2번계단을 선택해서 넣는다. 다 만들어졌을때 simulation
        // 1번계단 선택
        if (selectIndex == peopleNum) {
            simulation(group1, group2);
            return;
        }
        group1.offer(peopleArray[selectIndex][0]);
        makeGroup(selectIndex+1, group1, group2);
        group1.pollLast();

        // 2번계단 선택
        group2.offer(peopleArray[selectIndex][1]);
        makeGroup(selectIndex+1, group1, group2);
        group2.pollLast();
    }

    public static void simulation(Deque<Integer> group1, Deque<Integer> group2) {
        // 계단 길이를 받아와서,
        final int stairLen1 = stairInfoList.get(0)[2];
        final int stairLen2 = stairInfoList.get(1)[2];

        // 도착시간 리스트로 변환하고 정렬
        List<Integer> arrival1 = new ArrayList<>(group1);
        List<Integer> arrival2 = new ArrayList<>(group2);
        Collections.sort(arrival1);
        Collections.sort(arrival2);

        int finish1 = simulateOneStair(arrival1, stairLen1);
        int finish2 = simulateOneStair(arrival2, stairLen2);

        // 조합 한번 끝날때마다 최솟값 갱신을 하자. 
        int tmpTime = Math.max(finish1, finish2);
        if (tmpTime < bestAnswer) bestAnswer = tmpTime;
    }

    
    private static int simulateOneStair(List<Integer> arrivals, int stairLen) {
        if (arrivals.isEmpty()) return 0;

        // 현재 계단 위에 있는 사람들의 끝나는 시간들 저장 
        PriorityQueue<Integer> stairQueue = new PriorityQueue<>();

        int lastFinishTime = 0;

        // 계단 이용한사람들 보면서,
        for (int arrivalTime : arrivals) {
            int startCandidate = arrivalTime + 1;

            // startCandidate 시점 이전/동시에 끝난 사람들 제거
            while (!stairQueue.isEmpty() && stairQueue.peek() <= startCandidate) {
                stairQueue.poll();
            }

            if (stairQueue.size() < 3) {
                // 계단에 있는사람 3명보다 작을때 
                // 끝나는 시점을 계산해서
                int endTime = startCandidate + stairLen;
                stairQueue.offer(endTime);
                if (endTime > lastFinishTime) lastFinishTime = endTime;
            } else {
                // 계단에 있는사람 3명보다 클때
                // 가장 먼저 끝나는 시간까지 대기 하고
                int earliestEnd = stairQueue.poll(); 
                int startTime = earliestEnd;        
                int endTime = startTime + stairLen;
                stairQueue.offer(endTime);
                if (endTime > lastFinishTime) lastFinishTime = endTime;
            }
        }

        return lastFinishTime;
    }
}
