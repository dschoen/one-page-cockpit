package de.dschoen.opco.board;

import java.util.List;

import de.dschoen.opco.user.User;

public interface IBoardService {

	Board getBoardById(int id);
	List<Board> getAllBoardsOfUser(User user);
	boolean addBoard(Board board);
	void updateBoard(Board board);
	void deleteBoard(int boardId);     
	
}
