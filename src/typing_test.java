import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;


public class typing_test {

    public static void main(String[/*(╯°□°)╯︵ ┻━┻*/] args) {

        Scanner scanner = new Scanner(System.in);

        String request = "";

        //gameloop
        while (request.isEmpty()) {

            game();
            System.out.println();
            request = scanner.nextLine();

        }

    }

    public static void game() {

        //clear screen befor every game
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Scanner scanner = new Scanner(System.in);
        String input;

        int sentence_length = 7 + (int)(Math.random() * (15 - 7 + 1));

        String[] sentence = new String[sentence_length];

        //print out the givin sentence
        for (int i = 0; i != sentence_length; i++){
            sentence[i] = create_word();
            System.out.print(sentence[i] + " ");
        }

        //wait for player to press enter -> start
        if(scanner.nextLine().isEmpty()) {

            //start timer - is uses for wpm
            Instant starts = Instant.now();

            //takes input and splits it into words
            input = scanner.nextLine();
            String[] entry = input.split("\\s+");

            //variables used for calculating accuracy
            int correct_letters = 0;
            int letters = 0;


            //checks if entered sentence is equally long as givin sentence, if true starts checking word after word
            if (entry.length == sentence.length) {

                for (int i = 0; i != sentence_length; i++) {

                    //used for displaying spelling mistakes
                    if (sentence[i].length() == entry[i].length()) {

                        for (int j = 0; j != sentence[i].length(); j++){

                            letters++;
                            if(sentence[i].charAt(j) == entry[i].charAt(j)) {
                                correct_letters++;
                                System.out.print(" ");
                            }
                            else {
                                System.out.print("^");
                            }

                        }

                    }
                    //used for displaying missing letters
                    else if(sentence[i].length() >= entry[i].length()){

                        for (int a = 0; a != entry[i].length(); a++) {
                            System.out.print(" ");
                        }

                        for (int a = 0; a != (sentence[i].length() - entry[i].length()); a++) {
                            System.out.print("~");
                        }

                        return;

                    }
                    //used for displaying unnecessary letters
                    else {

                        int overlength = entry[i].length() - sentence[i].length();

                        for (int a = 0; a != sentence[i].length(); a++) {
                            System.out.print(" ");
                        }

                        for (int a = 0; a != overlength; a++) {
                            System.out.print("~");
                        }

                        correct_letters = correct_letters - overlength;

                    }

                    System.out.print(" ");

                }

            }
            //used for displaying missing words
            else {

                for (int i = 0; i != entry.length; i++) {

                    for (int j = 0; j != entry[i].length(); j++){
                        System.out.print(" ");
                    }
                    System.out.print(" ");

                }

                for (int i = entry.length; i != sentence.length; i++) {

                    for (int j = 0; j != sentence[i].length(); j++){
                        System.out.print("~");
                    }
                    System.out.print(" ");

                }

                return;
            }

            //calculate accuracy
            double accuracy = (double) correct_letters / letters * 100;
            System.out.println("\n[Accuracy: " + (int)accuracy + "%]");

            //calculate Words per Minute
            Instant ends = Instant.now();
            long seconds = Duration.between(starts, ends).getSeconds();
            double minutes = (double) seconds / 60;
            int wpm = (int) (sentence_length / minutes);
            System.out.print("[WPM: " + wpm + "]");

        }

    }

    //methode to create Random Words
    public static String create_word() {

        String word = "";

        while (word.length() <=1) {

            try{
                word = Files.readAllLines(Paths.get("/Users/gg1/Documents/Code/Java/typingTest/src/words.txt")).get((int) (Math.random() * (/*75320*/998 + 1)));
            }
            catch(IOException e){
                System.out.println(e);
            }

        }

        return word;

    }

}
