package pl.touk.tola.spring.mvc;

import org.junit.Test;
import static org.junit.Assert.*;

public class ConcurrentFileRepositoryTest {

    private static class Convertable implements ModelToByteArrayConverter {
        byte[] array;
        public Convertable(byte b) {
            array = new byte[1];
            array[0] = b;
        }
        public Convertable(byte[] array) {
            this.array = array;
        }
        public byte[] convert() {
            return array;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveFile1() {
        FileRepository fr = new ConcurrentFileRepository();
        fr.saveFile(null);
    }

    @Test
    public void testSaveFile3() {
        FileRepository fr = new ConcurrentFileRepository();

        Long id1 = fr.saveFile(new Convertable(null));
        assertTrue(id1 > 0);
        assertTrue(fr.getFile(id1) == null);


        Long id2 = fr.saveFile(new Convertable((byte) 10));
        assertTrue(id2 > 0);
        assertTrue(fr.getFile(id1) == null);
        assertTrue(fr.getFile(id2).length == 1 && fr.getFile(id2)[0] == 10);

        Long id3 = fr.saveFile(new Convertable((byte) 5));
        assertTrue(id3 > 0);
        assertTrue(fr.getFile(id1) == null);
        assertTrue(fr.getFile(id2).length == 1 && fr.getFile(id2)[0] == 10);
        assertTrue(fr.getFile(id3).length == 1 && fr.getFile(id3)[0] == 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFile1() {
        FileRepository fr = new ConcurrentFileRepository();
        fr.getFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void testGetFile2() {
        FileRepository fr = new ConcurrentFileRepository();
        fr.getFile(1L);
    }
}
