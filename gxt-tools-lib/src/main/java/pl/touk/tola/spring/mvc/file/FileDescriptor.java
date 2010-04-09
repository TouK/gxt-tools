/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */

package pl.touk.tola.spring.mvc.file;

/**
 * File descriptor used in file upload and download operations.
 * 
 * TODO: add group property
 *
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class FileDescriptor {
    
    private String fileName;
    private String fileId;
    private Long fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    

}
