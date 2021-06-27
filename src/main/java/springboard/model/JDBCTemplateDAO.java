package springboard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import model.JdbcTemplateConst;



/*
JDBCTemplate 관련 주요메소드

Object queryForObject(String sql, RowMapper rm)
	: 하나의 레코드나 결과값을 반환하는 select 계열의 쿼리문을
	실행할 때 사용한다.

Object queryForObject(String sql, Object[] args, RowMapper rm)
	: 인파라미터가 있고, 하나의 레코드를 반환하는 select계열의
	쿼리문 실행에 사용한다.

List query(String sql, RowMapper rm)
	: 여러개의 레코드를 반환하는 select계열의 쿼리문인 경우 사용
	
List query(String sql, Object[] args, RowMapper rm)
	: 인파라미터를 가진 여러개의 레코드를 반환하는 select계열의
	쿼리문인 경우 사용한다.

 */
public class JDBCTemplateDAO {

	/*
	컨트롤러에서 @Autowired를 통해 자동 주입 받았던 빈을 정적변수인
	JdbcTemplateConst.template에 값을 할당하였으므로, DB연결정보를
	DAO에서 바로 사용할 수 있다.
	 */
	JdbcTemplate template;

	public JDBCTemplateDAO() {
		this.template = JdbcTemplateConst.template;
		System.out.println("JDBCTemplateDAO() 생성자 호출");
	}
	
	
	public void close() {
		/*
		JdbcTemplate에서는 자원해제를 하지 않는다
		Spring 설정파일에서 빈을 생성하므로 자원을 해제하면
		다시 new를 통해 생성해야 하므로 자원해제를 사용하지않는다.
		 */
		
	
	}
	
	// 게시물 수 카운트
	public int getTotalCount(Map<String, Object> map) {
		
		String sql = " select count(*) from springboard ";
		
		if(map.get("Word")!=null) {
			sql += " where "+map.get("Column")+" "
				+ "   like '%"+map.get("Word")+"%' ";
		}
		
		// 쿼리문에서 count(*)를 통해 반환되는 값을 정수형태로 가져온다.
		return template.queryForObject(sql, Integer.class); 
		
	}
	
	// 게시판 리스트 가져오기(페이지 처리 없음)
	public ArrayList<SpringBbsDTO> list(Map<String, Object> map){
		
		String sql =" "
				+ " select * from springboard ";
		
		if(map.get("Word")!=null) {
			sql += " where "+map.get("Column")+" "
					+ "   like '%"+map.get("Word")+"%' ";
		}
		sql += " order by idx desc ";
		
		/*
		RowMapper가 select를 통해 얻어온 ResultSet을 DTO객체에 
		저장하고, List 컬렉션에 적재하여 반환한다. 그러므로 DAO에서
		개발자가 반복적으로 하던 작업을 자동으로 처리해준다.
		 */
		return (ArrayList<SpringBbsDTO>)template.query(sql, new BeanPropertyRowMapper<SpringBbsDTO>(SpringBbsDTO.class));
		
		
		
		
	}
	
	
	
	public void write(final SpringBbsDTO springBbsDTO) {
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

				String sql = " insert into springboard ( "
						+ " idx, name, title, contents, hits "
						+ " ,bgroup , bstep, bindent, pass ) "
						+ " values ("
						+ " springboard_seq.nextval,? ,? ,? ,0 ,"
						+ " springboard_seq.nextval, 0, 0, ? ) ";
				
				PreparedStatement psmt =
						con.prepareStatement(sql);
				
				psmt.setString(1, springBbsDTO.getName());
				psmt.setString(2, springBbsDTO.getTitle());
				psmt.setString(3, springBbsDTO.getContents());
				psmt.setString(4, springBbsDTO.getPass());
				
				
				return psmt;
			}
		});
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
