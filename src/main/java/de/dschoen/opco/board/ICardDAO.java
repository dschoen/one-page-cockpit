package de.dschoen.opco.board;

import java.util.List;

public interface ICardDAO {

	Card getCardById(int id);
	List<Card> getAllCards();
	void addCard(Card card);
	void updateCard(Card card);
	void deleteCard(int cardId);
}
