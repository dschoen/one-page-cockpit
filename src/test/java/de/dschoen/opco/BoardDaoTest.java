package de.dschoen.opco;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.dschoen.opco.board.Board;
import de.dschoen.opco.board.BoardColumn;
import de.dschoen.opco.board.BoardDao;
import de.dschoen.opco.board.BoardRow;
import de.dschoen.opco.board.Card;
import de.dschoen.opco.board.CardDao;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardDaoTest {

	@Autowired
	BoardDao dao;
	@Autowired
	CardDao cdao;
	
	
	@Test
	public void addAndGetBoardTest() {
		Board board = new Board();
		board.setName("JUNIT - Test - Board");		
		board = dao.addBoard(board);
		
		Board board2 = dao.getBoardById(board.getBoardId());
		
		assertEquals(board.getName(), board2.getName());
		
    }
	
	@Test
	public void updateBoardTest() {
		Board board = new Board();
		board.setName("JUNIT - Test - Board");
		board = dao.addBoard(board);
		
		board.setName("JUNIT-updateBoardTest-Update");		
		Board board2 = dao.updateBoard(board);
		
		assertEquals(board.getName(), board2.getName());
		    
    }
	
	@Test
	public void addBoardColumnTest() {
		Board board = new Board();
		board.setName("JUNIT-Test-Board");
		board = dao.addBoard(board);
		
		assertEquals(board.getBoardColumns().size(), 0);
		
		board.addBoardColumn(Util.columnDummy("JUNIT-addBoardColumnTest"));
		dao.updateBoard(board);
		
		Board board2 = dao.getBoardById(board.getBoardId());
		
		assertEquals(board2.getBoardColumns().size(), 1);
	}
	
	@Test
	public void deleteBoardColumnTest() {
		Board board = new Board();
		board.setName("JUNIT-Test-Board");
		board = dao.addBoard(board);
		
		BoardColumn col = Util.columnDummy("JUNIT-addBoardColumnTest");
		
		board.addBoardColumn(col);
		board = dao.updateBoard(board);
		
		assertEquals(1, board.getBoardColumns().size());
		
		board.removeBoardColumn(col);
		dao.updateBoard(board);
		
		Board board2 = dao.getBoardById(board.getBoardId());
		
		assertEquals(0, board2.getBoardColumns().size());		
	}
	
	
	@Test
	public void addBoardRowTest() {
		Board board = new Board();
		board.setName("JUNIT-Test-Board");
		board = dao.addBoard(board);
		
		assertEquals(board.getBoardRows().size(), 0);
		
		board.addBoardRow(Util.rowDummy("JUNIT-addBoardRowTest"));
		dao.updateBoard(board);
		
		Board board2 = dao.getBoardById(board.getBoardId());
		
		assertEquals(board2.getBoardRows().size(), 1);
	}
	
	
	@Test
	public void deleteBoardRowTest() {
		Board board = new Board();
		board.setName("JUNIT-Test-Board");
		board = dao.addBoard(board);
		
		BoardRow col = Util.rowDummy("JUNIT-addBoardRowTest");
		
		board.addBoardRow(col);
		board = dao.updateBoard(board);
		
		assertEquals(1, board.getBoardRows().size());
		
		board.removeBoardRow(col);
		dao.updateBoard(board);
		
		Board board2 = dao.getBoardById(board.getBoardId());
		
		assertEquals(0, board2.getBoardRows().size());		
	}
	
	
	
	
	@Test
	public void deleteBoardTest() {
		
		int b1 = dao.countBoards();
		int c1 = cdao.countCards();
		
		Board board 	= Util.boardDummy("JUNIT-deleteBoardTest");		
		BoardRow row 	= Util.rowDummy("JUNIT-deleteBoardTest");
		BoardColumn col = Util.columnDummy("JUNIT-deleteBoardTest");
		board = dao.addBoard(board);
		
		Card card = Util.cardDummy("JUNIT-deleteBoardTest");
		card.setBoard(board);
		card.setBoardColumn(col);
		card.setBoardRow(row);
		
		board.addCard(card);
		dao.updateBoard(board);
			
		int b2 = dao.countBoards();
		int c2 = cdao.countCards();
		
		assertTrue(b1 < b2);
		assertTrue(c1 < c2);
		
		dao.deleteBoard(board);
		
		int b3 = dao.countBoards();
		int c3 = cdao.countCards();
		
		assertTrue(b3 == b2);
		assertTrue(c3 == c2);
    }
	
	@After
	public void cleanUp() {
		
		//TODO remove all Boards
	}
	
}
