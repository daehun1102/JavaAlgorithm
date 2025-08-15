package swea.by_category.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 테스트 케이스 개수를 받는다.
 * 2. 주어지는 재료의 개수, 동시에 들어가면 안되는 재료 쌍 개수를 받는다.
 * 3. 다음 재료의 쌍 개수가 들어오면 배열에 저장한다.
 * 4. 조합을 구현한다.
 *  4-1-1. 기저조건1 : 뽑아야할 개수를 다 채운 경우엔
 *  4-1-2. 동시에 들어가면 안되는 요소가 있는지 체킹
 *  4-1-3. 없다면 cnt ++;
 *  4-2. 기저조건2 : 더이상 뽑을 요소가 없는 경우.
 * 5. 조합 결과에 동시에 들어가면 안되는 요소가 있는지 체킹하는 함수
 *  5-1. 5-1. 두개의 요소 들고 둘 다 존재할때 true
 *  */ 
public class pblm3421 {
    static BufferedReader br;
    static StringTokenizer st;

    static int testcaseNum;
    static int foodCount;
    static int dontTogetherCount;

    static int[] elementArray;
    static int[] selectedArray;

    static int[][] dontTogetherArray;
    static int answer;

    public static void main(String[] args) throws IOException {
        // 1. 테스트 케이스 개수를 받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        testcaseNum = Integer.parseInt(st.nextToken());
        
        // 2. 주어지는 재료의 개수, 동시에 들어가면 안되는 재료 쌍 개수를 받는다.
        for (int testcase = 1; testcase <= testcaseNum; testcase++) {
            st = new StringTokenizer(br.readLine().trim());
            foodCount = Integer.parseInt(st.nextToken());
            dontTogetherCount = Integer.parseInt(st.nextToken());
            answer = 0;

            elementArray = new int[foodCount+1];
            for (int food = 1; food <= foodCount; food++) {
                elementArray[food] = food;
            }

            dontTogetherArray = new int[dontTogetherCount][2];
            // 3. 다음 재료의 쌍 개수가 들어오면 배열에 저장한다.
            for (int dontTogeherIndex = 0; dontTogeherIndex < dontTogetherCount; dontTogeherIndex++) {
                st = new StringTokenizer(br.readLine().trim());
                dontTogetherArray[dontTogeherIndex][0] = Integer.parseInt(st.nextToken()); 
                dontTogetherArray[dontTogeherIndex][1] = Integer.parseInt(st.nextToken());        
            }
            // 조합으로 만들어볼 수제버거 다만들어보기
            for (int combCount = 0; combCount <= foodCount; combCount++) {
                if (combCount == 0 ) {
                    answer++;
                    continue;
                }
                selectedArray = new int[combCount];
                combination(1,0);
            }
            System.out.println("#" + testcase + " "+ answer );
        }
    }
    // 4. 조합을 구현한다.
    static void combination(int elementIndex, int selectedIndex ){
        // 4-1-1. 기저조건1 : 뽑아야할 개수를 다 채운 경우엔
        if (selectedIndex == selectedArray.length) {
            // 4-1-2. 동시에 들어가면 안되는 요소가 있는지 체킹
            if (!checking()) {
                // 4-1-3. 없다면 cnt ++;
                answer++;
            } 
            return;
        }
        // 4-2. 기저조건2 : 더이상 뽑을 요소가 없는 경우.
        if (elementIndex > foodCount) {
            return;
        }

        // 선택 했을때
        selectedArray[selectedIndex] = elementArray[elementIndex];
        selectedArray[selectedIndex] = elementArray[elementIndex];
        combination(elementIndex + 1, selectedIndex + 1);

        // 선택 안했을때
        selectedArray[selectedIndex] = -1;
        selectedArray[selectedIndex] = -1;
        combination(elementIndex + 1, selectedIndex);
    }
    // 5. 조합 결과에 동시에 들어가면 안되는 요소가 있는지 체킹하는 함수
    static boolean checking() {
        boolean[] picked = new boolean[foodCount + 1]; 
        for (int v : selectedArray) {
            if (v > 0) picked[v] = true; 
        }

        // 5-1. 두개의 요소 들고 둘 다 존재할때 true
        for (int index = 0; index < dontTogetherCount; index++) {
            int a = dontTogetherArray[index][0];
            int b = dontTogetherArray[index][1];
            if (picked[a] && picked[b]) {
                return true;   
            }
        }
        return false;      
    }
}
