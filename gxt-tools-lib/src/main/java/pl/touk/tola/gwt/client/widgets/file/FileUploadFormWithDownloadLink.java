package pl.touk.tola.gwt.client.widgets.file;

import com.extjs.gxt.ui.client.widget.Html;
import com.google.gwt.core.client.GWT;
import pl.touk.tola.gwt.client.model.file.FileDescriptorGxt;

public class FileUploadFormWithDownloadLink extends FileUploadForm {

    static private final String CONTROLLER_DOWNLOAD = "download=true";

    protected final Html link;

    public FileUploadFormWithDownloadLink(String initialFileUrl, String linkTitle) {
        super();
        this.link = new Html(makeAHref(initialFileUrl, linkTitle));
        this.mainPanel.add(link);
    }

    public FileUploadFormWithDownloadLink(String controllerUrl, String headerText, String initialFileUrl, String linkTitle) {
        super(controllerUrl, headerText);
        this.link = new Html(makeAHref(initialFileUrl, linkTitle));
        this.mainPanel.add(link);
    }

    public void setLink(String newFileUrl, String newTitle) {
        link.setHtml(makeAHref(newFileUrl, newTitle));
    }

    @Override
    protected void onSubmit(int status, String message, FileDescriptorGxt fdg) {
        String downloadUrl = createDownloadUrl(fdg);
        setLink(downloadUrl, fdg.getFileName());
        super.onSubmit(status, message, fdg);
    }

    private String makeAHref(String url, String title) {
        String html = "";
        if ((url != null) && (title != null)) {
            //TODO: safety checks?
            html = String.format("<a href=\"%s\">%s</a>", url, title);
        }
        return html;
    }

    private String createDownloadUrl(FileDescriptorGxt fileDescriptor) {
        String baseModuleUrl = GWT.getModuleBaseURL();
        return String.format("%s%s?%s&fileId=%s", baseModuleUrl, controllerUrl, CONTROLLER_DOWNLOAD, fileDescriptor.getFileId());
    }
}
