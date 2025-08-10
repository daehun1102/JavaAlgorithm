package swea.by_category.combination;
import java.io.*;
import java.util.StringTokenizer;

/**
 * 1. 테스트 케이스 T를 입력받는다.
 * 2. 각 케이스마다,
 *    2-1. 재료 개수와 제한 칼로리를 입력받고,
 *    2-2. N개의 점수, 칼로리를 읽는다.
 * 3. 조합으로 재료를 선택한다.
 *    3-2. 기저조건1 : 요소 개수만큼 선택 다했을때 맛점수, 칼로리 합산, 맛점수 갱신
 *    3-3. 기저조건2 : 재료를 더이상 뽑을게 없을때
 * 4. 케이스별로 가능한 최대 점수를 출력한다.
 */


public class pblm5215_1 {
  
    static BufferedReader br; 
    static StringTokenizer st;
    static StringBuilder sb;
    static int testCaseNum;

    static int foodCount, calorieLimit;
    static int[][] foodInfoArray;
    static int[][] selectedInfoArray;

    static int SCORE = 0, CALORIE = 1;
    static int maxScore = 0;

    // 조합 구현
    public static void combination(int elementIndex, int selectIndex) {
        
        // 3-2. 기저조건1 : 요소 개수만큼 선택 다했을때 맛점수, 칼로리 합산, 맛점수 갱신
        if (selectIndex == selectedInfoArray.length) {
            int tmpSumScore = 0;
            int tmpSumCalorie = 0;

            for (int index = 0; index < selectedInfoArray.length; index++) {
                tmpSumCalorie += selectedInfoArray[index][CALORIE];
                tmpSumScore += selectedInfoArray[index][SCORE];
            }

            if (tmpSumCalorie <= calorieLimit) {
                if(tmpSumScore > maxScore) {
                    maxScore = tmpSumScore;
                }
            }
            return;
        }
        // 3-3. 기저조건2 : 재료를 더이상 뽑을게 없을때
        if (elementIndex == foodCount) {
            return;
        }

        // 선택 했을때
        selectedInfoArray[selectIndex][SCORE] = foodInfoArray[elementIndex][SCORE];
        selectedInfoArray[selectIndex][CALORIE] = foodInfoArray[elementIndex][CALORIE];
        combination(elementIndex + 1, selectIndex + 1);

        // 선택 안했을때
        selectedInfoArray[selectIndex][SCORE] = -1;
        selectedInfoArray[selectIndex][CALORIE] = -1;
        combination(elementIndex + 1, selectIndex);
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        // 1. 테스트 케이스 개수를 입력받는다.
        testCaseNum = Integer.parseInt(br.readLine().trim());

        // 2. 각 테스트 케이스마다,
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {

            // 2-1. 재료 개수와 제한 칼로리를 입력받고,
            st = new StringTokenizer(br.readLine().trim());
            foodCount = Integer.parseInt(st.nextToken());
            calorieLimit = Integer.parseInt(st.nextToken());

            // 2-2. N개의 점수, 칼로리를 읽는다.
            foodInfoArray = new int[foodCount][2];
            for (int foodIndex = 0; foodIndex < foodCount; foodIndex++) {
                st = new StringTokenizer(br.readLine());
                foodInfoArray[foodIndex][SCORE] = Integer.parseInt(st.nextToken());
                foodInfoArray[foodIndex][CALORIE] = Integer.parseInt(st.nextToken());
            }

            maxScore = -1;
          
            // 3. 조합으로 재료를 선택한다.
            for (int combCount = 1; combCount <= foodCount; combCount++) {
                selectedInfoArray = new int[combCount][2];
                combination(0, 0);
            }

            // 4. 가장 높은 점수를 출력한다.
            sb.append("#").append(testCase).append(" ").append(maxScore).append("\n");
        }
        System.out.println(sb);
    }
}
