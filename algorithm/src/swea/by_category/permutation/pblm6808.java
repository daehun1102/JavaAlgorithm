package swea.by_category.permutation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 1. 테스트 케이스 개수를 받는다.
// 2. 테스트 케이스 마다 규영이 배열을 생성한다.
// 3. 순열 구현한다. (선택은 항상 9)
//  3-1.기저조건 : 9장 뽑았을때 점수 계산
// 4. 점수계산 함수를 구현한다. 
//  4-1. 각각의 총점을 구한다.
//  4-2. 규영이가 이긴횟수, 진횟수를 구한다.
// 5. 결과를 출력한다.
public class pblm6808 {
  static int testCaseNum;
  static BufferedReader br;
  static StringTokenizer st;
  static int[] gyuyoungArray;
  static int[] inyoungArray;
  static boolean[] numArray;

  static int[] permutationArray;
  static boolean[] usedArray;

  static int winCount, loseCount;


  public static void main(String[] args) throws IOException{
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine().trim());
    // 1. 테스트 케이스 개수를 받는다. 
    testCaseNum = Integer.parseInt(st.nextToken());

    // 2. 테스트 케이스 마다 규영이 배열을 생성한다.
    for (int testCase = 1; testCase < testCaseNum+1; testCase++) {
      gyuyoungArray = new int[9];
      inyoungArray = new int[9];
      permutationArray = new int[9];
      usedArray = new boolean[9];
      numArray = new boolean[19];
      winCount = 0;
      loseCount = 0;
      st = new StringTokenizer(br.readLine().trim());
      for (int numIndex = 0; numIndex < 9; numIndex++) {
        int tmpCard = Integer.parseInt(st.nextToken());
        gyuyoungArray[numIndex] = tmpCard;
        numArray[tmpCard] = true;
      }
      int idx = 0;
      for (int number = 1; number <= 18; number++) {
          if (!numArray[number]) inyoungArray[idx++] = number;
      }
      permutation(0);
      System.out.println("#" + testCase + " " + winCount + " " + loseCount);
      
    }
  }

  // 3. 순열 구현한다. (선택은 항상 9)
  static void permutation(int numCount) {
      // 3-1.기저조건 : 9장 뽑았을때 점수 계산
      if (numCount == 9) {
          evaluate(); 
          return;
      }
      for (int numIndex = 0; numIndex < 9; numIndex++) {
          if (usedArray[numIndex]) continue;
          usedArray[numIndex] = true;
          permutationArray[numCount] = inyoungArray[numIndex];
          permutation(numCount + 1);
          usedArray[numIndex] = false;
      }
  }

  // 4. 점수계산 함수를 구현한다. 
  static void evaluate() {
      int gyuScore = 0, inScore = 0;
      //  4-1. 각각의 총점을 구한다.
      for (int cardIndex = 0; cardIndex < 9; cardIndex++) {
          int gyuCard = gyuyoungArray[cardIndex];
          int inCard = permutationArray[cardIndex];
          int sum = gyuCard + inCard;
          if (gyuCard > inCard) gyuScore += sum;
          else inScore += sum;
      }

      //  4-2. 규영이가 이긴횟수, 진횟수를 구한다.
      if (gyuScore > inScore) {
        winCount++;
      }  else {
        loseCount++;
      }
  }

}
