package by.lozovenko.finalproject.util.mail;

public class MailMessageBuilder {
    private static final String PATH = "http://localhost:8080/finalproject_war_exploded/controller?command=confirm_registration_command&token=";
    public static String buildMessage(String token){
        StringBuilder builder = new StringBuilder();
        builder.append("\tGreetings dear customer of Research Center Project");
        builder.append("\n\n\tTo confirm registration, please open the link:");
        builder.append("\n").append(PATH).append(token);
        builder.append("\n\n\tIn case you didn't register on our site, please ignore this message.");
        builder.append("\n\n Best regards, administration of Research Center!");
        return builder.toString();
    }
}
