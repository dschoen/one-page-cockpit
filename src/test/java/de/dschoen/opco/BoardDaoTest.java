package de.dschoen.opco;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;
import de.dschoen.opco.board.model.Card;
import de.dschoen.opco.board.persist.BoardDao;
import de.dschoen.opco.board.persist.BoardDaoImpl;
import de.dschoen.opco.board.persist.CardDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardDaoTest {

	@Autowired
	BoardDao dao;
	@Autowired
	BoardDaoImpl daoImpl;
	@Autowired
	CardDao cdao;
	
	private final String MARKER 	= "<XX-JUNIT-Test-Board-XX>"; 
	private final String MARKER_COL = "<XX-JUNIT-Test-COL-XX>"; 
	private final String MARKER_ROW = "<XX-JUNIT-Test-ROW-XX>"; 
	
	
	@Test
	public void countBoardsTest() {
		int number = dao.countBoards();		
		assertTrue(number >= 0);
	}
	
	@Test
	public void insertBoardTest() {
		int count1 = dao.countBoards();
		daoImpl.insertBoard(Board.fromName(MARKER));
		int count2 = dao.countBoards();
		assertTrue(count1+1 == count2);	
	}
	
	@Test
	public void addBoartTest() {	
		Board board = dao.addBoard(Board.fromName(MARKER));			
		assertEquals(MARKER, board.getName());
    }
	
	@Test
	public void boardExistsByIdTest()	{		
		Board board = dao.addBoard(Board.fromName(MARKER));
		
		assertTrue(dao.boardExistsById(board.getBoardId()));
		assertFalse(dao.boardExistsById(999));
    }
	
	@Test
	public void getBoardByIdTest()	{		
		Board board = dao.addBoard(Board.fromName(MARKER));
		Board board2 = dao.getBoardById(board.getBoardId());
		
		assertEquals(board.getName(), board2.getName());
    }	
	
	@Test
	public void getBoardsTest()	{		
		int count = dao.countBoards();
		ArrayList<Board> boards = dao.getBoards();
		
		assertEquals(count, boards.size());
    }		
	
	@Test
	public void updateBoardTest() {
		Board board = dao.addBoard(Board.fromName(MARKER));		
		board.setName("JUNIT-updateBoardTest-Update");		
		Board board2 = dao.updateBoard(board);
		
		assertEquals(board.getName(), board2.getName());
    }
	
	@Test
	public void deleteBoardTest() {
		Board board = dao.addBoard(Board.fromName(MARKER));
		dao.deleteBoard(board);
		
		assertFalse(dao.boardExistsById(board.getBoardId()));
    }
		
	@Test
	public void addColumnTest() {
		Board board = dao.addBoard(Board.fromName(MARKER));		
		Column column = dao.addColumnToBoard(Column.fromName(MARKER_COL), board);

		assertEquals(MARKER_COL, column.getName());
		assertEquals(board.getBoardId(), column.getBoardId());
	}
	
	@Test
	public void updateColumnTest() {
		//TODO
		assertTrue(false);
	}
	
	@Test
	public void removeColumnTest() {
		//TODO
		assertTrue(false);
	}
	
	@Test
	public void getColumnByIdTest() {
		Board board = dao.addBoard(Board.fromName(MARKER));		
		Column column = dao.addColumnToBoard(Column.fromName(MARKER_COL), board);
		Column column2 = dao.getColumnById(column.getColumnId());
		
		assertEquals(column.getName(), column2.getName());
	}
	
	@Test
	public void getColumnsTest() {
		//TODO
		assertTrue(false);
	}
	
	@Test
	public void addRowTest() {
		Board board = dao.addBoard(Board.fromName(MARKER));		
		Row row = dao.addRowToBoard(Row.fromName(MARKER_ROW), board);

		assertEquals(MARKER_ROW, row.getName());
		assertEquals(board.getBoardId(), row.getBoardId());
	}
	
	@Test
	public void updateRowTest() {
		//TODO
		assertTrue(false);
	}
	
	@Test
	public void removeRowTest() {
		//TODO
		assertTrue(false);
	}
	
	@Test
	public void getRowByIdTest() {
		Board board = dao.addBoard(Board.fromName(MARKER));		
		Row row = dao.addRowToBoard(Row.fromName(MARKER_COL), board);
		Row row2 = dao.getRowById(row.getRowId());
		
		assertEquals(row.getName(), row2.getName());
	}
	
	@Test
	public void getRowsTest() {
		//TODO
		assertTrue(false);
	}
	
	@After
	public void cleanUp() {
		
		//TODO remove all Boards
	}
	
}
