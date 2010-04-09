/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.touk.tola.spring.mvc.file;

import java.io.InputStream;

/**
 * File processor interface.
 * Methods are invoked by FileUploadDownloadController.
 * Methods should be implemented as it is said in comments.
 *
 * @author Rafał Pietrasik rpt@touk.pl
 */
public interface UploadDownloadProcessor {
    
    /**
     * Saves file.
     * Invoked by controller.
     * 
     * @param fileStream OutputStream with file
     * @return fileId
     * @throws pl.touk.carrier2012.spring.mvc.FileSaveException
     */
    public FileDescriptor save(InputStream fileStream) throws FileSaveException;
    
    /**
     * File download.
     * Invoked by controller.
     * 
     * @param fileDescriptor description of file to download
     * @return file inputstream to be downloaded
     */
    public InputStream download(FileDescriptor fileDescriptor);
    
    
    /**
     * Gets file description.
     * Invoked by controller.
     * 
     * @param fileId id of file to get more info
     * @return file additional file informations
     */
    public FileDescriptor getFileDescription(String fileId);

}
