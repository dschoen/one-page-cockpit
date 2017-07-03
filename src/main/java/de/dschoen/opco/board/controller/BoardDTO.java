package de.dschoen.opco.board.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import de.dschoen.opco.board.model.Column;
import de.dschoen.opco.board.model.Row;
import de.dschoen.opco.board.model.Card;

public class BoardDTO {

    public int boardId;  
    public String name;
    public Instant createDate;
    public Instant lastUpdate;
	public Collection<Column> boardColumns = new ArrayList<Column>();
	public Collection<Row> boardRows = new ArrayList<Row>();
	public Collection<Card> cards = new ArrayList<Card>();
	public Collection<Card> disabledCards = new ArrayList<Card>();
}
