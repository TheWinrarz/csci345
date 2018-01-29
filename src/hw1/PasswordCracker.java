package hw1;

import java.io.File;

//USE DigestUtils first
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


class PasswordCracker {

    static File passwordFile;

    static MessageDigest md;

    public PasswordCracker(){

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {}
    }

    private static void readInput(String filename){

        passwordFile = new File(filename);


    }


    public static String hash(String preimage){

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
        System.out.println(hash("1234"));
        System.out.println(hash("Hair6"));
        System.out.println(hash("b3ach"));
        System.out.println(hash("9wXy!"));
    }


}