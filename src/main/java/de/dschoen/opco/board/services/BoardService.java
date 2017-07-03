package de.dschoen.opco.board.services;

import java.util.List;

import de.dschoen.opco.board.controller.CardDTO;
import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.BoardColumn;
import de.dschoen.opco.board.model.BoardRow;
import de.dschoen.opco.board.model.Card;

public interface BoardService {

	Board getBoardById(int id);
	List<Board> getAllBoards();
	boolean addBoard(Board board);
	void updateBoard(Board board);
	void deleteBoard(int boardId); 
	
	boolean addCard(Card card);
	void updateCard(Card card);
	void deleteCard(int cardId);
	
	Card cardDTOtoCard(CardDTO cardDTO);
	
	BoardColumn getBoardColumnById(int id);
	BoardRow getBoardRowById(int id);
	
	void disableCardById(int cardId);
}
