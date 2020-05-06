package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
//        MyHashMap<String,String> table = new MyHashMap<>(100);
//
//        for(int i=0;i<100;i++){
//            table.put(Integer.toString(i),Integer.toString(i));
//        }
//
//        table.printAll();
//        for(int i=0;i<100;i++) {
//            System.out.println(i + " " + table.get(Integer.toString(i)));
//        }
//        System.out.println();



        int numberOfWords = 128;
        MyHashMap<String,String> table = getDictionary(numberOfWords);

//        table.printAll();
        for(int i=0;i<numberOfWords;i++){
            table.get(words[i]);
        }
//        System.out.println(Arrays.toString(words));
//        table.printAll();
        System.out.println((float)(table.totalCollisionCounter)/numberOfWords);

    }



    static String [] words;
    private static MyHashMap<String, String> getDictionary (int numberOfWords) throws FileNotFoundException {
        MyHashMap<String, String> dictionary = new MyHashMap<>(numberOfWords);
        File file = new File("dict_processed.txt");
        Scanner scanner = new Scanner(file);
        words = new String[numberOfWords];

        for(int j=0;j<numberOfWords;j++){
            String line = scanner.nextLine();
            int i = line.indexOf(';');
            String keyWord = line.substring(0, i);
            String defWord = line.substring(i+1);
            dictionary.put(keyWord, defWord);
            words[j] = keyWord;
        }
        //dictionary.printAll();
        return dictionary;
    }
}
