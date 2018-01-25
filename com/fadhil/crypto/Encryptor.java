package com.fadhil.crypto;

import java.nio.ByteBuffer;

public class Encryptor {

    public static int VIGENERE_STANDARD = 1;
    public static int VIGENERE_EXTENDED = 2;
    public static int PLAYFAIR = 3;

    private byte[] plain;
    private String key;
    private int choosenAlgorithm;
    public static Encryptor main;

    public static Encryptor New(int name) {
        main = new Encryptor();
        main.choosenAlgorithm = name;
        return main;
    }

    public Encryptor SetKey(String key) {
        this.key = key.toUpperCase();
        return this;
    }

    public String Encrypt(String plain) {
        return Encrypt(plain.toUpperCase().getBytes());
    }

    public String Encrypt(byte[] plain) {
        this.plain = plain;
        if (choosenAlgorithm == VIGENERE_STANDARD) {
            DoVigenereEncrypt();
            return new String(plain);
        }
        return "INIACAK";
    }

    private void DoVigenereEncrypt() {
        int j = 0, i = 0, keyLength = key.length();

        for (i = 0; i < plain.length; i++) {
            plain[i] = (byte) (((plain[i] + key.charAt(j) - 2*65)%26) + 65);
            j = (j + 1)%keyLength;
        }
        
    }

}
