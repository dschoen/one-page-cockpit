package de.dschoen.opco.demo;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dschoen.opco.board.Board;
import de.dschoen.opco.board.BoardColumn;
import de.dschoen.opco.board.BoardRow;
import de.dschoen.opco.board.Card;
import de.dschoen.opco.board.CardDAOImpl;
import de.dschoen.opco.board.BoardDAO;
import de.dschoen.opco.board.CardDAO;
import de.dschoen.opco.user.UserDAO;
import de.dschoen.opco.user.User;

@Service
public class DemoServiceImpl implements DemoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private CardDAO cardDAO;
		
	// ----------------------------------------------------------------------------
		
	@Override
	public void createDemoData() {
		//check if Demo User exists, else create
		if (userDAO.existsUser("demo")) {
			logger.info("User 'demo' already exists.");
			return;
		} 

		logger.info("User 'demo' does not exist, therefore default 'demo'-'demo' is created.");
		
		User demo = new User();
		demo.setUsername("demo");
		demo.setPassword("demo");
		demo.setFirstname("Nobby");
		demo.setLastname("Nobs");
		demo.setEmail("none@mail.com");
		
		
		Board board = new Board("DemoBoard");
		
		Collection<BoardRow> rows = board.getBoardRows();
		rows.add(new BoardRow("high"));
		rows.add(new BoardRow("medium"));
		rows.add(new BoardRow("low"));		
		board.setBoardRows(rows);
		
		Collection<BoardColumn> cols = board.getBoardColumns();
		cols.add(new BoardColumn("Todo"));
		cols.add(new BoardColumn("inProgress"));
		cols.add(new BoardColumn("Backlog"));
		
		demo.getBoards().add(board);
		
		// create Cards
		Card card = new Card();
		card.setBoard(board);
		card.setBoardColumn(board.getBoardColumns().iterator().next());
		card.setBoardRow(board.getBoardRows().iterator().next());
		card.setTitle("Demo Karte");
		card.setText("Demotext");
		card.setEffort("normal");
		
		board.getCards().add(card);
		
		userDAO.addUser(demo);
		
	}
}
