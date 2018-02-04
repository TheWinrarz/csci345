package hw1;

import java.io.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class PasswordCracker {


    MessageDigest md;

    //constructor to create MessageDigest MD5 instance
    public PasswordCracker(){

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {}

    }

    //returns a string that contains the entire contents of the file
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


        return fullText;
    }

    //returns ArrayList where each element is a line of the input
    public ArrayList<String> parseInput(String input){
        ArrayList<String> users = new ArrayList<>();
        String lines[] = input.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) users.add(lines[i]);

        return users;
    }

    //returns an ArrayList containing just the hash of each of the lines in the password file
    public ArrayList<String> extractHashes(ArrayList<String> users){
        ArrayList<String> passwordHashes = new ArrayList<>();
        String[] lineArray;

        for (int i = 0; i < users.size(); i++){
            lineArray = users.get(i).split(":");
            passwordHashes.add(lineArray[1]);
        }

        return passwordHashes;

    }


    public ArrayList<String> createHashes(ArrayList<String> wordList){
        ArrayList<String> hashList = new ArrayList<>();

        for (String word : wordList){
            hashList.add(hash(word));
        }

        return hashList;
    }

    //returns MD5 hash of preimage argument
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


        System.out.println(c.extractHashes(c.parseInput(c.readInput(args[0]))));

        ArrayList<String> wordListHashes = c.createHashes(c.parseInput(c.readInput(args[1])));
        for (String s : wordListHashes){
            System.out.println(s);
        }


    }


}