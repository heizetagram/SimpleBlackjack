/*
Jeg prøvede at få user og Dealer til at skiftes med at trække et kort, men kunne ikke få det til at fungere.
Så jeg endte med at give Dealer lov til at trække efter user.
*/

import java.util.Random;
import java.util.Scanner;

public class SimpleBlackjackOriginal {
    public static void main(String[] args) {
        new SimpleBlackjackOriginal().run();
    }

    //----------RUN METHOD----------\\
    private void run() {
        Scanner scan = new Scanner(System.in);
        int userFirstCard = pictureCard(randomCard());
        int dealerFirstCard = pictureCard(randomCard());
        introductoryText(userFirstCard, dealerFirstCard); //Invokes 'introductoryText' that prints...

        String answer = "";
        int userFinalHandValue = userFirstCard;
        while (!answer.equals("stand") && userFinalHandValue < 21) { //While user's String input isn't 'stand and while user's hand is below 21, keep running the code
            System.out.println("Do you want to HIT or STAND?");
            answer = scan.next().toLowerCase().strip(); //Prompts user for String input and assigns 'answer' that value
            userFinalHandValue = userHit(userFinalHandValue, answer, scan); //Invokes method userHit, that runs if 'answer' equals 'hit'. If yes, then assign 'userFinalHandValue' that value

            userStand(answer); //Method is invoked and checks if 'answer' equals 'stand'

            userBust(userFinalHandValue); //Method is invoked and checks if user's hand is > 21
        }

        int finalDealerHandValue = dealerFirstCard;
        while (finalDealerHandValue < 17) { //While Dealer's hand is below 17, keep running the code below
            finalDealerHandValue = dealerHit(finalDealerHandValue); //Invokes 'dealerHit' and assigns 'finalDealerHandValue' that value

            dealerStand(finalDealerHandValue); //Invokes 'dealerStand' that checks if Dealer's hand is between 17-21

            dealerBust(finalDealerHandValue); //Invokes 'dealerBust' that checks if Dealer's hand is above 21
        }
        gameWinner(userFinalHandValue, finalDealerHandValue); //Invokes 'gameWinner' that checks who has the higher card, if either haven't busted
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

    //----------INTRODUCTORY TEXT----------\\
    private void introductoryText(int userFirstCard, int dealerFirstCard) {
        System.out.printf("Your first card is %d%n", userFirstCard);
        System.out.printf("Dealer's first card is %d%n", dealerFirstCard);
    }

    //----------USER HIT----------\\
    private int userHit(int userFirstCard, String answer, Scanner scan) {
        int newUserHandValue = userFirstCard;

        //If user inputs 'hit', get another card and add the value to the user's current hand
        if (answer.equals("hit")) {
            int userHitResult = pictureCard(randomCard()); //Creates int var with invoked method 'pictureCard' (random card between 1-13)
            newUserHandValue += userHitResult; //Adds 'userHitResult' to 'newUserHandValue'

            System.out.printf("You hit %d%nYour hand value is now %d%n", userHitResult, newUserHandValue);
        }
        return newUserHandValue; //Returns user's updated hand value as an int
    }

    //----------USER STAND----------\\
    private void userStand(String answer) {
        if (answer.equals("stand"))
            System.out.println("You STAND");
    }

    //----------USER BUST----------\\
    private void userBust(int userHand) {
        if (userHand > 21) {
            System.out.println("\nYou BUST!\nDealer wins!");
            System.exit(0); //Ends code, if user's hand busts
        }
    }

    //----------DEALER HIT----------\\
    private int dealerHit(int dealerFirstCard) {
        int newDealerHandValue = dealerFirstCard;
        int dealerHitResult = pictureCard(randomCard()); //Creates int var with invoked method 'pictureCard' (random card between 1-13)
        newDealerHandValue += dealerHitResult; //Adds 'dealerHitResult' to 'newUserHandValue'

        System.out.printf("%nDealer hits %d%nDealer's hand value is now %d", dealerHitResult, newDealerHandValue);

        return newDealerHandValue; //Returns dealer's updated hand as an int
    }

    //----------DEALER STAND----------\\
    private void dealerStand(int dealerHand) {
        if (dealerHand >= 17 && dealerHand <= 21) { //If Dealer's hand is between 17-21, then Dealer stands
            System.out.println(", and Dealer STANDS");
        }
    }

    //----------DEALER BUST----------\\
    private void dealerBust(int dealerHand) {
        if (dealerHand > 21) {
            System.out.println("\n\nDealer has BUSTED\nYou win!");
            System.exit(0); //Ends code, if Dealer's hand busts
        }
    }

    //----------DETERMINES GAME WINNER----------\\
    private void gameWinner(int userHand, int dealerHand) {
        if (userHand > dealerHand) { //If 'userHand' is greater than 'dealerHand', then print following...
            System.out.println("\nYou WIN!");
        } else if (userHand < dealerHand) { //Else if 'userHand' is lower than 'dealerHand', then print following...
            System.out.println("\nYou LOSE!");
        } else { //Else 'userHand' and 'dealerHand' are equal, so print following...
            System.out.println("\nPUSH!");
        }
    }
}

