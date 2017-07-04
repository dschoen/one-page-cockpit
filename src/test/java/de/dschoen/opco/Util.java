package de.dschoen.opco;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;
import de.dschoen.opco.board.model.Card;

public class Util {

	public static Card cardDummy(String title) {
		Card card = new Card();
		card.setTitle(title);
		card.setText("Dummy Text.");
		return card;
	}
	
	public static Board boardDummy(String name) {
		Board board = Board.fromName(name);
		return board;
	}
	
	public static Row rowDummy(String name) {
		Row row = Row.fromName(name);
		return row;
	}
	
	public static Column columnDummy(String name) {
		Column column = Column.fromName(name);
		return column;
	}
}
