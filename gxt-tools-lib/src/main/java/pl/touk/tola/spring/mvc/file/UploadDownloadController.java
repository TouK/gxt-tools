/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */
package pl.touk.tola.spring.mvc.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *  TODO: document this...
 * 
 *  Standard spring configuration:
 *   <bean id="fileUploadController" class="pl.touk.tola.spring.mvc.file.UploadDownloadController">
 *          <property name="uploadDownloadProcessor" ref="<UploadDownloadProcessor implementation>"/>
 *   </bean>
 *
 * @author Rafał Pietrasik rpt@touk.pl
 */
public class UploadDownloadController extends AbstractController {

    //TODO: move it to single common class, that view could use it
    public static String ERROR_PREFIX = "STATUS 1:";
    public static String SUCCESS_PREFIX = "STATUS 0:";
    public static String PARAM_UPLOAD = "upload";
    public static String PARAM_DOWNLOAD = "download";
    public static String PARAM_GET_INFO = "info";
    
    protected static Log log = LogFactory.getLog(UploadDownloadController.class);
    
    private UploadDownloadProcessor uploadDownloadProcessor;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        boolean isUpload = request.getParameter(PARAM_UPLOAD) != null && "true".equals(request.getParameter(PARAM_UPLOAD));
        boolean isDownload = request.getParameter(PARAM_DOWNLOAD) != null && "true".equals(request.getParameter(PARAM_DOWNLOAD));
        boolean isGetInfo = request.getParameter(PARAM_GET_INFO) != null && "true".equals(request.getParameter(PARAM_GET_INFO));

        if (isUpload) {
            log.debug("Uploading file mode");
            if (isMultipart) {
                log.debug("Uploading file...");
                ArrayList<String> processingResults = processUploadedFiles(request);
                createResponse(response, processingResults);
                log.debug("Uploading successful.");
            } else {
                log.debug(ERROR_PREFIX + "Error during file upload. It seem no file at all.");
                createResponse(response, ERROR_PREFIX + "Error during file upload. It seem no file at all.");
            }

        } else if (isDownload) {
            log.debug("Downloading file mode");

            FileDescriptor fd = getRequestParams(request);

            InputStream downloadedFile = uploadDownloadProcessor.download(fd);

            response.addHeader("Content-Type", "application/txt");
            response.setHeader("Content-Disposition", "attachment; filename=" + fd.getFileName());  //TODO: get fileName from uploadDownloadProcessor.getFileDescription 

            org.apache.commons.io.IOUtils.copy(downloadedFile, response.getOutputStream());

        } else if (isGetInfo) {
            log.debug("Info file mode");
            FileDescriptor fd = getRequestParams(request);
            FileDescriptor fileInfo = uploadDownloadProcessor.getFileDescription(fd.getFileId());
            createResponse(response, fileInfo.getFileId() + "|" + fileInfo.getFileName() + "|" + fileInfo.getFileSize());
        }

        return null;
    }

    private void createResponse(HttpServletResponse response, String msg) throws IOException {
        Writer w = new OutputStreamWriter(response.getOutputStream());
        w.write(msg);
        w.close();
    }

    private void createResponse(HttpServletResponse response, ArrayList<String> messages) throws IOException {
        Writer w = new OutputStreamWriter(response.getOutputStream());
        for (String msg : messages) {
            w.write(msg);
        }
        w.close();
    }

    private FileDescriptor getRequestParams(HttpServletRequest request) {
        String fileId = request.getParameter("fileId");
        String fileName = request.getParameter("fileName");

        FileDescriptor fd = new FileDescriptor();
        fd.setFileId(fileId);
        fd.setFileName(fileName);

        return fd;
    }

    private ArrayList<String> processUploadedFiles(HttpServletRequest request) {
        ArrayList<String> returnMsgs = new ArrayList<String>();

        try {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            log.debug("files no:" + items.size());

            // Process the uploaded items
            for (FileItem fi : items) {
                if (!fi.isFormField()) {
                    FileDescriptor fd = uploadDownloadProcessor.save(fi.getInputStream());
                    returnMsgs.add(fi.getName() + "|" + fd.getFileId() + "|" + fi.getSize());
                    log.debug("Saving file:" + fi.getName() + " status:" + fd.getFileId());
                }
            }
            return returnMsgs;


        } catch (IOException ex) {
            log.error("Error during file upload", ex);
            returnMsgs.add(ERROR_PREFIX + "IO Exception during file upload");
        } catch (FileSaveException ex) {
            log.error("Error during file upload", ex);
            returnMsgs.add(ERROR_PREFIX + "Exception during saving file.");
        } catch (FileUploadException ex) {
            log.error("Error during file upload", ex);
            returnMsgs.add(ERROR_PREFIX + "Error during file upload");
        }

        return returnMsgs;
    }

    public UploadDownloadProcessor getUploadDownloadProcessor() {
        return uploadDownloadProcessor;
    }

    public void setUploadDownloadProcessor(UploadDownloadProcessor uploadDownloadHandler) {
        this.uploadDownloadProcessor = uploadDownloadHandler;
    }
}
