package de.dschoen.opco.user;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import de.dschoen.opco.board.model.Board;
import de.dschoen.opco.board.model.Card;

public class User {

    private int userId;  

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private Instant createDate;

    private Instant lastLogin;

	private Collection<Board> boards = new ArrayList<Board>();

	private Collection<Card> cards = new ArrayList<Card>();
	
	// -----------------------------------------------
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Board> getBoards() {
		return boards;
	}

	public void setBoards(Collection<Board> boards) {
		this.boards = boards;
	}

	public Collection<Card> getCards() {
		return cards;
	}

	public void setCards(Collection<Card> cards) {
		this.cards = cards;
	}
}
