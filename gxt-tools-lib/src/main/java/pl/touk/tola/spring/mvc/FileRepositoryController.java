package pl.touk.tola.spring.mvc;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * An <code>AbstractController</code> implementation that serves files obtained from a file repository.
 * The file repository should be set through <code>setFileRepository</code> method before first request is handled in <code>handleRequestInternal</code> method.
 */
public class FileRepositoryController extends AbstractController {

    private static String errorPrefix = "Request to file repository controller is invalid. ";
    private FileRepository fileRepository;

    private static final String CONTENT_TYPE_PARAMETER = "contentType";
    private static final String ID_PARAMETER = "id";

    /**
     * Handles http request. Http request, <code>httpServletRequest</code>, should contain parameter named "id". Its value is
     * used to retrieve byte array from file repository set in method <code>setFileRepository</code>.
     * If <code>httpServletRequest</code> also contains parameter named "contentType" then its value is used
     * as value of "Content-Type" http header of the generated response.
     * This method returns null.
     * @param httpServletRequest request to handle
     * @param httpServletResponse generated response
     * @return null
     * @throws Exception
     */
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String s = httpServletRequest.getParameter(ID_PARAMETER);
        if (s == null) {
            throw new IllegalArgumentException(errorPrefix + "It should contain a parameter \"" + ID_PARAMETER + "\".");
        }
        Long id;
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorPrefix + "The value of parameter \"" + ID_PARAMETER + "\" is illegal: \"" + s + "\". Long value was expected.", e);
        }
        byte[] file = fileRepository.getFile(id);

        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setHeader("Expires", "0");
        httpServletResponse.setHeader("Cache-Control", "no-cache");

        String contentType = httpServletRequest.getParameter(CONTENT_TYPE_PARAMETER);
        if (contentType != null) {
            httpServletResponse.setHeader("Content-Type", contentType);
        }

        OutputStream os = httpServletResponse.getOutputStream();
        os.write(file);
        os.close();

        return null;
    }

    /**
     * Sets file repository used in <code>handleRequestInternal</code> method to retrieve files from.
     * @param fileRepository
     */
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
}
