import java.util.Scanner;

public class pblm15649 {
  static int N, M;
  static int[] numbers;
  static boolean[] isSelected;
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    N = sc.nextInt();
    M = sc.nextInt();
    
    
    numbers = new int[M];
    isSelected = new boolean[N + 1];
    myPermutation(0);
    System.out.print(sb);
  }

  static void myPermutation(int cnt) {
    if (cnt == M) {
      for (int i = 0; i < M; i++) {
        if (i > 0) sb.append(' ');
        sb.append(numbers[i]);
      }
      sb.append('\n');
      return;
    }

    // 모든 숫자 시도
    for (int currentNumber = 1; currentNumber <= N; currentNumber++) {
      if (isSelected[currentNumber]) continue; // 이미 들렀다면 스킵
      isSelected[currentNumber] = true; // 방문한 숫자 체크
      numbers[cnt] = currentNumber; // 해당 자리에 수 저장
      myPermutation(cnt + 1); // 다음 자리 진행
      isSelected[currentNumber] = false; // 다른 선택지에서 쓸수 있게 다시 false
    }
  }
}
