package springboard.command;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;
import springboard.util.EnvFileReader;
import springboard.util.PagingUtil;

/*
BbsCommandImpl 인터페이스를 구현했으므로 execute()는 반드시
오버라이딩 해야한다. 또한 해당 객체는 부모타입인 BbsCommandImpl로
참조할 수 있다.
 */
public class ListCommand implements BbsCommandImpl{

	
	@Override
	public void execute(Model model) {
		
		System.out.println("ListCommand > execute() 호출");
		
		/*
		컨트롤러에서 인자로 전달한 model객체에는 request객체가
		저장되어있다. asMap()을 통해 Map컬렉션으로 변환한 후
		모든 요청을 얻어올 수 있다.
		 */
		Map<String, Object> paramMap = model.asMap();
		
		
		HttpServletRequest req =
				(HttpServletRequest)paramMap.get("req");
		
		// DAO객체 생성
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		
		// 검색어 처리
		String addQueryString="";
		String searchColumn = req.getParameter("searchColumn");
		String searchWord = req.getParameter("searchWord");
		
		if(searchWord!=null) {
			addQueryString = String.format("searchColumn=%s"
					+ "&searchWord=%s&", searchColumn,searchWord);
			
			paramMap.put("Column",searchColumn);
			paramMap.put("Word",searchWord);
		}
		
		// 게시물 수 카운트
		int totalRecordCount = dao.getTotalCount(paramMap);
		
		
		/********************* 페이징 처리 start ***********************/
		// Environment객체를 통한 properties 파일을 읽어온다.
		int pageSize = Integer.parseInt(EnvFileReader.getValue(
				"SpringBbsInit.properties","springBoard.pageSize"));
		
		int blockPage = Integer.parseInt(EnvFileReader.getValue(
				"SpringBbsInit.properties","springBoard.blockPage"));
				
		// 페이지수를 계산
		int totalPage =
				(int)Math.ceil((double)totalRecordCount/pageSize);
		
		// 현재페이지번호. 첫 진입일때는 무조건 1페이지로 지정
		int nowPage = req.getParameter("nowPage")==null ? 1:
			Integer.parseInt(req.getParameter("nowPage"));
		
		// 리스트에 출력할 게시물의 구간을 계산(select절의 between에 사용)
		int start = (nowPage-1)*pageSize +1;
		int end = nowPage*pageSize;
		
		paramMap.put("start", start);
		paramMap.put("end", end);
		
		/********************* 페이징 처리 end ***********************/

		
		// 목록에 출력할 레코드 가져오기 => 페이지 처리 X
		//ArrayList<SpringBbsDTO> listRows = dao.list(paramMap);
		
		// 페이지 처리 O
		ArrayList<SpringBbsDTO> listRows = dao.listPage(paramMap);
		
		// 가상번호 계산하여 부여하기
		int virtualNum = 0;
		int countNum = 0;
		
		for(SpringBbsDTO row : listRows) {
			// 전체게시물의 개수에서 하나씩 차감하면서 가상번호 부여(페이지X)
			//virtualNum = totalRecordCount --;

			// 페이지 O(현재 페이지에 따른 가상번호 계산)
			virtualNum = totalRecordCount - (((nowPage-1)*pageSize) + countNum++);
			
			// setter를 통해 저장
			row.setVirtualNum(virtualNum);
			
			// 답변글 출력시의 처리 부분
			String reSpace = "";
			if(row.getBindent()>0) {
				// bindent의 크기만큼 반복해서 공백 문자 추가
				for(int i =0; i<row.getBindent(); i++) {
					reSpace += "&nbsp;&nbsp";
					
				}
				
				// 제목 앞에 reply아이콘 및 공백 문자를 통한 들여쓰기 처리
				row.setTitle(reSpace
						+ "<img src='../images/re3.gif' >"
						+ row.getTitle());
			}
		}
		
		// 출력할 목록을 model에 저장	
		model.addAttribute("listRows",listRows);

		/*
		페이지 번호 출력 및 게시물 개수 저장
		 */
		String pagingImg = PagingUtil.pagingImg(totalRecordCount,
				pageSize, blockPage, nowPage, req.getContextPath()+
				"/board/list.do?"+addQueryString);
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("nowPage", nowPage);
		
		// JDBCTemplate에서는 자원반납을 하지 않는다.
		// dao.close();
		
	}
	
}
