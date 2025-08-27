package swea.by_category.twodimension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 문제개요
 * 1. 미생물 군집들이 움직이고 규칙에 따라 미생물들 방향, 숫자가 달라짐
 * 2. 최종 미생물 수를 수하자.
 * 
 * 아이디어
 * 1. 미생물 군집으로 관리
 * 2. 움직이는 메서드, 확인하는 메서드 구현하기.
 * 3. M 번만큼 2번에서 구현한 메서드를 돌리면됨.
 * 
 * 문제풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 테스트케이스마다,
 *  2-1. 맵의 크기, 격리 시간, 미생물 군집 수를 받는다.
 *      2-1-1. 미생물 군집 수 만큼,
 *      2-1-2. 미생물 정보를 등록한다. row, col, misaengNum, direction (1~4)
 * 3. 움직이는 메서드를 구현한다.
 *  3-1. 미생물 군집 리스트를 돌면서
 *      3-1-1. 미생물의 direction 에 따라 움직인다.
 * 4. 체크하는 메서드 구현한다.
        4-1. 가장자리에 있는 거 체크
        4-2. 같은 위치에 있는 군집 체크
 *  
 *  
 * 
 */
public class pblm2382 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int mapSize,time,groupNum;
    static List<int[]> groupInfoList;

    static int[] deltaRow = {0,-1, 1, 0, 0};
    static int[] deltaCol = {0, 0, 0, -1, 1};
    public static void main(String[] args) throws IOException{
        // 1. 테스트케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        int testCaseNum = Integer.parseInt(br.readLine().trim());

        // 2. 테스트케이스마다,
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2-1. 맵의 크기, 격리 시간, 미생물 군집 수를 받는다.
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            time = Integer.parseInt(st.nextToken());
            groupNum = Integer.parseInt(st.nextToken());
            
            groupInfoList = new ArrayList<>();
            
            //2-1-1. 미생물 군집 수 만큼,
            for (int groupIndex = 0; groupIndex < groupNum; groupIndex++) {
                //2-1-2. 미생물 정보를 등록한다. row, col, misaengNum, direction (1~4)
                st = new StringTokenizer(br.readLine());
                int row = Integer.parseInt(st.nextToken());
                int col = Integer.parseInt(st.nextToken());
                int misaengNum = Integer.parseInt(st.nextToken());
                int direction = Integer.parseInt(st.nextToken());
                int[] curInfo = {row,col,misaengNum,direction};
                groupInfoList.add(curInfo);
            }

            // 움직이고 체크
            for (int i = 0; i < time; i++) {
                moving();
                checking();
            }

            // 끝났으면 미생물 더하기
            int answer = 0;
            for (int[] groupInfo : groupInfoList) {
                answer += groupInfo[2];
            }

            sb = new StringBuilder();
            sb.append('#').append(testCase).append(' ').append(answer);
            System.out.println(sb);

        }
    }

    // 3. 움직이드 메서드를 구현한다.
    public static void moving (){
        // 3-1. 미생물 군집 리스트를 돌면서
        for (int[] groupInfo  : groupInfoList) {
            // 3-1-1. 미생물의 direction 에 따라 움직인다.
            groupInfo[0] = groupInfo[0] + deltaRow[groupInfo[3]];
            groupInfo[1] = groupInfo[1] + deltaCol[groupInfo[3]]; 
        }
    }

    // 4. 체킹 메서드를 구현한다.
    public static void checking(){
        // 가장자리 체킹
        for (int groupIndex = 0; groupIndex < groupInfoList.size(); groupIndex++) {
            // 4-1-1. 좌표가 가장자리에 있다면,
            int[] newGroupInfo =  groupInfoList.get(groupIndex);
            if (newGroupInfo[0] == mapSize-1 || newGroupInfo[0] == 0 || newGroupInfo[1] == mapSize-1 || newGroupInfo[1] == 0) {
                // 4-1-1-1. misaengNum 절반, 방향 바꿔주기.
                newGroupInfo[2] = newGroupInfo[2] / 2 ;
                if (newGroupInfo[3] == 1) newGroupInfo[3] = 2;
                else if (newGroupInfo[3] == 2) newGroupInfo[3] = 1;
                else if (newGroupInfo[3] == 3) newGroupInfo[3] = 4;
                else if (newGroupInfo[3] == 4) newGroupInfo[3] = 3; 
            }
        }

        // 같은 위치에 있는 군집 체크 HashMap 으로 구현
        Map<String, int[]> cell = new HashMap<>();
        for (int[] groupInfo : groupInfoList) {
            int row = groupInfo[0], col = groupInfo[1], count = groupInfo[2], direction = groupInfo[3];
            String key = String.valueOf(row) + " " + String.valueOf(col) ;
            int[] value = cell.get(key);

            if (value == null) {
                cell.put(key, new int[]{row,col,count,direction,count});
            }
            else {
                value[2] += count;
                if (value[4] < count) {
                    value[3] = direction;
                    value[4] = count;
                }
            }
        }

        groupInfoList = new ArrayList<>();
        for (int[] value : cell.values()) {
            groupInfoList.add(new int[] {value[0],value[1],value[2],value[3]});
        }
    }
}
