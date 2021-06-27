package springboard.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBbsDTO;

public class WriteActionCommand implements BbsCommandImpl{

	@Override
	public void execute(Model model) {

		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		SpringBbsDTO springBbsDTO = (SpringBbsDTO)paramMap.get("springBbsDTO");
		
		System.out.println("springBbsDTO.title = "+springBbsDTO.getTitle());
		
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		
		dao.write(springBbsDTO);
		dao.close();
		
	}
	
	
	
	
}
