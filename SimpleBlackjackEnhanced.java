import java.util.Random;
import java.util.Scanner;

public class SimpleBlackjackEnhanced {
    public static void main(String[] args) {
        new SimpleBlackjackEnhanced().run();
    }

    //----------RUN METHOD----------\\
    private void run() {
        Scanner scan = new Scanner(System.in); //Creates a new Scanner var

        //Creates user's starting cards
        int userMandatoryHit1 = pictureCard(randomCard()); //Creates first int var that saves value of the invoked method 'pictureCard' (user's first card)
        int userMandatoryHit2 = pictureCard(randomCard()); //Creates second int var that saves value of the invoked method 'pictureCard' (user's second card)
        int userMandatoryHitsCalculated = userMandatoryHit1 + userMandatoryHit2; //Creates int var that combines the two var above
        int userFinalHandValue; //Creates int var with the user's final hand value

        //Creates Dealer's starting cards
        int dealerMandatoryHit1 = pictureCard(randomCard()); //Creates first int that saves value of the invoked method 'pictureCard' (Dealer's first card)
        int dealerMandatoryHit2 = pictureCard(randomCard());//Creates second int var that saves value of the invoked method 'pictureCard' (Dealer's second card)
        int dealerMandatoryHitsCalculated = dealerMandatoryHit1 + dealerMandatoryHit2; //Creates int that combines the two var above


        //If the 'userMandatoryHitsCalculated' equals 11, then invoke 'doesUserHaveBlackjack' and check if the user's cards are 1 and 10, and updates the value of user's final hand to 21
        if (userMandatoryHitsCalculated == 11) {
            userFinalHandValue = doesUserHaveBlackjack(userMandatoryHit1, userMandatoryHit2, userMandatoryHitsCalculated);
        } else { //If the 'userMandatoryHitsCalculated' isn't 21, then start the usual game
            introductoryText(dealerMandatoryHit1, userMandatoryHit1, userMandatoryHit2, userMandatoryHitsCalculated); //Invokes 'introductoryText' that prints...
            String answer = scan.next().toLowerCase().strip(); //Creates String var that prompts user for String input, lowercases it, and strips whitespaces
            userFinalHandValue = userHitOrStand(answer, userMandatoryHitsCalculated); //Assigns the value of 'userFinalHandValue' with the Invoked method 'userHitOrStand' that takes two arguments
        }

        //Creates int var with Dealer's final hand value, if their cards haven't busted
        int dealerFinalHandValue = dealerHitOrStand(dealerMandatoryHit1, dealerMandatoryHit2, dealerMandatoryHitsCalculated); //Invokes 'dealerHitOrStand' method that takes three arguments


        //Calculates who has the higher card
        System.out.println(whoHasHigherCard(userFinalHandValue, dealerFinalHandValue)); //Prints method 'whoHasHigherCard' that takes two arguments
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------CHANGES TEXT COLOR----------\\
    private String blueColor() {
        return ConsoleColors.BLUE_BOLD_BRIGHT;
    }
    private String greenColor() {
        return ConsoleColors.GREEN_BOLD_BRIGHT;
    }
    private String redColor() {
        return ConsoleColors.RED_BOLD_BRIGHT;
    }
    private String yellowColor() {
        return ConsoleColors.YELLOW_BOLD;
    }
    private String resetColor() {
        return ConsoleColors.RESET;
    }

    //----------CREATES DELAY-----------\\
    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //----------RANDOM CARD GENERATOR----------\\
    //Gets a random integer from 1-13
    private int randomCard() {
        Random random = new Random(); //Creates random
        return random.nextInt(13) + 1; //Adds +1 to a random integer between 0-12, and returns it.
    }


    //----------PICTURE CARD TO 10 CONVERTER----------\\
    //Converts picture card to 10, and returns the value of the random number if it's below 10
    private int pictureCard(int picture) { //Turns value of integer from 11-13 to 10
        int pictureToTen;
        if (picture > 10) {
            pictureToTen = 10; //Updates value of random number to 10, if its 11-13.
        } else {
            return picture; //If the input value is less than 10 or less, return the original card value without any change
        }
        return pictureToTen;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    //----------INTRODUCTORY TEXT----------\\
    private void introductoryText(int dealerMandatoryHit1, int userMandatoryHit1, int userMandatoryHit2, int userMandatoryHitsCalculated) {
        System.out.printf("%nDealer has the cards %s%d%s and %s#%s", blueColor(), dealerMandatoryHit1, resetColor(), blueColor(), resetColor());
        System.out.printf("%nYou have the cards %s%d%s and %s%d%s equaling %s%d%s%n", greenColor(), userMandatoryHit1, resetColor(), greenColor(), userMandatoryHit2, resetColor(), greenColor(), userMandatoryHitsCalculated, resetColor());
        System.out.println("\nHit or stand?");
    }


    //----------USER HIT OR STAND----------\\
    private int userHitOrStand(String answer, int userCurrentHandValue) {
        Scanner scan = new Scanner(System.in); //Creates a local Scanner var
        int newUserHandValue = userCurrentHandValue; //Creates an int var with the user's current hand value, and stores it in 'newUserHandValue'

        //While user String input isn't 'stand', keep running the code
        do {
            //If user inputs 'hit', get another card and add the value to the user's current hand.
            if (answer.equals("hit")) {
                int hitResult = pictureCard(randomCard()); //Creates int var with invoked method 'pictureCard' (random card between 1-13)
                newUserHandValue += hitResult; //Adds 'hitResult' to 'newUserHandValue'

                System.out.printf("You hit %s%d%s%n", yellowColor(), hitResult, resetColor());
                delay(500);
                System.out.printf("Your new hand value is %s%d%s%n", greenColor(),newUserHandValue,resetColor());

                //If user's hand value is 21, break the do-while loop and force the user to stand
                if (newUserHandValue == 21) {
                    break; //WORKS BUT SHOULDN'T BE USED
                }

                userBustedHand(newUserHandValue); //Invokes method 'userBustedHand' to check if the user's new hand is above 21

                System.out.println("Hit or stand?"); //
                answer = scan.next().toLowerCase().strip(); //Scans user string input, to ensure the loop doesn't keep iterating
            }
            //If user inputs 'stand', run the following
            if (answer.equals("stand")) {
                System.out.printf("You stand and your hand value is %s%d%s%n%n", greenColor(),newUserHandValue,resetColor());
                delay(1500);
            }
        } while (!answer.equals("stand")); //As long as the user's input isn't 'stand', keep running

        return newUserHandValue; //Returns user's updated hand value as an int
    }


    //----------IS USER'S STARTING HAND BLACKJACK?----------\\
    //Checks if the the user's starting hand equals 21. If yes, then force the user to stand.
    private int doesUserHaveBlackjack(int userMandatoryHit1, int userMandatoryHit2, int userMandatoryHitsCalculated){
        int blackjack = userMandatoryHitsCalculated;
        if (blackjackConverter(userMandatoryHit1, userMandatoryHit2) == 21) {
            System.out.printf("%n%nYou have the cards %s%d%s and %s%d%s equaling %sBLACKJACK!%s%n", greenColor(), userMandatoryHit1, resetColor(), greenColor(), userMandatoryHit2, resetColor(), greenColor(), resetColor());
            userHitOrStand("stand", 21);
            blackjack = 21;
        }
        return blackjack;
    }


    //----------BLACKJACK CONVERTER, 1 + 10 = 21----------\\
    //If the mandatory hit cards include 1 and 10, assign the value 21 to 'blackjack', and return it
    private int blackjackConverter(int mandatoryCard1, int mandatoryCard2) {
        int blackjack = 0; //Creates an int var

        if (mandatoryCard1 == 10 && mandatoryCard2 == 1) {
            blackjack = 21;
        }
        if (mandatoryCard1 == 1 && mandatoryCard2 == 10) {
            blackjack = 21;
        }
        return blackjack;
    }



    //----------DEALER HIT OR STAND----------\\
    private int dealerHitOrStand (int dealerMandatory1stCard, int dealerMandatory2ndCard, int dealerCurrentHandValue) {
        int newDealerHandResult = dealerCurrentHandValue; //Creates an int var with the Dealer's current hand value, and stores it in 'newDealerHandResult'
        System.out.printf("Dealer has the cards %s%d%s and %s%d%s equaling %s%d%s", blueColor(), dealerMandatory1stCard, resetColor(), blueColor(), dealerMandatory2ndCard, resetColor(), blueColor(), dealerCurrentHandValue, resetColor());

        //This if-statement is also created out of the while loop, because the Dealer can have 17-21 hands without hitting
        if (newDealerHandResult >= 17 && newDealerHandResult <= 21) {
            System.out.print(" and the Dealer stands");
        }
        System.out.println(); //Prints a new line

        //Dealer has to keep hitting while his hand value is lower than 17
        while (newDealerHandResult < 17) {
            int hitResult = pictureCard(randomCard()); //Creates hit var with invoked method 'pictureCard' (random card between 1-13)
            newDealerHandResult += hitResult; //Adds 'hitResult' to 'newDealerHandResult'
            delay(2000); //Invokes 'delay' method that creates a 2-second delay

            //Prints Dealer's new hit and the hand's new value
            System.out.printf("Dealer hits %s%d%s%n", yellowColor(), hitResult, resetColor());
            delay(500);
            System.out.printf("Dealer's new hand value is %s%d%s", blueColor(), newDealerHandResult, resetColor());

            if (newDealerHandResult >= 17 && newDealerHandResult <= 21) { //If the Dealer's hand value is between 17-21, print the following...
                System.out.print(" and the Dealer stands");
            }

            System.out.println(); //Creates a new line
            dealerBustedHand(newDealerHandResult); //Invokes method 'dealerBustedHand' that checks if the Dealer's new hand value has surpassed 21
        }
        return newDealerHandResult; //Returns as an int
    }



    //----------BUSTED HANDS----------\\
    //Two methods that check whether the user's or Dealer's hand is a bust
    private void userBustedHand(int userHand) {
        if (userHand > 21) { //If the user's hand is above 21, run the following code...
            delay(1000);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "YOU BUST!" + ConsoleColors.RESET);
            System.exit(0); //Ends code (if user's hand busts)
        }
    }
    private void dealerBustedHand(int dealerHand) {
        if (dealerHand > 21) { //If the Dealer's hand is above 21, run the following code...
            delay(1000);
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "DEALER BUSTS!" + ConsoleColors.GREEN_BOLD_BRIGHT + "\nYOU WIN!" + ConsoleColors.RESET);
            System.exit(0); //Ends code (if Dealer's hand busts)
        }
    }



    //----------DETERMINES GAME WINNER----------\\
    //Determines if whether the user or the Dealer has the higher hand or if the value is equal
    private String whoHasHigherCard(int userHand, int dealerHand) {
        String determine; //Creates String var

        if (userHand > dealerHand) { //If the user's hand is higher than the Dealer's hand, run the following code...
            delay(1500); //Invokes method 'delay' that causes a 1.5-second delay
            determine = greenColor() + "YOU WIN!";
        } else if (userHand < dealerHand) { //Else if the user's hand is lower than the Dealer's hand, run the following code...
            delay(1500);
            determine = redColor() + "YOU LOSE!";
        } else { //If both hands are equal, print the following...
            delay(1500);
            determine = yellowColor() + "PUSH";
        }
        return determine; //Returns as a String
    }
}
