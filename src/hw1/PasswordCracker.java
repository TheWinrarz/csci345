package hw1;

import java.io.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


class PasswordCracker {

    File passwordFile;

    MessageDigest md;

    public PasswordCracker(){

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {}

    }

    public void readInput(String filename) {

        try {
            String fullText = "";
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = in.readLine();

            while (line != null){
                fullText = fullText + line + "\n";
                line = in.readLine();

            }

            System.out.println(fullText);

        } catch (IOException e) {e.printStackTrace();}
    }


    public String hash(String preimage){

        String digest = "";

        try {

            byte[] preimageByteArray = preimage.getBytes("US-ASCII");
            byte[] digestByteArray = md.digest(preimageByteArray);

            BigInteger bigInt = new BigInteger(1,digestByteArray);
            digest = bigInt.toString(16);

            //zero padding the digest
            while(digest.length() < 32 ){
                digest = "0" + digest;
            }

            digest = digest.toUpperCase();


        } catch (UnsupportedEncodingException e) {}


        return digest;
    }

    public static void main(String[] args){

        PasswordCracker c = new PasswordCracker();
        System.out.println(c.hash("1234"));
        c.readInput("C:\\Users\\Daniel Lee\\Desktop\\School\\CSCI\\CSCI 345\\homework\\csci345\\src\\hw1\\passwords");
    }


}