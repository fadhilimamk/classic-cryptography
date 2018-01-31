package com.fadhil.crypto;

import java.nio.ByteBuffer;
import java.util.LinkedHashSet;
import java.util.Set;
import java.io.*;

public class Encryptor {

    public static int VIGENERE_STANDARD = 1;
    public static int VIGENERE_EXTENDED = 2;
    public static int PLAYFAIR = 3;

    private byte[] plain;
    private String key, space;
    private int choosenAlgorithm;
    public static Encryptor main;

    public static Encryptor New(int name) {
        main = new Encryptor();
        main.choosenAlgorithm = name;
        return main;
    }

    public Encryptor SetKey(String key) {
        this.key = key.toUpperCase();
        this.key = this.key.replaceAll("\\s+","");
        return this;
    }

    public static byte[] stringToBytesASCII(String str) {
        byte[] b = new byte[str.length()];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) str.charAt(i);
        }
        return b;
    }

    public Encryptor Encrypt(String plain) {
        plain = plain.toUpperCase(); space = plain;
        if (choosenAlgorithm == PLAYFAIR) {
            plain = plain.replace('J', 'I');
        } else if (choosenAlgorithm == VIGENERE_STANDARD) {
            StringBuilder builder = new StringBuilder();

            for (char ch : plain.toCharArray()) 
                if (Character.isAlphabetic(ch)) 
                    builder.append(ch);

            plain = builder.toString();
        }
        Encrypt(stringToBytesASCII(plain));
        return main;
    }

    public Encryptor Encrypt(byte[] plain) {
        this.plain = plain;
        if (choosenAlgorithm == VIGENERE_STANDARD) {
            DoVigenereEncrypt();
        } else if (choosenAlgorithm == VIGENERE_EXTENDED) {
            DoVigenereExtendEncrypt();
        } else if (choosenAlgorithm == PLAYFAIR) {
            DoPlayFairEncrypt();
        }
        return main;
    }

    public void ShowCipher() {
        String cipher = new String(plain);
        int cipherLength = cipher.length(), j = 0;

        for(int i = 0; i < space.length(); i++) {
            if (!Character.isAlphabetic(space.charAt(i))) {
                System.out.print(space.charAt(i));
            } else {
                System.out.print(cipher.charAt(j));
                j++;
            }
        }
        System.out.println();

        System.out.println(cipher);
        for(int i = 0; i < cipherLength; i++) {
            if (i % 5 == 0 && i != 0) {
                System.out.print(" ");
            }
            System.out.print(cipher.charAt(i));
        }
        System.out.println();
    }

    public void ExportCipher(String outfile) throws IOException {
        FileOutputStream fos = new FileOutputStream(outfile);
        for (int i = 0; i < plain.length; i++)
            System.out.printf("%d ", plain[i]);
        System.out.println();
        fos.write(plain);
        fos.close();
    }

    private void DoVigenereEncrypt() {
        int j = 0, i = 0, keyLength = key.length();

        for (i = 0; i < plain.length; i++) {
            plain[i] = (byte) (((plain[i] + key.charAt(j) - 2*65)%26) + 65);
            j = (j + 1)%keyLength;
        }
        
    }

    private void DoVigenereExtendEncrypt() {
        int j = 0, i = 0, keyLength = key.length();

        for (i = 0; i < plain.length; i++) {
            System.out.printf("%d -> %d : ", plain[i], ((plain[i] + key.charAt(j)) % 256));
            plain[i] = (byte) ((plain[i] + key.charAt(j)) % 256);
            System.out.printf("%d\n", plain[i]);
            j = (j + 1)%keyLength;
        }
    }

    private void DoPlayFairEncrypt() {
        // Prepare key matrix
        key += "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        key = UniqueKey(key);
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

        // Encryption process
        int i = 0;
        while (i < plain.length) {
            boolean same = false;
            char x = (char)plain[i], y = (char)( i+1 == plain.length ? 'Z' : plain[i+1]);
            if (x == y) {
                y = 'Z';
                same = true;
            }
            System.out.print((char)x + "" + (char)y);

            int a = 0, b = 0, c = 0, d = 0; Character firstFound = null;
            boolean finish = false;
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
            // Create cipher
            System.out.print("->"+keyMatrix[a][d] + keyMatrix[c][b] + " ");

            i+=2;
            if (same) {
                i-=1;
            }
        }

        System.out.println();

    }

    private String UniqueKey(String string) {
        char[] chars = string.toCharArray();

        Set<Character> charSet = new LinkedHashSet<Character>();
        for (char c : chars) {
            charSet.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (Character character : charSet) {
            sb.append(character);
        }

        return sb.toString();
    }

}
