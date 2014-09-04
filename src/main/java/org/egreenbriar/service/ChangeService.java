package org.egreenbriar.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChangeService {

    private final static Charset ENCODING = StandardCharsets.UTF_8;

    @Value("${changes.file}")
    private String changesFile = null;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");

    public Set<String> getChanges() throws IOException {
        Path fFilePath = Paths.get(changesFile);
        Set<String> changes = new TreeSet<>();
        try (Scanner scanner = new Scanner(fFilePath, ENCODING.name())) {
            while (scanner.hasNextLine()) {
                changes.add(scanner.nextLine());
            }
        }
        return changes;
    }

    public synchronized void logChange(final String action, final String message) throws IOException {
        String now = dateFormatter.format(new Date());
        FileUtils.writeStringToFile(new File(changesFile), String.format("%s: %s^%s\n", now, action, message), true);
    }

    public void setChangesFile(String changesFile) {
        this.changesFile = changesFile;
    }

    public String findLastChangeForPerson(String personUuid) throws IOException {
        String dateUpdated = "Unknown";
        Set<String> changes = getChanges();
        // the last change we see is the last change.
        for (String change : changes) {
            if (change.contains(personUuid)) {
                dateUpdated = change.substring(0, 10);
            }
        }
        return dateUpdated;
    }

}
