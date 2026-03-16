package com.badgersnacks.flashcards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

public class FlashCardsTest {

    //@Test
    public void testFlashCard(){
        FlashCard card1 = new FlashCard("I am the front of the card", "I am the back of the card");
        IO.println("getFront Test: " + card1.getFront());
        IO.println("getBack Test: " + card1.getBack());
        IO.println("toString Test: " + card1.toString());
        card1.setFront("This is a new front");
        card1.setBack("This is a new back");
        IO.println("getFront Test 2: " + card1.getFront());
        IO.println("getBack Test 2: " + card1.getBack());
    }

    //@Test
    public void testDeck(){
        Deck deck1 = new Deck();
        deck1.setDeckName("Test Deck 1");
        IO.println("getDeckName Test: " + deck1.getDeckName());
        deck1.addCard("Card 1 front", "Card 1 back");
        deck1.addCard("Card 2 front", "Card 2 back");
        deck1.addCard("Card 3 front", "Card 3 back");
        deck1.addCard("Card 4 front", "Card 4 back");
        deck1.addCard("Card 5 front", "Card 5 back");
        IO.println("getCardTest 1: " + deck1.getCard(0).toString());
        IO.println("getCardTest 2: " + deck1.getCard(1).toString());
        IO.println("getCardTest 3: " + deck1.getCard(2).toString());
        IO.println("getCardTest 4: " + deck1.getCard(3).toString());
        IO.println("getCardTest 5: " + deck1.getCard(4).toString());
        IO.println("size Test: " + deck1.getSize());
        IO.println("Removing a card!");
        deck1.removeCard(0);
        IO.println("size Test after removing card: " + deck1.getSize());
    }

}
