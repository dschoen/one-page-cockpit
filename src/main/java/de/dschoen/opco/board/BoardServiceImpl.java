package de.dschoen.opco.board;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private CardDao cardDAO;
	
	// ----------------------------------------------------
	
	@Override
	public Board getBoardById(int boardId) {
		Board obj = boardDao.getBoardById(boardId);
		return obj;
	}	
	
	// ----------------------------------------------------
	
	@Override
	public List<Board> getAllBoards(){
		return boardDao.getAllBoards();
	}
	
	// ----------------------------------------------------
	
	@Override
	public synchronized boolean addBoard(Board board){
        boardDao.addBoard(board);
        return true;
	}
	
	// ----------------------------------------------------
	
	@Override
	public void updateBoard(Board board) {		
		logger.debug("Update Board: "+board.getName());		
		
		Board newBoard = boardDao.getBoardById(board.getBoardId());
		newBoard.setName(board.getName());
		newBoard.updateBoardRows(board.getBoardRows());
		newBoard.updateBoardColumns(board.getBoardColumns());
		
		//do not change Cards
		
		boardDao.updateBoard(board);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void deleteBoard(int boardId) {
		Board board = boardDao.getBoardById(boardId);
		boardDao.deleteBoard(board);
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
		BoardColumn obj = boardDao.getBoardColumnById(boardColumnId);
		return obj;
	}
		
	// ----------------------------------------------------	
	
	@Override
	public BoardRow getBoardRowById(int boardRowId) {
		BoardRow obj = boardDao.getBoardRowById(boardRowId);
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
}
