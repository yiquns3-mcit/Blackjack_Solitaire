import java.util.*;

public class BlackjackSolitaire {

    public void play(){

        // prepare for the game (initialize deck)
        Deck deck = new Deck();
        deck.setDeck();

        // welcome words!
        System.out.println("Welcome to Blackjack Solitaire Game!");

        // display the deck
        deck.displaySpot();
        deck.discardSpot();

        // shuffle the card pool
        deck.shufflePool();
        System.out.println();
        System.out.println("Shuffle the card pool already!");

        // LOOP: spot status check (stop when no more empty spot)
        while (!deck.isFull()) {
            // draw the card
            Card c = deck.drawCard();
            // LOOP: user input check (stop when user enter an available spot)
            boolean success = false;
            while (!success) {
                // ask user to put on the deck
                System.out.println("[Notice: 17 to 20 are discard area]");
                // make sure user enter a number as index
                int index = 0;
                boolean validInput = false;
                while (!validInput) {
                    try {
                        Scanner input = new Scanner(System.in);
                        System.out.println("Choose the spot for this card: (enter number 1 to 20)");
                        index = input.nextInt();
                        validInput = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                    }
                }

                // put the card and check status
                success = deck.putCard(c, index);
            }
            // display the updated deck
            deck.displaySpot();
            deck.discardSpot();
        }

        // All spots are filled, now calculate the final score
        System.out.println();
        int score = deck.getScore();
        System.out.printf("Game over! You scored %d points.", score);
    }

}
