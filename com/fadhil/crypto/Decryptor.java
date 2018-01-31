package com.fadhil.crypto;

import java.nio.ByteBuffer;
import java.io.*;

public class Decryptor {

    private byte[] chiper;
    private String key;
    private int choosenAlgorithm;
    public static Decryptor main;

    public static Decryptor New(int name) {
        main = new Decryptor();
        main.choosenAlgorithm = name;
        return main;
    }

    public Decryptor SetKey(String key) {
        this.key = key.toUpperCase();
        this.key = this.key.replaceAll("\\s+","");
        return this;
    }

    public Decryptor Decrypt(byte[] chiper) {
        this.chiper = chiper;
        if (choosenAlgorithm == Encryptor.VIGENERE_STANDARD) {
            DoVigenereDecrypt();
        } else if (choosenAlgorithm == Encryptor.VIGENERE_EXTENDED) {
            DoVigenereExtendedDecrypt();
        } else {
            DoPlayFairDecrypt();
        }
        return main;
    }

    public void ShowPlain() {
        String plain = new String(chiper);
        System.out.println(plain);
    }

    public void ExportPlain(String outfile) throws IOException {
        FileOutputStream fos = new FileOutputStream(outfile);
        fos.write(chiper);
        fos.close();
    }

    private void DoVigenereDecrypt() {
        int j = 0, i = 0, keyLength = key.length();

        for (i = 0; i < chiper.length; i++) {
            byte diff = (byte)(chiper[i] - key.charAt(j));
            if (diff < 0) {
                diff = (byte) (diff + 26);
            }
            chiper[i] = (byte) ((diff%26) + 65);
            j = (j + 1)%keyLength;
        }
    }

    private void DoVigenereExtendedDecrypt() {
        int j = 0, i = 0, keyLength = key.length();
        for (i = 0; i < chiper.length; i++) {
            int intch = chiper[i] & 0xFF; 
            int diff = intch - key.charAt(j);
            if (diff < 0) {
                diff += 256;
            }
            chiper[i] = (byte) (diff%256);
            j = (j + 1)%keyLength;
        }
    }

    private void DoPlayFairDecrypt() {
        // Prepare key matrix
        key += "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        key = Encryptor.UniqueKey(key);
        char[][] keyMatrix = new char[6][6];
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyMatrix[i][j] = key.charAt(k++);
            }   
        }
        // extend matrix
        for (int i = 0; i < 5; i++) {
            keyMatrix[5][i] = keyMatrix[0][i];
            keyMatrix[i][5] = keyMatrix[i][0];
        }
        keyMatrix[5][5] = ' ';

        // Print matrix
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(keyMatrix[i][j] + " ");
            }   
            System.out.println();
        }

        // Decryption Process 
        int i = 0;
        while (i < chiper.length) {
            char x = (char)chiper[i], y = (char)chiper[i+1];
            int a = 0, b = 0, c = 0, d = 0; Character firstFound = null;
            boolean finish = false;
            
            System.out.print((char)x + "" + (char)y);
            for (int j = 0; j < 6 && !finish; j++) {
                for (k = 0; k < 6 && !finish; k++) {
                    if (keyMatrix[j][k] == x || keyMatrix[j][k] == y) {
                        if (firstFound == null) {
                            firstFound = keyMatrix[j][k];
                            if (firstFound == x) {
                                a = j; b = k;
                            } else {
                                c = j; d = k;    
                            }
                        } else if (keyMatrix[j][k] != firstFound) {
                            if (firstFound == y) {
                                a = j; b = k;
                            } else {
                                c = j; d = k;    
                            }
                            finish = true;
                        }
                    }
                }
            }

            if (b == d) {
                chiper[i] = (byte) keyMatrix[a][b-1];
                chiper[i+1] = (byte) keyMatrix[c][d-1];    
            } else if (a == c) {
                chiper[i] = (byte) keyMatrix[a-1][b];
                chiper[i+1] = (byte) keyMatrix[c-1][d];    
            } else {
                chiper[i] = (byte) keyMatrix[a][d];
                chiper[i+1] = (byte) keyMatrix[c][b];
            }
            System.out.print("->"+(char)chiper[i] +""+ (char)chiper[i+1] + " ");

            i+=2;
        }
    }

}
