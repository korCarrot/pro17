package sec03.brd01;

import java.util.List;



public class BoardService {
	
	BoardDAO boardDAO;
	
	
	public BoardService() {
		System.out.println("BoardService 객체 생성");
		boardDAO=new BoardDAO();
	}

	
	List<ArticleVO> listArticles(){
		 
		return  boardDAO.selectAllArticles();
	 }
}
