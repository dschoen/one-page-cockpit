package de.dschoen.opco.board.services;

import java.time.Instant;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dschoen.opco.board.controller.CardDTO;
import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;
import de.dschoen.opco.board.model.Card;
import de.dschoen.opco.board.persist.BoardDao;
import de.dschoen.opco.board.persist.CardDao;

@Service
public class BoardService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardDao boardDao;	
	@Autowired
	private CardDao cardDAO;
	
	// ----------------------------------------------------
	/**
	 * Init Board Module. Creates all neccessary tables.
	 */
	public void init() {
		this.boardDao.createBoardTable();
		this.boardDao.createColumnTable();
		this.boardDao.createRowTable();
	}
	
	// ----------------------------------------------------
	
	public Board getBoardById(int boardId) {
		Board obj = boardDao.getBoardById(boardId);
		return obj;
	}	
	
	// ----------------------------------------------------
	
	public ArrayList<Board> getBoards(){
		return boardDao.getBoards();
	}
	
	// ----------------------------------------------------
	
	public synchronized boolean addBoard(Board board){
        boardDao.addBoard(board);
        return true;
	}
	
	// ----------------------------------------------------
	
	public void updateBoard(Board board) {		
		logger.debug("Update Board: "+board.getName());		

		//TODO
//		Board newBoard = boardDao.getBoardById(board.getBoardId());
//		newBoard.setName(board.getName());
//		newBoard.updateBoardRows(board.getBoardRows());
//		newBoard.updateBoardColumns(board.getBoardColumns());
		
		//do not change Cards
		
		boardDao.updateBoard(board);
	}
	
	// ----------------------------------------------------
	
	public void deleteBoard(int boardId) {
		Board board = boardDao.getBoardById(boardId);
		boardDao.deleteBoard(board);
	}
	
	// ----------------------------------------------------
	
	public synchronized boolean addCard(Card card){
        cardDAO.addCard(card);
        return true;
	}
	
	// ----------------------------------------------------
	
	public void updateCard(Card card) {
		cardDAO.updateCard(card);
	}
	
	// ----------------------------------------------------
	
	public void deleteCard(int cardId) {
		Card card = cardDAO.getCardById(cardId);
		cardDAO.deleteCard(card);
	}
	
	// ----------------------------------------------------
	
	public void disableCardById(int cardId) {
		Card card = cardDAO.getCardById(cardId);
		cardDAO.disableCard(card);
	}

	// ----------------------------------------------------	
	
	public Column getBoardColumnById(int boardColumnId) {
		Column obj = boardDao.getColumnById(boardColumnId);
		return obj;
	}
		
	// ----------------------------------------------------	
	
	public Row getBoardRowById(int boardRowId) {
		Row obj = boardDao.getRowById(boardRowId);
		return obj;
	}
	
	// ----------------------------------------------------
	
	public Card cardDTOtoCard(CardDTO cardDTO) {
		Card card = new Card();
		card.setCardId(cardDTO.cardId);
		card.setTitle(cardDTO.title);
		card.setText(cardDTO.text);
		card.setStatus(cardDTO.status);
		card.setEffort(cardDTO.effort);
		
		// get Board, Row and Column
//		card.setBoard(this.getBoardById(cardDTO.boardId));
//		card.setBoardColumn(this.getBoardColumnById(cardDTO.boardColumnId));
//		card.setBoardRow(this.getBoardRowById(cardDTO.boardRowId));
		

		if (cardDTO.startDate != null) {			
			card.setStartDate( Instant.parse(cardDTO.startDate) );
		}
		if (cardDTO.endDate != null) {
			card.setEndDate( Instant.parse(cardDTO.endDate) );
		}
		
		return card;
	}
	
	// ----------------------------------------------------
}
