package swea.by_category;

import java.util.Scanner;

/**
 * 문제개요
 * 1. 숫자 들어오면 N+1, sqrt(N) 둘중에 하나 해서 2만들기
 * 
 * 아이디어
 * 1. 제곱근을 무조건 우선시 해야함
 * 2. 숫자가 들어오면 그 숫자보다 약간 큰 제곱수로 이동
 * 3. 제곱근이면 바로 제곱근 구하기
 * 4. long 으로 받기 , 제약사항 고려
 * 
 * 문제풀이
 * 1. 테스트케이스를 받는다.
 * 2. 숫자를 받는다.
 * 3. 숫자가 2가 될때까지
 *  3-1. 숫자가 제곱수라면 제곱근 시켜주고,
 *  3-2. 그게 아니라면 가장 가까운 제곱근으로 이동
 */
public class pblm6782 {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int testCaseNum = sc.nextInt();
        
        for (int testCase = 1; testCase <= testCaseNum; testCase++) {
            // 2. 숫자를 받는다.
            long inputNumber = sc.nextLong();
            int answer = 0;
            // 3. 숫자가 2가 될때까지
            while (inputNumber != 2) {
                // 3-1. 숫자가 제곱수라면 제곱근 시켜주고,
                long sqrtNum = (long) Math.sqrt(inputNumber);
                if (inputNumber == sqrtNum*sqrtNum ) {
                    inputNumber = sqrtNum;
                    answer++;
                }

                // 3-2. 그게 아니라면 가장 가까운 제곱근으로 이동
                else {
                    answer += (sqrtNum+1) * (sqrtNum+1) - inputNumber + 1;  
                    inputNumber = sqrtNum+1;
                }
            }   

            System.out.println("#" + testCase + " " + answer);
        }
    }
}
