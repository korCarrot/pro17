package sec03.brd01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private DataSource dataFactory;	
	
	public BoardDAO() {
		System.out.println("BoardDAO 객체 생성");

		try {
			
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
			
		} catch (Exception e) {
			System.out.println("MemberDAO 객체에서 DB 연결 관련 에러");
		}
		
	}
		
	//전체 글 가져오기
	List<ArticleVO> selectAllArticles(){
		
		List<ArticleVO> articlesList = new ArrayList<>();
		
		try {
			conn = dataFactory.getConnection();
			
			String query =	"select level,  articleNO,parentNO,title,content,id,writeDate "
			+ "from t_board "
			+ "start with parentno=0 "
			+ "connect by prior articleNo=parentno "
			+ "order SIBLINGS by articleno desc ";
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				int level =rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				
				ArticleVO article =new ArticleVO();
				
				article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNO(parentNO);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				articlesList.add(article);
			}			
			rs.close();
			pstmt.close();
			conn.close();			
		}catch(Exception e) {
			System.out.println("모든 게시판 글 가져오면서 예외 발생");
		}		
		return articlesList;
	} 
	
	
	

}
