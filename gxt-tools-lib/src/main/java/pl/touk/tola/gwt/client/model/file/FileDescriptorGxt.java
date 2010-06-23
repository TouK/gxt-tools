/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */
package pl.touk.tola.gwt.client.model.file;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.io.Serializable;

/**
 * This bean represents uploaded file.
 * Used by StatusGrid.
 * 
 * Mainly this is copy of pl.touk.tola.spring.mvc.file.FileDescriptor adapted to
 * use in GXT.
 * 
 * TODO: should be auto-generated from pl.touk.tola.spring.mvc.file.FileDescriptor 
 * 
 *
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class FileDescriptorGxt extends BaseModel implements Serializable {

    public static String FILE_ID = "fileId";
    public static String FILE_NAME = "fileName";
    public static String FILE_SIZE = "fileSize";

    public String getFileId() {
        return get(FILE_ID);
    }

    public void setFileId(String fileId) {
        set(FILE_ID, fileId);
    }

    public String getFileName() {
        return get(FILE_NAME);
    }

    public void setFileName(String fileName) {
        set(FILE_NAME, fileName);
    }

    public String getFileSize() {
        return get(FILE_SIZE);
    }

    public void setFileSize(String fileSize) {
        set(FILE_SIZE, fileSize);
    }
}
