package de.dschoen.opco.board;

import java.util.List;

public interface BoardDAO {

	Board getBoardById(int id);
	List<Board> getAllBoards();
	void addBoard(Board board);
	void updateBoard(Board board);
	void deleteBoard(int boardId);
	
	int countBoards();
	
	BoardColumn getBoardColumnById(int id);
	BoardRow getBoardRowById(int id);
}
