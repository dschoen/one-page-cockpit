package de.dschoen.opco.board;

import java.util.List;

public interface ICardDAO {

	Card getCardById(int cardId);
	List<Card> getAllCards();
	void addCard(Card card);
	void updateCard(Card card);
	void deleteCard(Card card);
	
	void disableCard(Card card);
	void enableCard(Card card);
}
