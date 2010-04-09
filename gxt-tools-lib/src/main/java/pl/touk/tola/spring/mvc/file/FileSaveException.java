/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */

package pl.touk.tola.spring.mvc.file;

/**
 * This exception should be thrown when any problems (except IO) occurs during
 * file saving. 
 *
 * 
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class FileSaveException extends Exception {

    public FileSaveException(String message) {
        super(message);
    }
    

}
