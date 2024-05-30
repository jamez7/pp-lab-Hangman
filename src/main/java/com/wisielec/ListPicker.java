package com.wisielec;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ListPicker {
    public String filepath = "src\\main\\resources\\WordLists\\food.txt";
    public String returnword(String filepath) throws FileNotFoundException {

        Scanner scannerlisty = new Scanner(new File(filepath));

        List<String> words = new ArrayList<>();

        while(scannerlisty.hasNext()) {
           words.add(scannerlisty.nextLine());
        }

        Random rand = new Random();

        String word = words.get(rand.nextInt(words.size()));

        return word;


    }


}
