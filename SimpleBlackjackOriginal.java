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

    private void run() {
        Scanner scan = new Scanner(System.in);
        int userFirstCard = pictureCard(randomCard());
        int dealerFirstCard = pictureCard(randomCard());

        mandatoryCards(userFirstCard, dealerFirstCard);


        String answer = "";
        int finalUserHand = userFirstCard;
        while (!answer.equals("stand") && finalUserHand < 21) {
            System.out.println("Do you want to HIT or STAND?");
            answer = scan.next().toLowerCase().strip();
            finalUserHand = userHit(finalUserHand, answer, scan);

            userStand(answer); //Method is invoked and checks if answer equals 'stand'

            userBust(finalUserHand); //Method is invoked and checks if user's hand is > 21
        }

        int finalDealerHand = dealerFirstCard;
        while (finalDealerHand < 17) {
            finalDealerHand = dealerHit(finalDealerHand);

            dealerStand(finalDealerHand);

            dealerBust(finalDealerHand);
        }

        gameWinner(finalUserHand, finalDealerHand);
    }

    private void mandatoryCards(int userFirstCard, int dealerFirstCard) {
        System.out.printf("Your first card is %d%n", userFirstCard);
        System.out.printf("Dealer's first card is %d%n", dealerFirstCard);
    }


    //----------USER HIT----------\\
    private int userHit(int userFirstCard, String answer, Scanner scan) {
        int userFinalHand = userFirstCard;

        if (answer.equals("hit")) {
            int userHit = pictureCard(randomCard());
            userFinalHand += userHit;
            System.out.println("You hit " + userHit); //COMBINE TO ONE LINE AFTER
            System.out.println("Your hand value is now " + userFinalHand);
        }
        return userFinalHand;
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
            System.exit(0);
        }
    }

    //----------DEALER HIT----------\\
    private int dealerHit(int dealerFirstCard) {
        int dealerFinalHitHand = dealerFirstCard;
        int dealerHit = pictureCard(randomCard());
        dealerFinalHitHand += dealerHit;

        System.out.println("\nDealer hits " + dealerHit); //COMBINE TO ONE LINE AFTER
        System.out.print("Dealer's hand value is now " + dealerFinalHitHand);

        return dealerFinalHitHand;
    }

    //----------DEALER STAND----------\\
    private void dealerStand(int dealerHand) {
        if (dealerHand > 17 && dealerHand <= 21) {
            System.out.println(", and Dealer STANDS");
        }
    }

    //----------DEALER BUST----------\\
    private void dealerBust(int dealerHand) {
        if (dealerHand > 21) {
            System.out.println("\n\nDealer has BUSTED\nYou win!");
            System.exit(0);
        }
    }

    //----------DETERMINES GAME WINNER----------\\
    private void gameWinner(int userHand, int dealerHand) {
        if (userHand > dealerHand){
            System.out.println("\nYou WIN!");
        } else if (userHand < dealerHand) {
            System.out.println("\nYou LOSE!");
        } else {
            System.out.println("\nPUSH!");
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

}
