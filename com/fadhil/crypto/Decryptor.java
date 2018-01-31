package com.fadhil.crypto;

import java.nio.ByteBuffer;

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
        return this;
    }

    public Decryptor Decrypt(String chiper) {
        return Decrypt(chiper.toUpperCase().getBytes());
    }

    public Decryptor Decrypt(byte[] chiper) {
        this.chiper = chiper;
        if (choosenAlgorithm == Encryptor.VIGENERE_STANDARD) {
            DoVigenereDecrypt();
        }
        return main;
    }

    public void ShowPlain() {
        String plain = new String(chiper);
        System.out.println(plain);
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

}
