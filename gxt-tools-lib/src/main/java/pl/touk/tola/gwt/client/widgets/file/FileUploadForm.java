/*
 * Copyright (c) 2006-2007 TouK
 * All rights reserved
 */
package pl.touk.tola.gwt.client.widgets.file;


import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import pl.touk.tola.gwt.client.model.file.FileDescriptorGxt;

/**
 * Container with file upload form.
 * Uploads only one file.
 * Use with UploadDownloadController.
 * 
 * When file is uploaded, the onSubmit(..) method is invoked.
 * onSubmit(..) method shows proper messages to user.
 * 
 * To handle submit events in different way,
 * onSubmit(..) method should be overriden.
 * onSubmit takes as parameter statuses,
 * which are declared as static fields in FileUploadForm
 * 
 * TODO: move constant parameters to single common class
 * 
 */
public class FileUploadForm extends LayoutContainer {

    static public final int STATUS_SUCCESS = 0;
    static public final int STATUS_BAD_URL = 1;
    static public final int STATUS_NOT_VALID = 2;
    static public final int STATUS_CONTROLLER_ERROR = 3;
    
    static private final String CONTROLLER_UPLOAD = "upload=true";
    
    protected final String controllerUrl;
    protected final ContentPanel mainPanel;
    private final FormPanel form;
    private final FileUploadField fuf;
    private final Button uploadButton;

    private FileDescriptorGxt lastUploadedFileDescriptor;

    /**
     * Create FileUploadForm with standard settings.
     * Standard settings mean standard UploadDownloadController url.
     */
    public FileUploadForm() {
        this("fileUploadDownload.do", null);
    }

    /**
     * Creates form with fileupload form. 
     * 
     * 
     * @param controllerUrl url where to upload file (UploadDownloadController url)
     * @param headerText header text if null, no header will be displayed
     */
    public FileUploadForm(String controllerUrl, String headerText) {

        this.controllerUrl = controllerUrl;
    
        this.setLayout(new FitLayout());

        mainPanel = new ContentPanel();

        if (headerText != null) {
            mainPanel.setHeaderVisible(true);
            mainPanel.setHeading(headerText);
        } else {
            mainPanel.setHeaderVisible(false);
        }

        form = new FormPanel();
        form.setFrame(true);
        form.setAction(GWT.getModuleBaseURL() + controllerUrl + "?" + CONTROLLER_UPLOAD);
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
        form.setHeaderVisible(false);

        form.addListener(Events.Submit, new Listener<FormEvent>() {

            public void handleEvent(FormEvent fe) {
                GWT.log(fe.getResultHtml(), null);
                prepareSubmitResults(fe.getResultHtml());
            }
        });

        fuf = new FileUploadField();
        fuf.setFieldLabel("Plik");
        fuf.setName("fileuploadfield");

        uploadButton = new Button("Upload file", new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent arg0) {
                if (!form.isValid()) {
                    FileDescriptorGxt fdg = new FileDescriptorGxt();
                    // lkc: wykomentowalem to bo w 1.2.1 gxt nie ma juz tego wywolania
//                    fdg.setFileName(fuf.getFileName());
                    onSubmit(STATUS_NOT_VALID, "File upload field should not be empty", fdg);
                    return;
                }
                form.submit();
            }
        });
        
        form.add(fuf);
        form.addButton(uploadButton);

        mainPanel.add(form);
        this.add(mainPanel);
    }

    /**
     * Converts html to FileDescriptionBean
     */
    private void prepareSubmitResults(String resultHtml) {

        //handle errors
        //not found error
        if (resultHtml == null || resultHtml.contains("404")) {
            onSubmit(STATUS_BAD_URL, "Controller not found. Probably bad spring configuration.", null);
            return;
        }

        //internal server error ie. runtime error in controller
        if (resultHtml.contains("505")) {
            onSubmit(STATUS_CONTROLLER_ERROR, "Controller error. See server logs", null);
            return;
        }

        //controller error
        if (resultHtml.contains("Status 1:")) {
            onSubmit(STATUS_CONTROLLER_ERROR, resultHtml, null);
            return;
        }

        //no error, so prepare data
        //TODO: this should be done with xml
        String tempString = resultHtml.substring(5, resultHtml.length() - 6);

        tempString = replacePre(tempString);

        String[] fileStatus = tempString.split("\\|");

        //only one file can be uploaded, so take care of only one data row
        lastUploadedFileDescriptor = new FileDescriptorGxt();
        lastUploadedFileDescriptor.setFileId(fileStatus[1]);
        lastUploadedFileDescriptor.setFileName(fileStatus[0]);
        lastUploadedFileDescriptor.setFileSize(fileStatus[2]);

        onSubmit(STATUS_SUCCESS, "You file was uploaded", lastUploadedFileDescriptor);
    }

        String replacePre(String resultHtml) {
        return resultHtml.replaceAll("<.?pre[^>]*>","");
    }

    
    /**
     * Fired whenever submit is successful or not. 
     * Displays apropriate alerts due to status no.
     * 
     * Overide if you want to handle messages on your own, 
     * or if you want to handle bad or good submits.
     * 
     * @param status status 
     * @param message message to dispaly
     */
    
    protected void onSubmit(int status, String message, FileDescriptorGxt fdg) {
        switch (status) {
            case STATUS_SUCCESS:
                MessageBox.info("Submit status", message, null);
                break;
            case STATUS_BAD_URL:
                MessageBox.info("Error during file upload", message, null);
                break;
            case STATUS_NOT_VALID:
                MessageBox.info("Error during file upload", message, null);
                break;
            case STATUS_CONTROLLER_ERROR:
                MessageBox.info("Error during file upload", message, null);
                break;
        }
    }

    /**
     * Sets label for upload field
     * 
     * @param label new label
     */
    public void setFieldLabel(String label) {
        fuf.setFieldLabel(label);
    }

    /**
     * Sets label for uload button
     * 
     * @param text new label
     */
    public void setUploadButtonText(String text) {
        uploadButton.setText(text);
    }

    /**
     * Gets descriptor of last successfully uploaded file
     */
    public FileDescriptorGxt getLastUploadedFileDescriptor() {
        return lastUploadedFileDescriptor;
    }
}
