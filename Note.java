import java.time.LocalDateTime;

public class Note {

    //fields
    private final String author;
    private final String text;
    private final LocalDateTime createdAt;

    //Constructor
    public Note(String author, String text) {
        this.author = author;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    //Getters
    public String getAuthor() { return author; }
    public String getText() { return text; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    //Other Methods
    @Override
    public String toString() {
        return "[" + createdAt.toLocalDate() + " "
                + createdAt.toLocalTime().withNano(0)
                + "] " + author + ": " + text;
    }
}