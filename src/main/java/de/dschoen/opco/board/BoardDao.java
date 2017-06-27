package de.dschoen.opco.board;

import java.util.List;

public interface BoardDao {

	public Board getBoardById(int id);
	public List<Board> getAllBoards();
	
	public Board addBoard(Board board);
	public Board updateBoard(Board board);
	public void deleteBoard(Board board);
	
	public int countBoards();
	
	public BoardColumn getBoardColumnById(int id);
	public BoardRow getBoardRowById(int id);
}
