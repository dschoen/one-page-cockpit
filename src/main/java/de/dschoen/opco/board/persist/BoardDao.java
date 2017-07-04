package de.dschoen.opco.board.persist;

import java.util.ArrayList;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;

public interface BoardDao {

	public void createBoardTable();
	public void createColumnTable();
	public void createRowTable();
	
	public boolean boardExistsById(int id);
	public int countBoards();

	public ArrayList<Board> getBoards();
	public Board getBoardById(int id);	
	public Board addBoard(Board board);	
	public Board updateBoard(Board board);
	public void deleteBoard(Board board);
	
	public ArrayList<Column> getColumnsOfBoard(Board board);
	public Column getColumnById(int id);
	public Column addColumnToBoard(Column column, Board board);
	public Column updateColumn(Column column);
	public void deleteColumn(Column column);
	
	public ArrayList<Row> getRowsOfBoard(Board board);
	public Row getRowById(int id);
	public Row addRowToBoard(Row row, Board board);
	public Row updateRow(Row row);
	public void deleteRow(Row row);	
}
