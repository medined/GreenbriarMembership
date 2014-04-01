package org.egreenbriar.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChangeService {
    
    @Value("${changes.file}")
    private String changesFile = null;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
    
    public synchronized void logChange(final String action, final String message) throws IOException {
        String now = dateFormatter.format(new Date());
        FileUtils.writeStringToFile(new File(changesFile), String.format("%s: %s^%s\n", now, action, message), true);
    }
    
    public void setChangesFile(String changesFile) {
        this.changesFile = changesFile;
    }

}
