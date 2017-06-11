package de.dschoen.opco.board;

import java.util.List;

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
	Board boardDTOtoBoard(BoardDTO boardDTO);
}
