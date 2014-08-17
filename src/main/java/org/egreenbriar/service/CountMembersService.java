package org.egreenbriar.service;

import java.io.File;
import java.io.FilenameFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CountMembersService {
    
    @Value("${storage.directory}")
    private String storageDirectory = null;
    
    public String[] getStorageDirectories() {
        File file = new File(getStorageDirectory());
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories;
    }

    public String getStorageDirectory() {
        return storageDirectory;
    }

    public void setStorageDirectory(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }

}
