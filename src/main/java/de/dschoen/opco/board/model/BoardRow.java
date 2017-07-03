package de.dschoen.opco.board.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="board_rows")
public class BoardRow implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="board_row_id")
    private int boardRowId;  
	
	@Column(name="name")
    private String name;
	
	// --- Constructor -------------------------------
	
	public BoardRow() {
	}
	
	public BoardRow(String name) {
		this.name = name;
	}
	
	// -----------------------------------------------
	
	public int getBoardRowId() {
		return boardRowId;
	}

	public void setBoardRowId(int boardRowId) {
		this.boardRowId = boardRowId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}