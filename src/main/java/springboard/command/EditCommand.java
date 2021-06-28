package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;

public class EditCommand implements BbsCommandImpl{

	@Override
	public void execute(Model model) {

		// 파라미터 한번에 받기
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		
		// 일련번호를 파라미터로 받은 후
		String idx = req.getParameter("idx");
		
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		
		// 기존의 게시물을 얻어온다.
		SpringBbsDTO dto = dao.view(idx);
		model.addAttribute("viewRow",dto);
		
		// dao.close();
		
		
	}
}
