package springboard.command;

import org.springframework.ui.Model;

// 게시판의 모든 Command클래스가 구현할 인터페이스 정의
public interface BbsCommandImpl {

	// 인터페이스에 정의한 모든 메소드는 public abstract 선언되어진다.
	void execute(Model model);
	
}
/*
게시판에서 구현될 모든 Command를 각각의 타입에 의해서가 아닌
해당 인터페이스 형으로 참조할 수 있으므로 관리가 수월해지게 된다.
*/
