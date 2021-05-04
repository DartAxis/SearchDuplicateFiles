package ru.Katyanka8bit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.Katyanka8bit.FilePaths.getPaths;
import static ru.Katyanka8bit.HashSum.getHashSumFile;

public class SearchDuplicateFilesTest {

    @Test
    void searchDuplicateFilesTest(){
        Throwable exception = assertThrows(NullPointerException.class,
                () -> {
                    getHashSumFile(null);
                    getPaths(null);
                });
    }
}
