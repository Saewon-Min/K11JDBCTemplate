package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;

public class ReplyCommand implements BbsCommandImpl{

	@Override
	public void execute(Model model) {

		// 요청 일괄적으로 받아오기
		Map<String, Object> map = model.asMap();
		
		HttpServletRequest req = (HttpServletRequest)map.get("req");
		
		String idx = req.getParameter("idx");
		
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		
		// 기존 게시물 가져오기
		SpringBbsDTO dto = dao.view(idx);
		// 제목에는 Reply라는 의미의 말머리를 추가한다.
		dto.setTitle("[RE]"+dto.getTitle());
		// 내용에는 원본글을 추가하고, 작성의 편의를 위해 약간의 줄바꿈을 한다.
		dto.setContents("\n\r\n\r---[원본글]---\n\r"+dto.getContents());
	
		
		model.addAttribute("replyRow",dto);
		
		// dao.close();
		
	}
	
}
