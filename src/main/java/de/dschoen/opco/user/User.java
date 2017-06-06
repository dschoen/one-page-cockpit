package de.dschoen.opco.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.dschoen.opco.board.Board;
import de.dschoen.opco.board.Card;

@Entity
@Table(name="users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
    private int userId;  
	
	@Column(name="username", unique=true, length = 50)
    private String username;
	
	@Column(name="firstname", length = 100)
    private String firstname;
	
	@Column(name="lastname", length = 100)
    private String lastname;

	@Column(name="email")
    private String email;
	
	@Column(name="password")
    private String password;
	
	@Column(name="create_date")
    private LocalDateTime createDate;
	
	@Column(name="last_login")
    private LocalDateTime lastLogin;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private Collection<Board> boards = new ArrayList<Board>();

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
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
