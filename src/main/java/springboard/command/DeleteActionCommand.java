package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;

public class DeleteActionCommand implements BbsCommandImpl{

	@Override
	public void execute(Model model) {
		
		// 요청 한번에 받기
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest)map.get("req");
		
		// 일련번호와 패스워드를 받아온 후
		String idx = req.getParameter("idx");
		String pass = req.getParameter("pass");
		
		// delete메소드로 전달
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		dao.delete(idx,pass);
		
	}
	
	
}
