package presentation;

import abst.IDeckCreator;
import abst.IDeckShuffle;
import abst.IHandService;
import enums.DeckType;
import factory.DeckFactory;
import model.deck.Deck;
import model.hand.Hand;
import services.deck.manipulate.DeckService;
import services.hand.HandService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Lütfen deste türünü seçiniz");
        System.out.println("1 - Alchemy");
        System.out.println("2 - Element");
        System.out.println("3 - Quantum");

        int deckChoice = input.nextInt();

        Deck deck = switch (deckChoice) {
            case 1 -> createDeck(DeckType.ALCHEMY_SET);
            case 2 -> createDeck(DeckType.ELEMENT_SET);
            case 3 -> createDeck(DeckType.QUANTUM_SET);
            default -> throw new IllegalArgumentException("Hatalı deste seçimi");
        };

        IDeckShuffle deckShuffleService = DeckService.getInstance();
        IHandService handService = HandService.getInstance();

        deckShuffleService.shuffleDeck(deck);

        Hand hand = handService.createHand(deck);

        boolean running = true;

        while (running) {

            System.out.println("\n========= TEST MENU =========");
            System.out.println("1 - Hand göster");
            System.out.println("2 - Hand'e 1 kart ekle");
            System.out.println("3 - Hand'e istediğim kadar kart ekle");
            System.out.println("4 - Hand'den kart sil");
            System.out.println("5 - Deck kalan kart sayısını göster");
            System.out.println("6 - Hand kart sayısını göster");
            System.out.println("0 - Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = input.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nYour Hand:");
                    handService.showHand(hand);
                    break;

                case 2:
                    handService.addCard(hand, deck);
                    System.out.println("Hand'e 1 kart eklendi.");
                    break;

                case 3:
                    System.out.print("Kaç kart eklemek istiyorsunuz?: ");
                    int count = input.nextInt();

                    handService.addCards(hand, deck, count);
                    System.out.println(count + " kart hand'e eklendi.");
                    break;

                case 4:
                    System.out.println("\nSilmeden önce hand:");
                    handService.showHand(hand);

                    System.out.print("Silmek istediğiniz kartın sıra numarasını giriniz: ");
                    int cardNumber = input.nextInt();

                    int cardIndex = cardNumber - 1;

                    handService.removeCard(hand, cardIndex);
                    System.out.println(cardNumber + ". kart hand'den silindi.");
                    break;

                case 5:
                    System.out.println("Deck kalan kart sayısı: " + deck.getCards().size());
                    break;

                case 6:
                    System.out.println("Hand kart sayısı: " + hand.getCards().size());
                    break;

                case 0:
                    running = false;
                    System.out.println("Test sonlandırıldı.");
                    break;

                default:
                    System.out.println("Hatalı seçim.");
            }
        }

        input.close();
    }

    public static Deck createDeck(DeckType deckType) {
        IDeckCreator deckCreator = DeckFactory.createDeck(deckType);
        return deckCreator.deckCreate();
    }
}