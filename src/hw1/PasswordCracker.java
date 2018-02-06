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
        ArrayList<String> list = new ArrayList<>();
        String lines[] = input.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) list.add(lines[i]);

        return list;
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


    public ArrayList<String> createPasswordGuesses(ArrayList<String> wordList){
        ArrayList<String> guessList = new ArrayList<>();
        for (Integer i = 1000; i < 1000000; i++) guessList.add((i.toString()));

        guessList.add("0000");
        guessList.add("000000");


        for (String word : wordList){
            guessList.add(word);

            if (word.length() == 4){
                for (Integer n = 0; n < 10; n++){
                    guessList.add(word.substring(0, 1).toUpperCase() + word.substring(1) + n);
                }
            }

            else if (word.length() == 5 && word.contains("e")){
                guessList.add(word.replace("e", "3"));
            }

        }


        return guessList;
    }

    public ArrayList<String> createHashes(ArrayList<String> guessList){
        ArrayList<String> hashList = new ArrayList<>();
        for (String s: guessList) hashList.add(hash(s));

        return hashList;
    }

    public void crack(String wordsFile, String usersFile){
        File out = new File("output");
        FileWriter writer;
        try {
            writer = new FileWriter(out);

            String words = readInput(wordsFile);
            String users = readInput(usersFile);

            ArrayList<String> wordList = parseInput(words);
            ArrayList<String> usersList = parseInput(users);

            ArrayList<String> passwordGuesses = createPasswordGuesses(wordList);
            ArrayList<String> wordListHashes = createHashes(passwordGuesses);
            ArrayList<String> passwordHashes = extractHashes(usersList);

            for (int i = 0; i < passwordHashes.size(); i++){
                for (int n = 0; n < wordListHashes.size(); n++) {
                    if (passwordHashes.get(i).equals(wordListHashes.get(n))){

                        System.out.println("Match found");
                        System.out.println(passwordHashes.get(i) + ":" + passwordGuesses.get(i));
                        writer.write(passwordHashes.get(i) + ":" + passwordGuesses.get(i) + "\n");
                    }
                }
            }

            writer.close();
        } catch (IOException e) {}



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

        c.crack(args[1], args[0]);

    }


}