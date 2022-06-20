package CMP_S;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ServerThread extends Thread{
	private Socket TSocket;
	private int TCount;
	public void run() {
		// 읽어올 br, 쓸 bw 정의
		BufferedReader br = null;
		BufferedWriter bw = null;
		// DB 클래스 객체화
		MariaDB test = new MariaDB();
		try {
			// 문장 형태의 문자열을 주고받을수 있도록 셋팅.
			br = new BufferedReader(new InputStreamReader(TSocket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(TSocket.getOutputStream()));
			// server는 client가 연결되면 "연결되었습니다." 라고 메시지 전송.
			bw.write(TCount + "번 유저 연결되었습니다."+"\n");
			bw.flush();
			String msg, i1,i2,i3,i4, d1, u1, u2;
			// 계속해서 client가 보내는 메시지를 수신,
			while(true) {
				msg = br.readLine();
				switch(msg) {
					case "select":
						// DB 클래스 호출하여 수행
						test.select();
						// 연결 결과값 전송
						bw.write(test.cr);
						bw.flush();
						// 반복횟수 전송
						// 클라이언트에서 읽을 때 readLine으로 구분짓기 위해 String값으로 변환하여 보냄
						bw.write(Integer.toString(test.r)+"\n");
						bw.flush();
						// 성공이면 저장한 배열만큼 전송
						if(test.sr == "1") {
							for(int j=0;j<test.r;j++) {
								bw.write(test.b[j] + "\n");
							}
							bw.flush();
							// 실패면 에러메세지 전송
						}else if(test.sr == "2") {
							bw.write("Database 연결중 에러가 발생 했습니다." + "\n");
							bw.flush();
						}
						break;
					case "insert":
						// 키보드 값이 insert일 때 i1~i4로 저장한 배열 값 읽어올 ri 선언
						i1 = br.readLine();
						i2 = br.readLine();
						i3 = br.readLine();
						i4 = br.readLine();
						// DB 클래스 호출하여 수행
						test.insert(i1, i2, i3, i4);
						// 연결 결과값 전송
						bw.write(test.cr);
						bw.flush();
						// 반복횟수 전송
						bw.write(Integer.toString(test.r)+"\n");
						bw.flush();
						// 성공메세지 전송
						if(test.ir == "1") {
							bw.write( "삽입 성공"+ "\n");
							bw.flush();
							// 실패면 예외메세지 전송
						}else if(test.ir == "2") {
							bw.write("삽입 실패(조건/자료형/크기를 확인하세요)" + "\n");
							bw.flush();
						}
						break;
					case "delete":
						// 키보드 값이 delete일 때 d1~d2로 저장한 배열 값 읽어올 rd 선언
						d1 = br.readLine();
						// DB 클래스 호출하여 수행
						test.delete(d1);
						// 연결 결과값 전송
						bw.write(test.cr);
						bw.flush();
						// 반복횟수 전송
						bw.write(Integer.toString(test.r)+"\n");
						bw.flush();
						// 성공메세지 전송
						if(test.dr == "1") {
							bw.write( "삭제 성공"+ "\n");
							bw.flush();
							// 예외1(조건실패) 메세지 전송
						}else if(test.dr == "2") {
							bw.write("삭제 실패(조건/자료형/크기를 확인하세요)" + "\n");
							bw.flush();
							// 예외2(값이 없음) 메세지 전송
						}else if(test.dr == "3") {
							bw.write("삭제 실패(조건을 충족하는 값이 없습니다)" + "\n");
							bw.flush();
						}
						break;
					case "update":
						u1 = br.readLine();
						u2 = br.readLine();
						// DB 클래스 호출하여 수행
						test.update(u1, u2);
						// 연결 결과값 전송
						bw.write(test.cr);
						bw.flush();
						// 반복횟수 전송
						bw.write(Integer.toString(test.r)+"\n");
						bw.flush();
						// 성공메세지 전송
						if(test.ur == "1") {
							bw.write("수정 성공" + "\n");
							bw.flush();
							// 예외1(조건실패) 메세지 전송
						}else if(test.ur == "2") {
							bw.write("수정 실패(조건/자료형/크기를 확인하세요)" + "\n");
							bw.flush();
							// 예외2(값이 없음) 메세지 전송
						}else if(test.ur == "3") {
							bw.write("수정 실패(조건을 충족하는 값이 없습니다)" + "\n");
							bw.flush();
						}
						break;
					case "exit":
						br.close();
						bw.close();
						TSocket.close();
						break;
					default:
						// 이외의 다른 값이 들어올 경우
						bw.write("그런 명령어는 없습니다. 아래 명령 중 골라주세요."+"\n");
						bw.flush();
						bw.write(Integer.toString(1)+"\n");
						bw.flush();
						bw.write("select, insert, delete, update, exit"+"\n");
						bw.flush();
						break;
				}
				System.out.println(getTime() +TCount + "번 유저 : "+ msg);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println(TCount + "번 유저가 나갔습니다.");
		}

	}
	//클라이언트에서 보내준 키보드 값 앞에 시간값 추가
	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[MM월 dd일 hh:mm]");
		return f.format(new Date());
	}
	public void setSocket(Socket socket) {
		TSocket = socket;
	}
	public void setCount(int count) {
		TCount = count;
	}
}