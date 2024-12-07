package so.gbrl.files;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.security.MessageDigest;

class JournalEntry {
    enum OperationType {
        CREATE, MODIFY, DELETE, RENAME, PERMISSION_CHANGE
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    private LocalDateTime timestamp;
    private OperationType operationType;
    private Path filePath;
    private String previousState;
    private String currentState;
    private String fileHash;

}

class FileJournalManager {
    private static final String JOURNAL_FILE = "file_journal.log";
    private Path journalPath;

    public FileJournalManager() {
        journalPath = Paths.get(JOURNAL_FILE);

        if (!Files.exists(journalPath)) {
            try {
                Files.createFile(journalPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logOperation(JournalEntry entry) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                journalPath,
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE
        )) {
            String logEntry = serializeJournalEntry(entry);
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String calculateFileHash(Path path) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] fileBytes = Files.readAllBytes(path);
            byte[] hashBytes = digest.digest(fileBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void recover() {
    }

    private String serializeJournalEntry(JournalEntry entry) {
        return String.format(
                "%s,%s,%s,%s,%s",
                entry.getTimestamp(),
                entry.getOperationType(),
                entry.getFilePath(),
                entry.getPreviousState(),
                entry.getCurrentState()
        );
    }
}

class FileManager {
    private FileJournalManager journalManager;

    public FileManager() {
        journalManager = new FileJournalManager();
    }

    public void createFile(Path path) {
        try {
            Files.createFile(path);

            JournalEntry entry = new JournalEntry();
            entry.setOperationType(JournalEntry.OperationType.CREATE);
            entry.setFilePath(path);
            entry.setTimestamp(LocalDateTime.now());

            journalManager.logOperation(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}