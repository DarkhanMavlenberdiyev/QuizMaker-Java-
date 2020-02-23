//package com.company;

import java.util.ArrayList;

public class Test extends Question{
    String[] options;
    int numOfOptions;
    ArrayList<Character> label = new ArrayList<>();
    Test(){
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
    String getOptionAt(int numOfOptions){

        return label.get(numOfOptions)+") "+options[numOfOptions];
    }
    public String toString()
    {
        return super.getDescription();
    }

}