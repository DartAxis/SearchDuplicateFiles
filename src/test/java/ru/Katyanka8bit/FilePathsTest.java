package ru.Katyanka8bit;
import static org.junit.jupiter.api.Assertions.assertEquals;
class FilePathsTest {

    @org.junit.jupiter.api.Test
    void testGetListPaths() {
       assertEquals(null, FilePaths.getListPaths(null));
    }

    @org.junit.jupiter.api.Test
    void testGetListPaths2() {
       assertEquals(null, FilePaths.getListPaths("C:\\User\\Home\\Pictures"));
    }

    @org.junit.jupiter.api.Test
    void testGetListPaths3() {
        assertEquals((FilePaths.getListPaths("D:\\8pf\\Cottage").size() > 0), FilePaths.getListPaths("D:\\8pf\\Cottage").size() > 0);
    }

}



