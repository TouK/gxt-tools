package pl.touk.tola.spring.mvc;

/**
 * Interface to be implemented by objects that can be stored in a {@link pl.touk.tola.spring.mvc.FileRepository FileRepository}.
 */
public interface ModelToByteArrayConverter {

    /**
     * Converts this object to a byte array.
     * @return this object converted to byte array
     */
    byte[] convert();

}
