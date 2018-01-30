package com.fadhil.crypto;

import java.util.Scanner;

public class Main {

    private static char METHOD_ENCRYPT = 'e';
    private static char METHOD_DECRYPT = 'd';
    private static String CRYPT_VIGENERE = "vigenere";
    private static String CRYPT_VIGENERE_EXTENDED = "vigenere-etd";
    private static String CRYPT_PLAYFAIR = "playfair";

    public static void main(String args[]) {
        Boolean isError = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("    ______                 __      ");
        System.out.println("   / ____/______  ______  / /_____ ");
        System.out.println("  / /   / ___/ / / / __ \\/ __/ __ \\");
        System.out.println(" / /___/ /  / /_/ / /_/ / /_/ /_/ /");
        System.out.println(" \\____/_/   \\__, / .___/\\__/\\____/ ");
        System.out.println("           /____/_/                \n");
        System.out.println("Crypto version 1.0 by fadhilimamk (13515146) 2018-01-30\n");

        // Check arguments
        if (args.length < 2) {
            System.out.println("Error: WRONG ARGUMENT!"); isError = true;
        } else if (args[0].charAt(0) != METHOD_ENCRYPT && args[0].charAt(0) != METHOD_DECRYPT) {
            System.out.println("Error: UNKNOWN METHOD!"); isError = true;
        } else if (!args[1].equals(CRYPT_PLAYFAIR) && !args[1].equals(CRYPT_VIGENERE_EXTENDED) && !args[1].equals(CRYPT_PLAYFAIR)) {
            System.out.println("Error: UNKNOWN ALGORITHM!"); isError = true;
        }

        if (isError) {
            showHelp();
            return;
        }
 

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
        
        scanner.nextLine();
        System.out.print("  Insert text: ");
        String text = scanner.nextLine();
        String key;

        switch (option) {
            case 'a':
                System.out.print("  Insert key: ");
                key = scanner.nextLine();
                if (method == METHOD_ENCRYPT) {
                    System.out.println("  Chiper : " + 
                        Encryptor.New(Encryptor.VIGENERE_STANDARD)
                            .SetKey(key)
                            .Encrypt(text));
                } else {
                    System.out.println("  Plain : " + 
                        Decryptor.New(Encryptor.VIGENERE_STANDARD)
                            .SetKey(key)
                            .Decrypt(text));
                }
                break;
            case 'b':

                break;
            case 'c':
                System.out.print("  Insert key: ");
                key = scanner.nextLine();
                if (method == METHOD_ENCRYPT) {
                    System.out.println("  Chiper : " + 
                        Encryptor.New(Encryptor.PLAYFAIR)
                            .SetKey(key)
                            .Encrypt(text));
                }                
                break;
            default:
                System.out.println("Wrong option!");
                return;
        }

    }

    private static void showHelp() {
        System.out.println("USAGE:");
        System.out.println("    method algorithm [input_mode] [file]\n");
        System.out.println("    method:");
        System.out.println("        e               Encryption mode, use this to encrypt text or file");
        System.out.println("        d               Decryption mode, use this to decrypt text or file\n");
        System.out.println("    algorithm:");
        System.out.println("        vigenere        Standard Vigenere Cipher (26 alphabet characters)");
        System.out.println("        vigenere-etd    Extend of Vigenere Cipher (256 ASCII characters)");        
        System.out.println("        playfair        Playfair Cipher (26 alphabet characters)\n");
        System.out.println("    input_mode: default = stdio");
        System.out.println("        --stdio         Standar input from terminal");
        System.out.println("        --text filename Read text from file eksternal");
        System.out.println("        --file filename Encrypt/decrypt file\n");
    }

}