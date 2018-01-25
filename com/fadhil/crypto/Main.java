package com.fadhil.crypto;

import java.util.Scanner;

public class Main {

    private static char METHOD_ENCRYPT = 'a';
    private static char METHOD_DECRYPT = 'b';
    private static char CRYPT_VIGENERE = 'a';
    private static char CRYPT_VIGENERE_EXTENDED = 'b';
    private static char CRYPT_PLAYFAIR = 'c';

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        // Read desired method to be executed
        System.out.println("Classic Cryptography\n");
        System.out.println("  Choose method:");
        System.out.println("    a. Encryption");
        System.out.println("    b. Decryption");
        System.out.print("  Select (a/b) ");
        char method = scanner.next().charAt(0);
        if (method != METHOD_ENCRYPT && method != METHOD_DECRYPT) {
            System.out.println("Wrong option!");
            return;
        }

        System.out.println("\n  Choose algorithm:");
        System.out.println("    a. Vigenere Standard");
        System.out.println("    b. Vigenere Extended");
        System.out.println("    c. Playfair\n");
        System.out.print("  Select (a/b/c) ");
        char option = scanner.next().charAt(0);
        if (option != CRYPT_VIGENERE && option != CRYPT_VIGENERE_EXTENDED && option != CRYPT_PLAYFAIR) {
            System.out.println("Wrong option!");
            return;
        }

        System.out.print("  Insert text: ");
        String text = scanner.next();

        switch (option) {
            case 'a':
                System.out.print("  Insert key: ");
                if (method == METHOD_ENCRYPT) {
                    System.out.println("  Chiper : " + 
                        Encryptor.New(Encryptor.VIGENERE_STANDARD)
                            .SetKey(scanner.next())
                            .Encrypt(text));
                } else {
                    System.out.println("  Plain : " + 
                        Decryptor.New(Encryptor.VIGENERE_STANDARD)
                            .SetKey(scanner.next())
                            .Decrypt(text));
                }
                break;
            case 'b':
                
                break;
            case 'c':
                
                break;
            default:
                System.out.println("Wrong option!");
                return;
        }

    }

}