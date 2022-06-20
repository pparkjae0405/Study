package CMP_C;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	public static void main(String[] args) {
		// 읽어올 br, 쓸 bw 정의
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String serverIp = "127.0.0.1";
			System.out.println("서버와 연결중..");
			//소켓 생성 및 연결 요청
			Socket socket = new Socket(serverIp, 5001);
			// 문장 형태의 문자열을 주고받을수 있도록 셋팅.
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			// client는 연결이 성공하면 서버가 보낸 메시지를 모니터에 출력.
			System.out.println(br.readLine());
			String msg, i1, i2 ,i3 ,i4, d1, u1, u2;
			// 사용자로부터 메시지를 입력 받을때마다 서버에 전달.
			while (true) {
				System.out.println("##############################################");
				System.out.print("명령어를 입력하세요 : ");
				// 키보드 값 msg 저장
				Scanner scan = new Scanner(System.in);
				msg = scan.nextLine();
				// 조건에 따라 키보드값 서버로 전송
				switch(msg) {
					//case "select" 추가
					case "select":
						bw.write(msg + "\n");
						bw.flush();
						break;
					case "insert":
						// 삽입할 조건
						System.out.print("이름 : ");
						i1 = scan.next();
						System.out.print("생년월일 : ");
						i2 = scan.next();
						System.out.print("전화번호 : ");
						i3 = scan.next();
						System.out.print("메모 : ");
						i4 = scan.next();
						bw.write(msg +"\n");
						bw.flush();
						// 저장한 배열 전송
						bw.write(i1+ "\n");
						bw.write(i2+ "\n");
						bw.write(i3+ "\n");
						bw.write(i4+ "\n");
						bw.flush();
						break;
					case "delete":
						// 삭제할 조건
						System.out.print("삭제하려는 사람의 전화번호 : ");
						d1 = scan.next();
						bw.write(msg+"\n");
						bw.flush();
						// 저장한 배열 전송
						bw.write(d1+"\n");
						bw.flush();
						break;
					case "update":
						// 수정할 조건
						System.out.print("수정할 사람의 전화번호 : ");
						u1 = scan.next();
						System.out.print("수정할 메모 : ");
						u2 = scan.next();

						bw.write(msg+"\n");
						bw.flush();
						// 저장한 배열 전송
						bw.write(u1+ "\n");
						bw.write(u2+ "\n");
						bw.flush();
						break;
					case "exit":
						br.close();
						bw.close();
						socket.close();
					default:
						bw.write(msg + "\n");
						bw.flush();
						break;
				}
				// connectDB() DB연결 수행결과 출력
				System.out.println(br.readLine());
				int q;
				// test.r 반복횟수 수행결과 int형으로 변환
				int w = Integer.parseInt(br.readLine());
				// 반복횟수 에 따라 출력횟수 조절
				switch(w) {
					// select를 제외한 나머지는 한번만 받아와 출력
					case 1:
						System.out.println(br.readLine());
						break;
					// select 이면 저장된 배열만큼 받아와 출력
					default :
						for(q = 1; q <= w ; q++) {
							System.out.println(br.readLine());
						}
						break;
				}
			}
			// HOST를 찾을 수 없을 때 예외
		} catch (UnknownHostException e) {
			e.printStackTrace();
			// 입력 또는 출력 작업 실패 예외
		}catch (IOException e) {
			//e.printStackTrace();
			System.out.println("종료하였습니다.");
		}
	}
}