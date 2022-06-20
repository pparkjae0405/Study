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
	public void update(String u1, String u2) {
		try {
			// 수정 성공
			ur = "1";
			// connectDB 호출
			connectDB();
			String sql = " update tcp set 메모 = ? where 전화번호 = ?; ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u2);
			pstmt.setString(2, u1);
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