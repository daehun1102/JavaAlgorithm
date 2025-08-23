package swea.by_category.twopointer;

import java.util.Scanner;

/**
 * 문제 개요
 * 1. 산이 주어지고
 * 2. 3개 이상의 산을 선택했을때 최고점이 있으면 그구간은 우뚝 선 산
 * 
 * 아이디어
 * 1. 투포인터
 * 2. 봉우리가 있으면 왼쪽 오르막, 오른쪽 내리막이 있고, 그 숫자를 곱한게,
 * 3. 그 봉우리에서 구할 수 있는 구간 개수
 *  
 * 
 * 문제 풀이
 * 1. 테스트케이스 개수를 받는다.
 * 2. 테스트케이스 숫자를 받고,
 * 3. 산 배열을 받는다.
 * 4. lt rt를 잡고 (lt 가 끝까지 갈때까지 계속 아래 반복)
 *  4-1 오르막 개수를 구한다.
 *  4-2 내리막 개수를 구한다.
 *  4-3 둘이 곱해서 우뚝선산 구간개수를 구한다.
 *  4-4 이걸 반복해서 구한다.
 */
public class pbml4796 {
    static int[] mountainArray;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseNum = sc.nextInt();
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            int mountainCount = sc.nextInt();

            mountainArray = new int[mountainCount];
            for (int mountainIndex = 0; mountainIndex < mountainCount; mountainIndex++) {
                mountainArray[mountainIndex] = sc.nextInt();
            }

            int answer = 0; 
            int leftIndex = 0;
            int rightIndex = 0;

            while (leftIndex < mountainCount - 1) {
                // 오르막 개수를 센다.
                int peakIndex = leftIndex;
                while (peakIndex < mountainCount - 1 && mountainArray[peakIndex] < mountainArray[peakIndex + 1]) {
                    peakIndex++;
                }
                int upLength = peakIndex - leftIndex;

                // 내리막 개수를 센다
                rightIndex = peakIndex;
                while (rightIndex < mountainCount - 1 && mountainArray[rightIndex] > mountainArray[rightIndex + 1]) {
                    rightIndex++;
                }
                int downLength = rightIndex - peakIndex;

                // 구간개수를 센다.
                if (upLength > 0 && downLength > 0) {
                    answer += upLength * downLength;
                }

                leftIndex = rightIndex;
            }
            System.out.println("#" + testCase + " " + answer);
        }
        sc.close();
    }
}
