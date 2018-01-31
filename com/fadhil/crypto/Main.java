package com.fadhil.crypto;

import java.util.*;
import java.lang.*;
import java.nio.file.Files;
import java.io.*;

public class Main {

    private static char METHOD_ENCRYPT = 'e';
    private static char METHOD_DECRYPT = 'd';
    private static String CRYPT_VIGENERE = "vigenere";
    private static String CRYPT_VIGENERE_EXTENDED = "vigenere-etd";
    private static String CRYPT_PLAYFAIR = "playfair";
    private static String INPUT_STDIO = "--stdio";
    private static String INPUT_TEXT = "--text";
    private static String INPUT_FILE = "--file";

    public static void main(String args[]) {
        Boolean isError = false;
        Scanner scanner = new Scanner(System.in);
        char method; String text = null, key = null; int algo;
        String filename, outfile = null;
        File file;

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
        } else if (!args[1].equals(CRYPT_VIGENERE) && !args[1].equals(CRYPT_VIGENERE_EXTENDED) && !args[1].equals(CRYPT_PLAYFAIR)) {
            System.out.println("Error: UNKNOWN ALGORITHM!"); isError = true;
        }

        if (isError) {
            showHelp();
            return;
        }

        // Handle input_mode
        if (args.length > 2) {
            Scanner fileScanner;

            try {
                
                if (args.length < 4) {
                    throw new Exception("Filename is required!");
                }

                if (args[2].equals(INPUT_STDIO)) {
                    outfile = args[3];
                } else if (args[2].equals(INPUT_TEXT)) {
                    filename = args[3]; file = new File(filename);
                    fileScanner = new Scanner(file);

                    StringBuilder sb = new StringBuilder();
                    while (fileScanner.hasNext()) {
                        sb.append(fileScanner.nextLine());
                    }
                    text = sb.toString();

                    if (args.length >= 5) {
                        outfile = args[4];
                    }

                } else if (args[2].equals(INPUT_FILE)) {
                    if (args.length < 5) {
                        throw new Exception("Output filename is required!");
                    }

                    filename = args[3]; outfile = args[4];
                    handleFile(filename, outfile);
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                showHelp();
                return;
            }
        }

        method = args[0].charAt(0);
        algo = args[1].equals(CRYPT_VIGENERE) ? 
                Encryptor.VIGENERE_STANDARD : 
                (args[1].equals(CRYPT_VIGENERE_EXTENDED) ? Encryptor.VIGENERE_EXTENDED : Encryptor.PLAYFAIR);
        
        // Read key and text
        System.out.print("Insert key    : ");
        key = scanner.nextLine();
        if (text == null) {
            System.out.print("Insert text   : ");
            text = scanner.nextLine();
        }

        if (method == METHOD_ENCRYPT) {
            Encryptor e = Encryptor.New(algo)
                .SetKey(key)
                .Encrypt(text);
            System.out.println("Chiper        : ");
            if (outfile == null) {
                e.ShowCipher();
            } else {
                try {
                    e.ExportCipher(outfile);
                    System.out.println("Saved to \"" + outfile +"\"");
                } catch (Exception ex) {
                    System.out.print("Error: " + ex.getMessage());
                }
            }
        } else {
            Decryptor d = Decryptor.New(Encryptor.VIGENERE_STANDARD)
                .SetKey(key)
                .Decrypt(text);
            System.out.println("Plain         : "); 
            d.ShowPlain();
        }

    }

    private static void showHelp() {
        System.out.println("USAGE:");
        System.out.println("    method algorithm [io_mode]\n");
        System.out.println("    method:");
        System.out.println("        e                          Encryption mode, use this to encrypt text or file");
        System.out.println("        d                          Decryption mode, use this to decrypt text or file\n");
        System.out.println("    algorithm:");   
        System.out.println("        vigenere                   Standard Vigenere Cipher (26 alphabet characters)");
        System.out.println("        vigenere-etd               Extend of Vigenere Cipher (256 ASCII characters)");        
        System.out.println("        playfair                   Playfair Cipher (26 alphabet characters)\n");
        System.out.println("    io_mode: [default=stdio]");
        System.out.println("        --stdio [output]           Standard from terminal");
        System.out.println("        --text  filename [output]  Read text from file eksternal");
        System.out.println("        --file  filename output    Encrypt/decrypt file\n");
    }

    private static void handleFile(String filename, String outfile) {
        byte byteBuffer[];

        try {
            byteBuffer = Files.readAllBytes(new File(filename).toPath());
        } catch (IOException e) {
            System.out.println("Error: Can not find file \"" + filename + "\"!");
            showHelp();
        }
         
    }

}