package by.lozovenko.finalproject.model.entity;

public class Assistant extends User {
    private String imageFilePath;
    private long laboratoryId;
    private long assistantId;

    public Assistant() {
    }

    public Assistant(User user) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Assistant assistant = (Assistant) o;

        if (laboratoryId != assistant.laboratoryId) return false;
        if (assistantId != assistant.assistantId) return false;
        return imageFilePath != null ? imageFilePath.equals(assistant.imageFilePath) : assistant.imageFilePath == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (imageFilePath != null ? imageFilePath.hashCode() : 0);
        result = 31 * result + (int) (laboratoryId ^ (laboratoryId >>> 32));
        result = 31 * result + (int) (assistantId ^ (assistantId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Assistant{");
        sb.append("imageFilePath='").append(imageFilePath).append('\'');
        sb.append(", laboratoryId=").append(laboratoryId);
        sb.append(", assistantId=").append(assistantId);
        sb.append('}');
        return sb.toString();
    }
}
