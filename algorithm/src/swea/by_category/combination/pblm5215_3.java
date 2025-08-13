package swea.by_category.combination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 1. 테스트케이스를 받는다.
 * 2. 재료의 개수, 칼로리 제한을 받는다.
 * 3. 재료의 개수만큼 재료의 맛, 재료의 칼로리를 받는다.
 * 4. NextPermutation 으로 조합을 구현한다.
 *  4-1. 재료의 개수만큼 뒤에서 부터 1을 채우면서 조합배열을 만들어본다. (1~N 까지 채우기 : 조합배열)
 *  4-2. 조합 배열을 NextPermutation 에 넣어보면서 경우의 수를 전부 구한다.
 *  4-3. 경우의수가 나오면 칼로리 체킹 해보고 맛점수 최대값인지 확인해본다. 
 * 5.  NextPermutation 을 구현한다.
 *  5-1. 오름차순으로 정렬된 배열을 NextPermutation 으로 만들어주는 함수. (boolean 되면 true 아님 false)
 */
public class pblm5215_3 {
    static StringTokenizer st;
    static BufferedReader br;
    static StringBuilder sb;
    
    static int[][] foodArray; 
    static int foodNum;
    static final int TASTE = 0, CALORIE = 1;

    static int[] combinationArray;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        

        // 1. 테스트케이스를 받는다.
        int testCaseNum = Integer.parseInt(st.nextToken());        
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            sb = new StringBuilder();
            int bestScore = 0;
            st = new StringTokenizer(br.readLine());

            // 2. 재료의 개수, 칼로리 제한을 받는다.
            foodNum = Integer.parseInt(st.nextToken());        
            foodArray = new int[2][foodNum];
            int limitCalorie = Integer.parseInt(st.nextToken());
            
            // 3. 재료의 개수만큼 재료의 맛, 재료의 칼로리를 받는다.
            for (int foodIndex = 0; foodIndex < foodNum ; foodIndex++) {
                st = new StringTokenizer(br.readLine());
                foodArray[TASTE][foodIndex] = Integer.parseInt(st.nextToken());
                foodArray[CALORIE][foodIndex] = Integer.parseInt(st.nextToken());
            }
            
            // 4. NextPermutation 으로 조합을 구현한다.
            for (int selectIndex = foodNum -1; selectIndex >= 0 ; selectIndex--) {
                combinationArray = new int[foodNum];
                for(int index = selectIndex ; index <= combinationArray.length -1 ; index++){
                    combinationArray[index] = 1;
                }
                // 4-2. 조합 배열을 NextPermutation 에 넣어보면서 경우의 수를 전부 구한다.
                do {
                    int tempCalorie = 0;
                    int tempTaste = 0;
                    for (int index = 0; index < combinationArray.length; index++) {
                        if (combinationArray[index] == 1) {
                            tempTaste += foodArray[TASTE][index];
                            tempCalorie += foodArray[CALORIE][index];
                        }
                    }
                    // 4-3. 경우의수가 나오면 칼로리 체킹 해보고 맛점수 최대값인지 확인해본다. 
                    if (tempCalorie <= limitCalorie){
                        bestScore = Math.max(tempTaste, bestScore);
                    }
                } while (getNext());
            }
            sb.append("#").append(testCase).append(" ").append(bestScore);
            System.out.println(sb);
        }
    }

    // 4. NextPermutation 으로 조합을 구현한다.
    public static boolean getNext() {
        int tail = foodNum - 1;
        int highestElementIndex = tail;

        // 꼭대기 찾는다.
        while ( highestElementIndex >0 && combinationArray[highestElementIndex-1] >= combinationArray[highestElementIndex] ) {
            highestElementIndex--;
        }
        if (highestElementIndex == 0) {
            return false;
        }

        // 꼭대기 바로 앞에 인덱스 요소, 꼭대기 바로 앞에 인덱스 요소 뒤 배열에서 뒤에서 부터 돌다가 큰값 만난것(약간 큰값) 바꾼다. 
        int frontOfHighestElementIndex = highestElementIndex -1;
        int fairIndex = tail;
        while (combinationArray[frontOfHighestElementIndex] >= combinationArray[fairIndex]) {
            fairIndex--;
        }
        swap(fairIndex,frontOfHighestElementIndex );

        // 꼭대기 뒷 배열을 오름차순으로 변경시킨다. 이미 내림차순이기 때문에 아래 로직이 작동한다.
        int swapIndex = tail;
        while (highestElementIndex < swapIndex) {
            swap(swapIndex,highestElementIndex);
            highestElementIndex++;
            swapIndex--;
        }
        return true;
    }

    public static void swap(int leftIndex, int rightIndex) {
		int temp = combinationArray[leftIndex];
		combinationArray[leftIndex] = combinationArray[rightIndex];
		combinationArray[rightIndex] = temp;
	}
}

