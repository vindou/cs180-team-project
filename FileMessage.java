import java.io.File;

public class FileMessage extends Message {
    private File rawContent; 

    public FileMessage(User sender, File rawContent) {
        super(sender);
        this.rawContent = rawContent;
    }
    
    public File getImage() {
        return this.rawContent;
    }

    public void changeMessage(File newFile) {
        this.rawContent = newFile;
    }

    public boolean equals(FileMessage comparedMessage) {
        boolean equality = false;
        if (this.rawContent.equals(comparedMessage.getImage())
            && this.getIndex() == comparedMessage.getIndex()) {
            equality = true;
        }
        return equality;
    }

    public String toString() {
        return this.getSender().getUsername() 
                + ":["
                + this.getTimeSent().toString()
                + "]: \""
                + this.rawContent.toString()
                + "\"";
    }
}
