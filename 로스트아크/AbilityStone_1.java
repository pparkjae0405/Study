// 로스트아크 세공 한줄로 다 밀었을 때
// 10번 다 성공할때까지 걸리는 횟수
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException{
        // 1. 세공할 수 있는 칸 10개 생성
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean[] a = new boolean[10];
        // 2. 초기 확률 percent, random 값 r, 횟수 count를 선언하고 반복 시작
        int percent = 75;
        Random r = new Random();
        int count = 0;
        while(true) {
            for (int N = 0; N < 10; N++) {
                if (r.nextInt(100) <= percent) {
                    // 성공 시 배열을 성공으로 바꾸고 percent를 10 낮추는데
                    a[N] = true;
                    percent -= 10;
                    // percent가 25 아래라면 25로 만들기
                    if (percent < 25) percent = 25;
                } else {
                    //실패 시 배열을 실패로 바꾸고 percent를 10 올리는데
                    a[N] = false;
                    percent += 10;
                    // percent가 75 위라면 75로 만들기
                    if (percent > 75) percent = 75;
                }
            }
            // 3. 세공 완료 후 카운트를 증가시키고 배열이 전부 true라면 루프문 탈출
            count++;
            int successCount = 0;
            for(int i = 0; i < 10; i++){
                if(a[i] == true) successCount++;
            }
            if(successCount == 10) break;
        }

        // 4. 배열과 시도 횟수 출력
        for(int i = 0; i < 10; i++) {
            if (a[i] == true) {
                System.out.print("◆ ");
            } else {
                System.out.print("◇ ");
            }
        }
        System.out.println();
        System.out.println("목표치까지 걸린 횟수 : " + count);
    }
}