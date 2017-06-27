package de.dschoen.opco;

import de.dschoen.opco.board.Board;
import de.dschoen.opco.board.BoardColumn;
import de.dschoen.opco.board.BoardRow;
import de.dschoen.opco.board.Card;

public class Util {

	public static Card cardDummy(String title) {
		Card card = new Card();
		card.setTitle(title);
		card.setText("Dummy Text.");
		return card;
	}
	
	public static Board boardDummy(String name) {
		Board board = new Board();
		board.setName(name);
		return board;
	}
	
	public static BoardRow rowDummy(String name) {
		BoardRow row = new BoardRow();
		row.setName(name);
		return row;
	}
	
	public static BoardColumn columnDummy(String name) {
		BoardColumn column = new BoardColumn();
		column.setName(name);
		return column;
	}
}
