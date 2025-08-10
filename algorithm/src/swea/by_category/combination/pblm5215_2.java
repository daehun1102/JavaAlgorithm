package swea.by_category.combination;
import java.io.*;
import java.util.StringTokenizer;

/**
 * 1. 테스트 케이스 T를 입력받는다.
 * 2. 각 케이스마다,
 *    2-1. 재료 개수와 제한 칼로리를 입력받고,
 *    2-2. N개의 점수, 칼로리를 읽는다.
 * 3. 부분집합으로 재료를 선택한다.
 *    3-2. 기저조건 : 요소 개수만큼 만들어졌을때 맛점수, 칼로리 합산, 최대 맛점수 갱신
 * 4. 케이스별로 가능한 최대 점수를 출력한다.
 */


public class pblm5215_2 {
  
    static BufferedReader br; 
    static StringTokenizer st;
    static StringBuilder sb;
    static int testCaseNum;

    static int foodCount, calorieLimit;
    static int[][] foodInfoArray;
    static boolean[] isSelected;

    static int SCORE = 0, CALORIE = 1;
    static int maxScore = 0;

    // 부분집합 구현
    public static void powerSet(int selectCount) {
        
        // 3-2. 기저조건 : 요소 개수만큼 만들어졌을때 맛점수, 칼로리 합산, 최대 맛점수 갱신
        if (selectCount == foodCount) {
            int tmpSumScore = 0;
            int tmpSumCalorie = 0;

            for (int index = 0; index < foodCount; index++) {
                if (isSelected[index]){
                    tmpSumCalorie += foodInfoArray[index][CALORIE];
                    tmpSumScore += foodInfoArray[index][SCORE];
                }
            }

            if (tmpSumCalorie <= calorieLimit) {
                if(tmpSumScore > maxScore) {
                    maxScore = tmpSumScore;
                }
            }
            return;
        }

        // 선택 했을때
        isSelected[selectCount] = true; 
        powerSet(selectCount+1);

        // 선택 안했을때
        isSelected[selectCount] = false;
        powerSet(selectCount+1);
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
            isSelected = new boolean[foodCount];

            // 3. 부분집합으로 재료를 선택한다.
            powerSet(0);

            // 4. 가장 높은 점수를 출력한다.
            sb.append("#").append(testCase).append(" ").append(maxScore).append("\n");
        }
        System.out.println(sb);
    }
}
