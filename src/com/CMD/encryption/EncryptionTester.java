//package com.CMD.encryption;
//
//public class EncryptionTester {
//    public static void main(String[] args) {
//
//
//
//        try {
//            EncryptionUtil.init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("INIT CONFIGURATIONS SUCCESSFUL\n=====================================\n");
//        String OUTPUT_FORMAT = "%-30s:%s";
//        String PASSWORD = "55G4f8,42#";
//
//        String encryptedText = EncryptionUtil.encrypt(PASSWORD);
//
//        System.out.println("\n------ AES PKC5 Password-based Encryption ------");
//        System.out.println(String.format(OUTPUT_FORMAT, "Input (plain text)", PASSWORD));
//        System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (base64) ", encryptedText));
//
//        System.out.println("\n------ AES PKC5 Password-based Decryption ------");
//        System.out.println(String.format(OUTPUT_FORMAT, "Input (base64)", encryptedText));
//
//        String decryptedText = EncryptionUtil.decrypt(encryptedText);
//        System.out.println(String.format(OUTPUT_FORMAT, "Decrypted (plain text)", decryptedText));
//    }
//}
