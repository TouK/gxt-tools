package pl.touk.tola.spring.mvc;

/**
 * Repository for storing and retrieving objects as byte arrays.
 */
public interface FileRepository {

    /**
     * Saves for future retrieval the given <code>object</code>.
     * <code>object</code> is saved as a byte array. The byte array is obtained by calling <code>object.convert</code> method.
     * If <code>object.convert</code> method returns null then null is saved. For the saved byte array a uniqe identifier
     * is generated and returned.
     *
     * @param object object that should be saved
     * @return identifier that can be used in <code>getFile</code> method to retrieve the byte array saved
     */
    public Long saveFile(ModelToByteArrayConverter object);

    /**
     * Returns byte array identified by the given <code>id</code>.
     * <code>id</code> should be earlier returned by <code>saveFile</code> method.
     * This method can return <code>null</code> if null was saved under <code>id</code> in <code>saveFile</code> method.
     * If nothing is saved under <code>id</code> then a <code>RuntimeException</code> is thrown.
     *
     * @param id identifier of byte array that should be returned
     * @return byte array identified by the given <code>id<code>
     * @throws RuntimeException if nothing is saved under <code>id</code>
     */
    public byte[] getFile(Long id);

}
