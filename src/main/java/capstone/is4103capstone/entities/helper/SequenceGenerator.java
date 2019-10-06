package capstone.is4103capstone.entities.helper;

import javax.swing.*;

public class SequenceGenerator {

    public static void main(String[] args){
        String c = "CamelCase";
        String underscore = c.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
        System.out.println(underscore);
    }

    public void generateSeq(Class c){

    }
}
