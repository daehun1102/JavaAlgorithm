package swea.by_category.ssafy_a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 1. 양쪽으로 퍼지면서 검사하기
 * 2. 보물획능 가능한지 확인하는 메서드
 *  2-1. 2포인터로 방문하듯이 양쪽으로 퍼지면서 검사 ( 처음 위치는 false 로 바꾸기 )
 *    2-1-1. 모든 집을 다찾으면 끝
 *    2-1-2. 동시에 발견하면 return false 
 * 3. 
 * 
 */
public class pblm22416 {
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int testCaseNum;
    static int[] goldArr;
    static int roomCount;
    static int goldCount;
    
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        testCaseNum = Integer.parseInt(br.readLine());
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            st = new StringTokenizer(br.readLine().trim());
            roomCount = Integer.parseInt(st.nextToken());
            int startIndex = Integer.parseInt(st.nextToken())-1;
            goldArr = new int[roomCount];
            
            // 배열 받기
            st = new StringTokenizer(br.readLine().trim());
            goldCount = 0;
            for (int index = 0; index < roomCount; index++) {
                int tempInput = Integer.parseInt(st.nextToken());
                goldArr[index] = tempInput;
                if (tempInput == 1) {
                    goldCount++;
                } 
            } 

            sb = new StringBuilder();

            // 보물이 없는경우..?
            if (goldCount == 0 ) {
                sb.append('#').append(testCase).append(' ').append(0);
                System.out.println(sb);
                continue;
            }
            

            // 1. 양쪽으로 퍼지면서 검사하기
            int lt = startIndex, rt = startIndex;
            int answer = 0;
            while (true) {
                // 각자 검사하고 시작점이 되는 곳이 있으면 그만.
                if (lt>=0) {
                    if (check(lt)) break;
                }
                if (rt<roomCount) {
                    if (check(rt)) break;
                }

                answer++;
                lt--;
                rt++;
            }
            sb.append('#').append(testCase).append(' ').append(answer);
            System.out.println(sb);
        }
    }

    // 2. 보물획능 가능한지 확인하는 메서드
    public static boolean check (int sIndex) {
        // 2-1. 2포인터로 방문하듯이 양쪽으로 퍼지면서 검사 ( 처음 위치는 0 로 바꾸기 )
        int lt = sIndex, rt = sIndex;
        int tempGoldCount = goldCount; 
        int[] copyGoldArr = goldArr.clone();

        if (copyGoldArr[sIndex] == 1) {
            copyGoldArr[sIndex] = 0; 
            tempGoldCount--; 
        } 
        while (tempGoldCount > 0) {
            lt--;
            rt++;
            // 만약에 범위를 벗어나는게 있다면 한쪽만 검사
            if (lt >= 0 && rt < roomCount) {
                // 둘 다 1이면 x
                if (copyGoldArr[rt] == 1 && copyGoldArr[lt] == 1 ) return false;
                else if (copyGoldArr[rt] == 1) {
                    copyGoldArr[rt] = 0; lt = rt; tempGoldCount--;
                }    
                else if (copyGoldArr[lt] == 1) {
                    copyGoldArr[lt] = 0; rt = lt; tempGoldCount--;
                } 
            }
            else if (lt < 0 ) {
                // 오른쪽만 검사
                if (copyGoldArr[rt] == 1) {
                    copyGoldArr[rt] = 0;
                    tempGoldCount--;
                    lt = rt;
                }
            } else if (rt >= roomCount) {
                // 왼쪽만 검사
                if ( copyGoldArr[lt] == 1) {
                    copyGoldArr[lt] = 0;
                    tempGoldCount--;
                    rt = lt;
                }
            }
        }
        
        return true;
    }
}
