import java.util.*;
public class Deck {

    private ArrayList<Card> cardPool;               // card pool to play
    private String[] displaySpot;                   // record the card information (for user)
    private int[] valueSpot;                        // record the card value (for calculating score)

    // initialize
    public Deck() {
        this.cardPool = new ArrayList<>();
        this.displaySpot = new String[20];
        this.valueSpot = new int[20];
        setDeck();
    }

    // set the deck
    public void setDeck() {
        // initialize displaySpot
        String[] display = new String[20];
        for (int i = 0; i < 20; i++) {
            int num = 1 + i;
            display[i] = Integer.toString(num);
        }
        this.displaySpot = display;
        // initialize value storage for each spot
        this.valueSpot = new int[20];
        ArrayList<Card> pool = new ArrayList<Card>();
        String[] number = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suit = {"S", "H", "D", "C"};       // "Spade","Heart","Diamond","Club"
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                Card x = new Card(number[i], suit[j]);
                pool.add(x);
            }
        }
        // set the base card pool
        this.cardPool = pool;
    }

    // display the card spots (not discard)
    public void displaySpot() {
        System.out.println("<DECK>");
        for (int i = 0; i < 5; i++) {
            String cardValue = displaySpot[i];
            System.out.print(cardValue + " ");
        }
        System.out.println();
        for (int i = 5; i < 10; i++) {
            String cardValue = displaySpot[i];
            System.out.print(cardValue + " ");
        }
        System.out.println();
        for (int i = 10; i < 13; i++) {
            String cardValue = displaySpot[i];
            System.out.print(cardValue + " ");
        }
        System.out.println();
        for (int i = 13; i < 16; i++) {
            String cardValue = displaySpot[i];
            System.out.print(cardValue + " ");
        }
    }

    // display the discard area
    public void discardSpot(){
        System.out.println();
        System.out.print("DISCARD: [ ");
        for (int i = 16; i < 20; i++) {
            String cardValue = displaySpot[i];
            System.out.print(cardValue + " ");
        }
        System.out.print("]");
    }

    // shuffle the card pool
    public void shufflePool() {
        ArrayList<Card> newPool = new ArrayList<Card>();
        // by randomly drawing the card from the old pool and put it in to new pool
        Random rand = new Random();
        for (int i = 0; i < 52; i++) {
            int index = rand.nextInt(cardPool.size());
            Card c = cardPool.remove(index);
            newPool.add(c);
        }
        cardPool = newPool;
    }

    // Draw the card
    public Card drawCard(){
        Card c = cardPool.remove(0);
        System.out.println("The drawn Card is: " + c.getString());
        return c;
    }

    // Put the card on either discard or display area (make sure user choose a right spot)
    public boolean putCard(Card c, int index){
        String card = c.getString();
        // check if it is able to put on this spot
        boolean isEmptySpot = displaySpot[index-1].equals(Integer.toString(index));
        if (isEmptySpot) {                                               // if there is no card on it
            displaySpot[index-1] = card;                                 // place the card on it
            valueSpot[index-1] = c.getValue();                           // store the value of the card
        } else {
            System.out.println("Wrong spot! Please choose an empty spot!");
        }
        return isEmptySpot;                                              // (for main method) if false, ask user to choose again
    }

    // get the final score of this game
    public int getScore(){
        int score = 0;
        // the value of each row
        int[] row1 = {valueSpot[0], valueSpot[1], valueSpot[2], valueSpot[3], valueSpot[4]};
        int[] row2 = {valueSpot[5], valueSpot[6], valueSpot[7], valueSpot[8], valueSpot[9]};
        int[] row3 = {valueSpot[10], valueSpot[11], valueSpot[12]};
        int[] row4 = {valueSpot[13], valueSpot[14], valueSpot[15]};
        // the max sum of each row (special case A)
        int sum_row1 = maxSum(row1);
        int sum_row2 = maxSum(row2);
        int sum_row3 = maxSum(row3);
        int sum_row4 = maxSum(row4);
        // sum up all calculated scores for each row
        score += calScore(sum_row1,5);
        score += calScore(sum_row2,5);
        score += calScore(sum_row3,3);
        score += calScore(sum_row4,3);
        // the value of each column
        int[] col1 = {valueSpot[0],  valueSpot[5]};
        int[] col2 = {valueSpot[1],  valueSpot[6],  valueSpot[10],  valueSpot[13]};
        int[] col3 = {valueSpot[2],  valueSpot[7],  valueSpot[11],  valueSpot[14]};
        int[] col4 = {valueSpot[3],  valueSpot[8],  valueSpot[12],  valueSpot[15]};
        int[] col5 = {valueSpot[4],  valueSpot[9]};
        // the max sum of each column (special case A)
        int sum_col1 = maxSum(col1);
        int sum_col2 = maxSum(col2);
        int sum_col3 = maxSum(col3);
        int sum_col4 = maxSum(col4);
        int sum_col5 = maxSum(col5);
        // sum up all calculated scores for each column
        score += calScore(sum_col1,2);
        score += calScore(sum_col2,4);
        score += calScore(sum_col3,4);
        score += calScore(sum_col4,4);
        score += calScore(sum_col5,2);

        return score;
    }

    // check if the row or column has A inside
    private static boolean hasA(int[] targetArray){
        for (int i = 0; i < targetArray.length; i++){
            if(targetArray[i] == 1){        // since we initialize set A = 1
                return true;
            }
        }
        return false;
    }

    // find the maximum sum of that row or column (consider the case hasA)
    private static int maxSum(int[] targetArray){
        int sum = 0;
        for (int i = 0; i < targetArray.length; i++){
            sum += targetArray[i];
        }
        // special case: check if 11 is a better choice of A
        if (hasA(targetArray)){
            if (sum + 10 <= 21){
                sum += 10;
            }
        }
        return sum;
    }

    // the rule of scoring in this game
    private static int calScore(int sum, int numCard){
        int score = 0;
        if (sum == 21 && numCard == 2){
            score = 10;
        } else if (sum == 21 && numCard > 2){
            score = 7;
        } else if (sum == 20){
            score = 5;
        } else if (sum == 19){
            score = 4;
        } else if (sum == 18){
            score = 3;
        } else if (sum == 17){
            score = 2;
        } else if (sum <= 16){
            score = 1;
        } else {
            score = 5;
        }
        return score;
    }

}