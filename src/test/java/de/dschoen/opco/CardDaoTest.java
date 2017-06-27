package de.dschoen.opco;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
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
public class CardDaoTest {

	@Autowired
	private CardDao dao;
	@Autowired
	private BoardDao bdao;
	
	private Board board;
	private BoardRow boardRow;
	private BoardColumn boardColumn;
	
	@Before
	public void createBoard() {
		boardRow 	= Util.rowDummy("Reihe");
		boardColumn = Util.columnDummy("Spalte");
		board 		= Util.boardDummy("TestBoard");
		
		board.addBoardColumn(boardColumn);
		board.addBoardRow(boardRow);
		
		bdao.addBoard(board);
	}	
	
	@Test
	public void addAndGetCardTest() {
		
		Card card = Util.cardDummy("Test1");
		card.setBoard(board);
		card.setBoardColumn(boardColumn);
		card.setBoardRow(boardRow);		
		card = dao.addCard(card);
		
		Card card2 = dao.getCardById(card.getCardId());
		
    	assertEquals(card.getTitle(), card2.getTitle());
    	assertEquals(card.getText(), card2.getText());  
    }
	
	@Test
	public void updateCardTest() {
		
		Card card = Util.cardDummy("Test1");
		card.setBoard(board);
		card.setBoardColumn(boardColumn);
		card.setBoardRow(boardRow);		
		card = dao.addCard(card);
		
		card.setText("NEU");
		Card card2 = dao.updateCard(card);
		
    	assertEquals(card2.getText(), "NEU");        
    }
	
	@Test
	public void deleteCardTest() {
		
		int c1 = dao.countCards();
		
		Card card = Util.cardDummy("Test1");
		card.setBoard(board);
		card.setBoardColumn(boardColumn);
		card.setBoardRow(boardRow);		
		card = dao.addCard(card);
		
		int c2 = dao.countCards();
		
		assertTrue(c1 < c2 );
		
		dao.deleteCard(card);
		
		int c3 = dao.countCards();
		
		assertTrue(c3 == c1);		
    }
	
	@After
	public void deleteBoard() {
//		bdao.deleteBoard(board);	
	}
	
}
