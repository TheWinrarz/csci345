package hw1;

import java.io.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class PasswordCracker {


    MessageDigest md;

    public PasswordCracker(){

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {}

    }

    public String readInput(String filename) {

        String fullText = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = in.readLine();

            while (line != null){
                fullText = fullText + line + "\n";
                line = in.readLine();

            }

        } catch (IOException e) {e.printStackTrace();}

        System.out.println(fullText);

        return fullText;
    }

    public ArrayList<String> parseInput(String input){
        ArrayList<String> users = new ArrayList<>();
        return users;
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

        //The first four passwords
        System.out.println(c.hash("1234"));
        System.out.println(c.hash("Hair6"));
        System.out.println(c.hash("b3ach"));
        System.out.println(c.hash("9wXy!"));


        c.readInput(args[0]);
    }


}