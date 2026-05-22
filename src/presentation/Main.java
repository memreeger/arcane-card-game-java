package presentation;

import abst.IGameService;
import enums.DeckType;
import enums.Difficulty;
import model.card.Card;
import services.gameService.GameService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        IGameService gameService = GameService.getInstance();

        System.out.println("========= KULATRO GAME =========");

        // DECK SELECTION
        System.out.println("\nLütfen deste türünü seçiniz");
        System.out.println("1 - Alchemy");
        System.out.println("2 - Element");
        System.out.println("3 - Quantum");
        System.out.print("Seçiminiz: ");

        int deckChoice = input.nextInt();

        DeckType selectedDeckType = switch (deckChoice) {

            case 1 -> DeckType.ALCHEMY_SET;

            case 2 -> DeckType.ELEMENT_SET;

            case 3 -> DeckType.QUANTUM_SET;

            default -> throw new IllegalArgumentException("Hatalı deste seçimi.");
        };

        // DIFFICULTY SELECTION
        System.out.println("\nLütfen zorluk seviyesi seçiniz");
        System.out.println("1 - Easy");
        System.out.println("2 - Medium");
        System.out.println("3 - Hard");
        System.out.println("4 - Extreme");
        System.out.print("Seçiminiz: ");

        int difficultyChoice = input.nextInt();

        Difficulty selectedDifficulty = switch (difficultyChoice) {

            case 1 -> Difficulty.EASY;

            case 2 -> Difficulty.MEDIUM;

            case 3 -> Difficulty.HARD;

            case 4 -> Difficulty.EXTREME;

            default -> throw new IllegalArgumentException("Hatalı zorluk seçimi.");
        };

        // START GAME
        gameService.startGame(selectedDeckType, selectedDifficulty);

        boolean running = true;

        while (running && !gameService.isGameOver()) {

            System.out.println("\n========= GAME INFO =========");
            System.out.println("Current Round: "
                    + gameService.getCurrentRoundNumber() + " / 4");

            System.out.println("Current Target Score: "
                    + gameService.getCurrentTargetScore());

            System.out.println("Total Score: "
                    + gameService.getTotalScore());

            System.out.println("Remaining Deck Cards: "
                    + gameService.getRemainingDeckCardCount());

            System.out.println("Discarded Cards: "
                    + gameService.getDiscardedCardCount());

            System.out.println("\n========= MENU =========");
            System.out.println("1 - Show Hand");
            System.out.println("2 - Discard Card And Draw New Card");
            System.out.println("3 - Submit Hand");
            System.out.println("4 - Show Discard Pile");
            System.out.println("0 - Exit");
            System.out.print("Seçiminiz: ");

            int choice = input.nextInt();

            switch (choice) {

                // SHOW HAND
                case 1:

                    System.out.println("\n========= YOUR HAND =========");

                    gameService.showCurrentHand();

                    break;

                // DISCARD CARD
                case 2:

                    System.out.println("\n========= CURRENT HAND =========");

                    gameService.showCurrentHand();

                    System.out.print("\nDiscard etmek istediğiniz kart numarası: ");

                    int cardNumber = input.nextInt();

                    int cardIndex = cardNumber - 1;

                    gameService.discardCardAndDrawNewCard(cardIndex);

                    System.out.println("\nKart discard edildi.");
                    System.out.println("Yeni kart çekildi.");

                    System.out.println("\n========= UPDATED HAND =========");

                    gameService.showCurrentHand();

                    break;

                // SUBMIT HAND
                case 3:

                    System.out.println("\n========= HAND SUBMITTED =========");

                    gameService.submitHand();

                    if (!gameService.isGameOver()) {

                        System.out.println("\nYeni round başladı.");

                        System.out.println("\n========= NEW HAND =========");

                        gameService.showCurrentHand();
                    }

                    break;

                // SHOW DISCARD PILE
                case 4:

                    System.out.println("\n========= DISCARD PILE =========");

                    for (Card card : gameService.getDiscardedCards()) {

                        System.out.println(card);
                    }

                    break;

                // EXIT
                case 0:

                    running = false;

                    System.out.println("\nOyun sonlandırıldı.");

                    break;

                default:

                    System.out.println("\nHatalı seçim.");
            }
        }

        // FINAL RESULT
        if (gameService.isGameOver()) {

            System.out.println("\n========= FINAL RESULT =========");

            System.out.println("Total Score: "
                    + gameService.getTotalScore());

            if (gameService.isPlayerWon()) {

                System.out.println("YOU WIN!");
            }

            else {

                System.out.println("YOU LOSE!");
            }
        }

        input.close();
    }
}