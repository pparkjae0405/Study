# 13안

Client
```
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
    		String msg, i1, i2 ,i3 ,i4, d1, u1, u2, u3;
    		String[] i, d, u;
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
						// u1, u2, u3를 종합하여 저장할 배열
						u = new String[11];
						// 수정할 조건
						System.out.print("이동할 조건행 : ");
						u1 = scan.next();
						System.out.print("검색할 내용 : ");
						u2 = scan.next();
						System.out.print("수정할 내용 : ");
						u3 = scan.next();
						u[0] = u1 + " = " + "'" + u3 + "'" + " WHERE " + u1 + " = " + "'" + u2 + "'";
						bw.write(msg+"\n");
						bw.flush();
						// 저장한 배열 전송
						bw.write(u[0]+"\n");
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
```
Server
```
package CMP_S;

public class Server{
	public static void main(String[] args) {
		Thread server = new ServerThread();
		server.start();
	}
}
```
ServerThread
```
package CMP_S;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ServerThread extends Thread{
	public void run() {
    	// 서버소켓 정의
        ServerSocket serverSocket = null;
        // 받아올 소켓 정의
        Socket socket = null;
        // 읽어올 br, 쓸 bw 정의
        BufferedReader br = null;
        BufferedWriter bw = null;
    	// DB 클래스 객체화
        MariaDB test = new MariaDB();
        try {
            // server는 프로그램이 실행되면 포트 5001의 연결요청을 기다림
            serverSocket = new ServerSocket(5001);
            socket = serverSocket.accept();
            // 문장 형태의 문자열을 주고받을수 있도록 셋팅.
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // server는 client가 연결되면 "연결되었습니다." 라고 메시지 전송.
            bw.write("연결되었습니다.\n");
            bw.flush();
            String msg, i1,i2,i3,i4, d1, u1, u2, u3;
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
                		// 
                		u1 = br.readLine();
                		u2 = br.readLine();
                		u3 = br.readLine();
                		// DB 클래스 호출하여 수행
                		test.update(u1, u2, u3);
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
                System.out.println(getTime() +" "+ msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
	//클라이언트에서 보내준 키보드 값 앞에 시간값 추가
	static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[MM월 dd일 hh:mm]");
        return f.format(new Date());
    }
}
```
MariaDB
```
package CMP_S;

import java.sql.*;

public class MariaDB {
	//DB 접속값 선언
	private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
	private static final String DB_URL = "jdbc:mariadb://127.0.0.1:3306/tcp";
	Connection conn = null;
	// SQL문 실행 / 결과값 선언
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// cr = Server- DB연결 결과 / sr = select 결과 / 
	// ir = insert 결과 / dr = delete 결과 / ur = update 결과
	String cr, sr, ir, dr, ur;
	// DB 조회 결과
	String[] b;
	// 출력할 횟수
	public int r;
	// 연결 명령어 및 DB연결 결과 저장
	public String connectDB() {
		try {
			Class.forName(DB_DRIVER_CLASS);
			conn = DriverManager.getConnection(DB_URL, "root", "password");
			// 연결성공			
			cr = "DB 연결 성공 \n";
			r = 1;
		// 드라이브 로딩 실패
		} catch (ClassNotFoundException e) {
			cr = "드라이브 로딩 실패 \n";
			r = 1;
		// DB 연결 실패
		} catch (SQLException e) {
			cr = "DB 연결 실패 \n";
			r = 1;
		}
		return cr;
	}
	public void close() {
		if( rs != null && pstmt != null && conn != null) {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// 조회 명령어 수행 및 DB 조회 결과 저장
	public String[] select(){
		try {
			// connectDB 호출
			connectDB();
			//배열 크기 설정;
			b = new String[7];
			String sql = " select * from tcp; ";
			// SQL문을 데이터베이스에 보내기위한 stmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			// SQL실행
			rs = pstmt.executeQuery();
			// 조회 성공
			sr = "1";
			r = 0;
			String 이름, 생년월일, 전화번호, 메모;
			while (rs.next()) { 
				이름 = rs.getString(1);
				생년월일 = rs.getString(2);
				전화번호 = rs.getString(3);
				메모 = rs.getString(4);
				b[r] = 이름 + " " + 생년월일 + " " + 전화번호 + " " + 메모;
				r++;
			}
		// SQL문에 이상이 있을 때
		}catch (SQLException e) {
			//Database 연결중 에러가 발생 했습니다.
			sr = "2";
			r = 1;
		}finally {
			this.close();
			}
		return b;
	}
	// 삽입 명령어 수행
	public void insert(String i1, String i2, String i3, String i4) {
		try {
			// 삽입 성공
			ir = "1";
			// connectDB 호출
			connectDB();
			String sql = " insert into tcp values(?,?,?,?); ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, i1);
			pstmt.setString(2, i2);
			pstmt.setString(3, i3);
			pstmt.setString(4, i4);
			rs = pstmt.executeQuery();
			
			r = 1;
		// SQL문에 이상이 있을 때	
		}catch(SQLException e) {
			//연결 에러
			ir = "2";
			r = 1;
		}finally {
			this.close();
			}
	}
	// 삭제 명령어 수행
	public void delete(String d1) {
		try {
			// 삭제 성공
			dr = "1";
			// connectDB 호출
			connectDB();
			String sql = " delete from tcp where 전화번호 = ?; ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, d1);
			int search = pstmt.executeUpdate();
			// 값이 없을 때
			if(search == 0) dr = "3";
			r = 1;
		// SQL문에 이상이 있을 때
		}catch(SQLException e) {
			dr = "2";
			r = 1;
		}finally {
			this.close();
			}
	}
	// 수정 명령어 수행
	public void update(String u1, String u2, String u3) {
		try {
			// 수정 성공
			ur = "1";
			// connectDB 호출
			connectDB();
			String sql = " update tcp set ? = ? where ? = ?; ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u1);
			pstmt.setString(2, u2);
			pstmt.setString(3, u3);
			int search = pstmt.executeUpdate();
			// 값이 없을 때
			if(search == 0) ur = "3";
			r = 1;
		// SQL문에 이상이 있을 때
		}catch(SQLException e) {
			ur = "2";
			r = 1;
		}finally {
			this.close();
			}
	}
}
```