package boj.by_category.permutation;
import java.util.Scanner;

// 1. 요소개수 N, 뽑아야하는 수 M 을 받는다.
// 2. 순열 함수를 구현한다.
//  2-1. 기저조건 :뽑아야하는 숫자가 전부 뽑혔을때.
//  2-2. 요소들을 돌면서 (요소는 연속적이 므로 인덱스로 한다.) 요소를 선택하고 다음 자리 올 숫자를 찾으러 간다.
public class pblm15650 {
  static int elementNum, selectLimit;
  static int[] selectedArray; 
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) {
    // 1. 요소개수 N, 뽑아야하는 수 M 을 받는다.
    Scanner sc = new Scanner(System.in);
    elementNum = sc.nextInt();
    selectLimit = sc.nextInt();

    selectedArray = new int[selectLimit];
    myPermutation(0, 0); 
    System.out.print(sb);
  }

  // 2. 순열 함수를 구현한다.
  static void myPermutation(int startIndex, int count) {
    // 순열은 기저조건 1개이다.
    //  2-1. 기저조건 :뽑아야하는 숫자가 전부 뽑혔을때. 
    if (count == selectLimit) {
      for (int selectedIndex = 0; selectedIndex < selectLimit; selectedIndex++) {
        if (selectedIndex > 0) sb.append(' ');
        sb.append(selectedArray[selectedIndex]);
      }
      sb.append('\n');
      return;
    }
    //  2-2. 요소들을 돌면서 (요소는 연속적이 므로 인덱스로 한다.) 요소를 선택하고 다음 자리 올 숫자를 찾으러 간다.
    for (int curremtIndex = startIndex + 1; curremtIndex <= elementNum; curremtIndex++) {
      selectedArray[count] = curremtIndex;       
      myPermutation(curremtIndex, count + 1);   
    }
  }
}
