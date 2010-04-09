/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */

package pl.touk.tola.spring.mvc.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Mock implementation.
 * Only for testing - dirty and dummy ;-)
 * 
 * TODO: remove it from Tola!!!
 *
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class UploadDownloadProcessorTestingImpl implements UploadDownloadProcessor {
    protected static Log log = LogFactory.getLog(UploadDownloadProcessorTestingImpl.class);
    
    public String upload(FileItem file) {
        log.debug("In handler");
        log.debug("saving file:"+file.getString());
        return "handler-1";
    }

    public FileDescriptor getFileDescription(String fileId) {
        FileDescriptor fd = new FileDescriptor();
            fd.setFileId("333");
            fd.setFileName("dupa");
            fd.setFileSize(new Long(300));
            return fd;
    }

    public FileDescriptor save(InputStream fileStream) throws FileSaveException {
        try {
            log.debug("In handler");
            log.debug("saving file:" + fileStream.read());
            
        } catch (IOException ex) {
            Logger.getLogger(UploadDownloadProcessorTestingImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        FileDescriptor fd = new FileDescriptor();
            fd.setFileId("333");
            fd.setFileName("dupa");
            fd.setFileSize(new Long(300));
            return fd;
    }

    public InputStream download(FileDescriptor fd) {
        String text = "wyganiala kasia wołki";
        InputStream is = new ByteArrayInputStream(text.getBytes());
        
        return is;
    }

}
