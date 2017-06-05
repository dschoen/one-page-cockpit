package de.dschoen.opco.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dschoen.opco.user.User;

@Service
public class BoardService implements IBoardService{

	@Autowired
	private IBoardDAO boardDAO;
	
	// ----------------------------------------------------
	
	@Override
	public Board getBoardById(int boardId) {
		Board obj = boardDAO.getBoardById(boardId);
		return obj;
	}	
	
	// ----------------------------------------------------
	
	@Override
	public List<Board> getAllBoardsOfUser(User user){
		return boardDAO.getAllBoardsOfUser(user);
	}
	
	// ----------------------------------------------------
	
	@Override
	public synchronized boolean addBoard(Board board){
        boardDAO.addBoard(board);
        return true;
	}
	
	// ----------------------------------------------------
	
	@Override
	public void updateBoard(Board board) {
		boardDAO.updateBoard(board);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void deleteBoard(int boardId) {
		boardDAO.deleteBoard(boardId);
	}
	
}
