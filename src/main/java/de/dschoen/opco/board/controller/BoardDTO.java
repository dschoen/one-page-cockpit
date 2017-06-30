package de.dschoen.opco.board.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import de.dschoen.opco.board.model.BoardColumn;
import de.dschoen.opco.board.model.BoardRow;
import de.dschoen.opco.board.model.Card;

public class BoardDTO {

    public int boardId;  
    public String name;
    public Instant createDate;
    public Instant lastUpdate;
	public Collection<BoardColumn> boardColumns = new ArrayList<BoardColumn>();
	public Collection<BoardRow> boardRows = new ArrayList<BoardRow>();
	public Collection<Card> cards = new ArrayList<Card>();
	public Collection<Card> disabledCards = new ArrayList<Card>();
}
