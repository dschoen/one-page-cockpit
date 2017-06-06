package de.dschoen.opco.board;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="board_columns")
public class BoardColumn implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="board_column_id")
    private int boardColumnId;  
	
	@Column(name="name")
    private String name;
	
	// --- Constructor -------------------------------
	
	public BoardColumn() {
	}
	
	public BoardColumn(String name) {
		this.name = name;
	}
	
	// -----------------------------------------------
	
	public int getBoardColumnId() {
		return boardColumnId;
	}

	public void setBoardColumnId(int boardColumnId) {
		this.boardColumnId = boardColumnId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
