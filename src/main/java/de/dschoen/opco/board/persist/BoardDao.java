package de.dschoen.opco.board.persist;

import java.util.ArrayList;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;

public interface BoardDao {

	public void init();
	
	public ArrayList<Board> getAllBoards();
	public Board getBoardById(int id);	
	
	public Board addBoard(Board board);
	public Board updateBoard(Board board);
	public void deleteBoard(Board board);
	
	public int countBoards();
	
	public Column getBoardColumnById(int id);
	public Row getBoardRowById(int id);
}
