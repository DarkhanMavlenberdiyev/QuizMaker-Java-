//package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Quiz {
    int res = 0;
    Scanner in = new Scanner(System.in);
    private String name;
    private ArrayList<Question> questions;

    Quiz() {
        questions = new ArrayList<>();

    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void addQuestion(Question question) {
        this.questions.add(question);

    }

    static Quiz loadFromFile(String s) {
        Random random = new Random();
        Quiz quiz = new Quiz();
        try {

            quiz.setName(s);
            File file = new File(s);
            if (!file.exists()) {
                throw new InvalidQuizFormatException();
            } else {
                Scanner in = new Scanner(file);
                String l = "";
                while (in.hasNextLine()) {
                    l += in.nextLine() + "\n";
                }
                String[] lis = l.split("\n\n");
                for (int i = 0; i < lis.length; i++) {
                    if (lis[i].contains("{blank}")) {
                        Question fil = new Fillin();
                        String[] li = lis[i].split("\n");
                        fil.setDescription(li[0].replace("{blank}", "_____"));
                        fil.setAnswer(li[1]);
                        quiz.addQuestion(fil);
                    } else {
                        Question test = new Test();
                        String[] li = lis[i].split("\n");
                        test.setDescription(li[0]);
                        test.setAnswer(li[1]);
                        String[] op = new String[li.length - 1];
                        for (int k = 0, e = 65; k < op.length; k++, e++) {
                            ((Test) test).label.add((char) e);
                        }
                        for (int k = 0; k < op.length; k++) {
                            op[k] = li[k + 1];
                        }
                        String[] ops = new String[op.length];
                        ArrayList<Integer> ran = new ArrayList<>();
                        int a = 0;
                        while (a <= ops.length - 1) {
                            int b = random.nextInt(4);
                            if (a == 0) {
                                ops[a] = op[b];
                                ran.add(b);
                                a++;
                            } else if (!ran.contains(b)) {
                                ops[a] = op[b];
                                ran.add(b);
                                a++;

                            }
                        }

                        ((Test) test).setOptions(ops);
                        for (int g = 0; g < ops.length; g++) {
                            if (ops[g].contains(op[0]))
                                test.setAnswer(((Test) test).getOptionAt(g));
                        }
                        quiz.addQuestion(test);

                    }
                }
            }
        } catch (InvalidQuizFormatException e) {
            System.out.println(e);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return quiz;
    }

    public String toString() {
        return "WELCOME TO " + "\"" + name + "\" QUIZ\n-----------------------------------------------\n";
    }

    void start() {
        questions = peremeshat(questions);

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i) instanceof Fillin) {
                System.out.println((i + 1) + ") " + questions.get(i).getDescription());
                String fi = in.nextLine();
                if (fi.trim().toLowerCase().equals(questions.get(i).getAnswer().toLowerCase())) {
                    System.out.println("Correct\n-----------------------------------------------");
                    res++;
                } else
                    System.out.println("Incorrect\n-----------------------------------------------");
            } else if (questions.get(i) instanceof Test) {
                System.out.println((i + 1) + ") " + questions.get(i).getDescription());
                for (int k = 0; k < 4; k++) {
                    System.out.println(((Test) questions.get(i)).getOptionAt(k));
                }
                while (true) {
                    System.out.print("Enter the correct choice: ");
                    String ans = in.nextLine();
                    char q = ans.charAt(0);
                    if (q == questions.get(i).getAnswer().charAt(0)) {
                        System.out.println("Correct\n-----------------------------------------------");
                        res++;
                        break;
                    } else if (!Character.isAlphabetic(q) || !Character.isUpperCase(q))
                        System.out.print("Invalid choice!  Try again(Ex: A, B, ...): ");
                    else {
                        System.out.println("Incorrect\n-----------------------------------------------");
                        break;
                    }
                }


            }
        }
        System.out.print("Correct Answers: " + res + "/5 (" + ((double) ((res * 100) / 5)) + "%)");


    }

    public ArrayList<Question> peremeshat(ArrayList<Question> list) {
        Random ran = new Random();
        ArrayList<Question> q = new ArrayList<>();
        ArrayList<Integer> nums = new ArrayList<>();

        int i = 0;
        while (i < list.size()) {

            int ra = ran.nextInt(5);

            if (i == 0) {
                q.add(list.get(ra));
                nums.add(ra);
                i++;
            }
            else if (!nums.contains(ra)){
                q.add(list.get(ra));
                nums.add(ra);
                i++;
            }
        }
        return q;

    }

}