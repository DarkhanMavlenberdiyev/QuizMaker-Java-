//package com.company;md





public class QuizMaker {

    public static void main(String[] args){

        Quiz quiz = new Quiz();
        quiz = Quiz.loadFromFile(args[0]);
        System.out.println(quiz);
        quiz.start();

    }
}











