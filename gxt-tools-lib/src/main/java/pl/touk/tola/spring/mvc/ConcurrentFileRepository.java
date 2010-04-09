package pl.touk.tola.spring.mvc;

import java.util.Map;
import java.util.HashMap;
import java.util.MissingResourceException;

/**
 * Thread-safe implementation of <code>FileRepository</code> interface.
 */
public class ConcurrentFileRepository implements FileRepository {

    private long currentId = 1;
    private final Map<Long, byte[]> repository = new HashMap<Long, byte[]>();

    // Guards currentId and repository:
    private final Object lock = new Object();

    public Long saveFile(ModelToByteArrayConverter converter) {
        if (converter == null) {
            throw new IllegalArgumentException("converter should not be null");
        }
        long i;
        byte[] converted = converter.convert();
        synchronized (lock) {
            repository.put(currentId, converted);
            i = currentId;
            currentId++;
        }
        return i;
    }

    public byte[] getFile(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id should not be null");
        }
        synchronized (lock) {
            byte[] array = repository.get(id);
            if (array != null) {
                return array;
            } else {
                if (repository.containsKey(id)) {
                    return null;
                } else {
                    throw new MissingResourceException("File repository does not contain object identified by " + id + ".", "byte[]", id.toString());
                }
            }
        }
    }
}
