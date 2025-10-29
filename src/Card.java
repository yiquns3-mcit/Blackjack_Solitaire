public class Card {

    private String number;                         // the value of cards: 1 to K
    private String suit;                           // "Spade","Heart","Diamond","Club"

    public Card(String number, String suit){       // setting of the card
        this.number = number;
        this.suit = suit;
    }

    public int getValue(){                          // read the number on the card
        if (number.equals("J") || number.equals("Q") || number.equals("K")){
            return 10;
        } else if (number.equals("A")){
            return 0;                               // set 0 first, later will reset the value when counting the score
        } else {
            return Integer.parseInt(number);
        }
    }

    public String getString(){                      // to show the card information in string
        return (number + suit);
    }
}