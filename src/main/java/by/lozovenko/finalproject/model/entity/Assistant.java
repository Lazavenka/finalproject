package by.lozovenko.finalproject.model.entity;

public class Assistant extends User{
    private String imageFilePath;
    private long laboratoryId;
    private long assistantId;
    public Assistant(){
    }
    public Assistant(User user){
        super(user);
    }
    public Assistant(long id, String login, String password, String firstName, String lastName,
                     String email, String phone, UserRole role, UserState state,
                     String imageFilePath, long laboratoryId, long assistantId) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.imageFilePath = imageFilePath;
        this.laboratoryId = laboratoryId;
        this.assistantId = assistantId;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public long getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(long laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public long getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(long assistantId) {
        this.assistantId = assistantId;
    }
}
