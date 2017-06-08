package de.dschoen.opco.board;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService implements IBoardService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IBoardDAO boardDAO;
	
	@Autowired
	private ICardDAO cardDAO;
	
	// ----------------------------------------------------
	
	@Override
	public Board getBoardById(int boardId) {
		Board obj = boardDAO.getBoardById(boardId);
		return obj;
	}	
	
	// ----------------------------------------------------
	
	@Override
	public List<Board> getAllBoards(){
		return boardDAO.getAllBoards();
	}
	
	// ----------------------------------------------------
	
	@Override
	public synchronized boolean addBoard(Board board){
        boardDAO.addBoard(board);
        return true;
	}
	
	// ----------------------------------------------------
	
	@Override
	public void updateBoard(Board board) {
		boardDAO.updateBoard(board);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void deleteBoard(int boardId) {
		boardDAO.deleteBoard(boardId);
	}
	
	// ----------------------------------------------------
	
	@Override
	public synchronized boolean addCard(Card card){
        cardDAO.addCard(card);
        return true;
	}
	
	// ----------------------------------------------------
	
	@Override
	public void updateCard(Card card) {
		cardDAO.updateCard(card);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void deleteCard(int cardId) {
		Card card = cardDAO.getCardById(cardId);
		cardDAO.deleteCard(card);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void disableCardById(int cardId) {
		Card card = cardDAO.getCardById(cardId);
		cardDAO.disableCard(card);
	}

	// ----------------------------------------------------	
	
	@Override
	public BoardColumn getBoardColumnById(int boardColumnId) {
		BoardColumn obj = boardDAO.getBoardColumnById(boardColumnId);
		return obj;
	}
		
	// ----------------------------------------------------	
	
	@Override
	public BoardRow getBoardRowById(int boardRowId) {
		BoardRow obj = boardDAO.getBoardRowById(boardRowId);
		return obj;
	}
	
	// ----------------------------------------------------
	
	@Override
	public Card cardDTOtoCard(CardDTO cardDTO) {
		Card card = new Card();
		card.setCardId(cardDTO.cardId);
		card.setTitle(cardDTO.title);
		card.setText(cardDTO.text);
		card.setStatus(cardDTO.status);
		card.setEffort(cardDTO.effort);
		
		// get Board, Row and Column
		card.setBoard(this.getBoardById(cardDTO.boardId));
		card.setBoardColumn(this.getBoardColumnById(cardDTO.boardColumnId));
		card.setBoardRow(this.getBoardRowById(cardDTO.boardRowId));
		

		if (cardDTO.startDate != null) {			
			card.setStartDate( Instant.parse(cardDTO.startDate) );
		}
		if (cardDTO.endDate != null) {
			card.setEndDate( Instant.parse(cardDTO.endDate) );
		}
		
		return card;
	}
	
	// ----------------------------------------------------
	
	@Override
	public Board boardDTOtoBoard(BoardDTO boardDTO) {
		Board board = new Board();
		board.setBoardId(boardDTO.boardId);
		board.setName(boardDTO.name);
		
		// Create Rows
		for (String rowString : boardDTO.boardRows) {
			BoardRow boardRow = new BoardRow(rowString);
			board.getBoardRows().add(boardRow);
		}
		
		for (String colString : boardDTO.boardColumns) {
			BoardColumn boardColumn = new BoardColumn(colString);
			board.getBoardColumns().add(boardColumn);
		}	
		
		return board;
	}
}
