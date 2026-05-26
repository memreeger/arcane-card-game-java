package presentation;

import abst.IGameService;
import abst.IUserService;
import dto.userDto.UserRequestDto;
import dto.userDto.UserResponseDto;
import enums.DeckType;
import enums.DifficultyType;
import model.card.Card;
import services.gameService.GameService;
import services.userService.UserService;

import java.util.Scanner;

public class Main {
    public static final int MAX_ROUND = 4;
    public static final int MAX_DISCARD = 6;


    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);

        IUserService userService = UserService.getInstance();
        IGameService gameService = GameService.getInstance();

        System.out.println("========= ARCANE GAME =========");


        UserResponseDto currentUser = null;

        boolean authenticated = false;

        while (!authenticated) {

            System.out.println("========= USER MENU =========");
            System.out.println("1 - Register");
            System.out.println("2 - Login");
            System.out.println("0 - Exit");
            System.out.print("Choice: ");

            int userChoice = input.nextInt();
            input.nextLine();

            switch (userChoice) {

                case 1:
                    System.out.print("Username: ");
                    String registerUsername = input.nextLine();

                    System.out.print("Password: ");
                    String registerPassword = input.nextLine();

                    UserRequestDto registerRequest = new UserRequestDto();
                    registerRequest.setUsername(registerUsername);
                    registerRequest.setPassword(registerPassword);

                    currentUser = userService.register(registerRequest);

                    if (currentUser != null) {
                        authenticated = true;
                        System.out.println("Registration successful. Welcome, " + currentUser.getUsername());
                    } else {
                        System.out.println("Registration failed.");
                    }
                    break;

                case 2:
                    System.out.print("Username: ");
                    String loginUsername = input.nextLine();

                    System.out.print("Password: ");
                    String loginPassword = input.nextLine();

                    UserRequestDto loginRequest = new UserRequestDto();
                    loginRequest.setUsername(loginUsername);
                    loginRequest.setPassword(loginPassword);

                    currentUser = userService.login(loginRequest);

                    if (currentUser != null) {
                        authenticated = true;
                        System.out.println("Login successful. Welcome back, " + currentUser.getUsername());
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 0:
                    System.out.println("Program terminated.");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }

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

        DifficultyType selectedDifficulty = switch (difficultyChoice) {

            case 1 -> DifficultyType.EASY;

            case 2 -> DifficultyType.MEDIUM;

            case 3 -> DifficultyType.HARD;

            case 4 -> DifficultyType.EXTREME;

            default -> throw new IllegalArgumentException("Hatalı zorluk seçimi.");
        };

        // START GAME
        gameService.startGame(selectedDeckType, selectedDifficulty,currentUser);

        boolean running = true;

        while (running && !gameService.isGameOver()) {

            System.out.println("\n========= GAME INFO =========");
            System.out.println("Current Round: "
                    + gameService.getCurrentRoundNumber() + " / " + MAX_ROUND);

            System.out.println("Current Target Score: "
                    + gameService.getCurrentTargetScore());

            System.out.println("Total Score: "
                    + gameService.getTotalScore());

            System.out.println("Remaining Deck Cards: "
                    + gameService.getRemainingDeckCardCount());

            System.out.println("Discarded Cards: "
                    + gameService.getDiscardedCardCount());

            System.out.println("Discarded Card Count: "
                    + gameService.getDiscardedCardCount() + " / " + MAX_DISCARD);

            System.out.println("\n========= MENU =========");
            System.out.println("1 - Show Hand");
            System.out.println("2 - Discard Card And Draw New Card");
            System.out.println("3 - Submit Hand");
            System.out.println("4 - Show Discard Pile");
            System.out.println("5 - Use Special Card");
            System.out.println("0 - Exit");
            System.out.print("Select : ");

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

                    //gameService.discardCardAndDrawNewCard(cardIndex);

                    if (gameService.discardCardAndDrawNewCard(cardIndex)) {
                        System.out.println("\nKart discard edildi.");
                        System.out.println("Yeni kart çekildi.");
                        System.out.println("\n========= UPDATED HAND =========");
                    }

                    System.out.println("\n");
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

                case 5:

                    System.out.println("\n========= CURRENT HAND =========");

                    gameService.showCurrentHand();

                    System.out.print(
                            "Enter special card number: "
                    );

                    int specialCardNumber = input.nextInt();

                    int specialCardIndex = specialCardNumber - 1;

                    gameService.useSpecialCard(
                            specialCardIndex
                    );

                    System.out.println(
                            "Special card used."
                    );

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
            } else {

                System.out.println("YOU LOSE!");
            }
        }

        input.close();
    }


        /*
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        try {
            System.out.println("=== REGISTER TEST ===");

            UserRequestDto registerRequest = new UserRequestDto();
            registerRequest.setUserName("emreeger");
            registerRequest.setPassword("12345");

            UserResponseDto registeredUser = userService.register(registerRequest);

            System.out.println("User registered successfully:");
            System.out.println("ID: " + registeredUser.getId());
            System.out.println("Username: " + registeredUser.getUserName());
            System.out.println("Created At: " + registeredUser.getCreatedAt());

        } catch (Exception e) {
            System.out.println("Register error: " + e.getMessage());
        }

        try {
            System.out.println("\n=== LOGIN TEST ===");

            UserRequestDto loginRequest = new UserRequestDto();
            loginRequest.setUserName("emreeger");
            loginRequest.setPassword("12345");

            UserResponseDto loggedInUser = userService.login(loginRequest);

            System.out.println("Login successful:");
            System.out.println("ID: " + loggedInUser.getId());
            System.out.println("Username: " + loggedInUser.getUserName());
            System.out.println("Created At: " + loggedInUser.getCreatedAt());

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }

        try {
            System.out.println("\n=== DUPLICATE USERNAME TEST ===");

            UserRequestDto duplicateRequest = new UserRequestDto();
            duplicateRequest.setUserName("emreegers");
            duplicateRequest.setPassword("99999");

            userService.register(duplicateRequest);

        } catch (Exception e) {
            System.out.println("Duplicate test result: " + e.getMessage());
        }

        try {
            System.out.println("\n=== WRONG PASSWORD TEST ===");

            UserRequestDto wrongLoginRequest = new UserRequestDto();
            wrongLoginRequest.setUserName("emreeger");
            wrongLoginRequest.setPassword("1234");

            userService.login(wrongLoginRequest);

        } catch (Exception e) {
            System.out.println("Wrong password test result: " + e.getMessage());
        }
    }


         */
}


