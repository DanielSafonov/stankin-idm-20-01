package ru.stankin.emailsign;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter run configuration: \n" +
                "1 -- producer, 2 -- consumer \n" +
                "> ");
        switch (in.nextInt()){
            case 1:
                new Producer().startProducer();
                break;
            case 2:
                new Consumer().startConsumer();
                break;
            default:
                System.out.println("Incorrect input!");
        }
    }
}
