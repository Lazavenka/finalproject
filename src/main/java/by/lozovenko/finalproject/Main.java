package by.lozovenko.finalproject;

public class Main {
    public static void main(String[] args) {
        String token = "/finalproject_war_exploded/jsp/common/login.jsp";
        int idx = token.indexOf("ded");
        String newStr = token.substring(idx+3);
        System.out.println(token);
        System.out.println(newStr);
    }
}
