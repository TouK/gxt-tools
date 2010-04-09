package pl.touk.tola.spring.mvc;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.MissingResourceException;

public class FileRepositoryControllerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testHandleRequestInternal1() throws Exception {
        AbstractController controller = new FileRepositoryController();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");

        // Invalid request - without id parameter:
        controller.handleRequest(request, response);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleRequestInternal2() throws Exception {
        AbstractController controller = new FileRepositoryController();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");

        // Invalid request - with invalid id parameter:
        request.setParameter("id", "abc");
        controller.handleRequest(request, response);
    }

    @Test(expected = NullPointerException.class)
    public void testHandleRequestInternal3() throws Exception {
        AbstractController controller = new FileRepositoryController();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");

        // Valid request but repository not set:
        request.setParameter("id", "1");
        controller.handleRequest(request, response);
    }

    @Test(expected = MissingResourceException.class)
    public void testHandleRequestInternal4() throws Exception {
        FileRepositoryController controller = new FileRepositoryController();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");

        // Valid request but empty repository:
        FileRepository repository = new ConcurrentFileRepository();
        controller.setFileRepository(repository);
        request.setParameter("id", "1");
        controller.handleRequest(request, response);
    }

    @Test
    public void testHandleRequestInternal5() throws Exception {
        FileRepositoryController controller = new FileRepositoryController();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setMethod("GET");

        FileRepository repository = new ConcurrentFileRepository();
        Long id = repository.saveFile(new ModelToByteArrayConverter() {
            public byte[] convert() {
                byte[] a = new byte[1];
                a[0] = 'a';
                return a;
            }
        });
        controller.setFileRepository(repository);
        request.setParameter("id", id.toString());
        String contentType = "someContentType";
        request.setParameter("contentType", contentType);
        controller.handleRequest(request, response);
        assertTrue("a".equals(response.getContentAsString()));
        assertTrue(contentType.equals(response.getHeader("Content-Type")));
    }
}
