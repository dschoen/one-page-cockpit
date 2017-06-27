package de.dschoen.opco.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.dschoen.opco.user.UserService;
import de.dschoen.opco.user.User;

@Controller
@RequestMapping("/api/")
public class BoardController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	
	// ----------------------------------------------------
	
	@GetMapping("boards/{id}")
	public ResponseEntity<Board> getBoardById(@PathVariable("id") Integer id) {		
		Board board = boardService.getBoardById(id);
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@GetMapping("/users/{id}/boards")
	public ResponseEntity<List<Board>> getAllBoardsOfUserById(@PathVariable("id") Integer id) {		
		User user = userService.getUserById(id);
		List<Board> list = (List<Board>)user.getBoards();
		return new ResponseEntity<List<Board>>(list, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@PostMapping("/users/{userId}/boards")
	public ResponseEntity<Void> addBoard(@RequestBody Board board, @PathVariable("userId") Integer userId, UriComponentsBuilder builder) {
		
		boolean result = boardService.addBoard(board);
		
		User user = userService.getUserById(userId);
		user.getBoards().add(board);
		userService.updateUser(user);
		
        if (result == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/boards/{id}").buildAndExpand(board.getBoardId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	// ----------------------------------------------------
	
	@PutMapping("/users/{userId}/boards/{boardId}")
	public ResponseEntity<Board> updateBoard(@RequestBody Board board, @PathVariable("userId") Integer userId, @PathVariable("boardId") Integer boardId) {
		boardService.updateBoard(board);
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@DeleteMapping("boards/{id}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("id") Integer id) {
		boardService.deleteBoard(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
	
	// ----------------------------------------------------
	
	@GetMapping("boards/{id}/cards")
	public ResponseEntity<List<Card>> getCardsOfBoard(@PathVariable("id") Integer id) {
		Board board = boardService.getBoardById(id);
		List<Card> cards = (List<Card>)board.getCards();
		return new ResponseEntity<List<Card>>(cards, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@PostMapping("boards/{boardId}/cards")
	public ResponseEntity<Void> addCard(@RequestBody CardDTO cardDTO, @PathVariable("boardId") Integer boardId, UriComponentsBuilder builder) {

		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json;
			json = ow.writeValueAsString(cardDTO);
			
			logger.debug("Request to Add Card: " + json);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// encode CardInformation
		Card card = boardService.cardDTOtoCard(cardDTO);
		
		boolean result = boardService.addCard(card);
        if (result == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/boards/{id}/cards/").buildAndExpand(card.getCardId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	// ----------------------------------------------------
	
	@PutMapping("boards/{boardId}/cards/{cardId}")
	public ResponseEntity<Card> updateCard(@RequestBody CardDTO cardDTO) {
		
		Card card = boardService.cardDTOtoCard(cardDTO);
		
		boardService.updateCard(card);
		return new ResponseEntity<Card>(card, HttpStatus.OK);
	}
	
	// ----------------------------------------------------
	
	@DeleteMapping("boards/{id}/cards/{cardId}")
	public ResponseEntity<Void> deleteBoard(@PathVariable("id") Integer id, @PathVariable("cardId") Integer cardId) {
		boardService.disableCardById(cardId);
		// boardService.deleteCard(cardId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
}
