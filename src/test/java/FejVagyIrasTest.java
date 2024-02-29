import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FejVagyIrasTest {

    @Test
    void dobasTest() {
        char eredmeny = FejVagyIras.dobas();
        assertTrue(eredmeny == 'F' || eredmeny == 'I');
    }

    @Test
    void kiserletHosszaTest() throws IOException {
        createDummyKiserletFile();
        int hossz = FejVagyIras.kiserletHossza();
        assertEquals(5, hossz);
    }

    @Test
    void fejRelativGyakorisagTest() throws IOException {
        createDummyKiserletFile();
        double gyakorisag = FejVagyIras.fejRelativGyakorisag(5);
        assertEquals(40, gyakorisag); // Az első 2 sor F, az összes 5 dobás közül.
    }

    @Test
    void ketFejEgymasUtanTest() throws IOException {
        createDummyKiserletFile();
        int db = FejVagyIras.ketFejEgymasUtan();
        assertEquals(1, db);
    }

    @Test
    void leghosszabbTisztafejTest() throws IOException {
        createDummyKiserletFile();
        int[] eredmeny = FejVagyIras.leghosszabbTisztafej();
        assertEquals(3, eredmeny[0]); // A harmadik és negyedik sor F, az összes 5 dobás közül.
        assertEquals(3, eredmeny[1]); // A harmadik dobásnál kezdődik.
    }

    @Test
    void dobassorozatokKiirasaTest() throws IOException {
        FejVagyIras.dobassorozatokKiirasa(1);
        List<String> sorozatok = Files.readAllLines(new File("dobasok.txt").toPath());
        assertEquals(1, sorozatok.size());
        String sorozat = sorozatok.get(0);
        assertEquals(18, sorozat.length()); // 4 dobás + 3 szóköz + 6 számjegy + 2 szóköz + 6 számjegy + 1 szóköz
        assertTrue(sorozat.contains("FFFF") || sorozat.contains("IIII")); // A 4 dobás között mind F vagy mind I van.
    }

    // Segédfüggvény a dummy kísérlet.txt fájl létrehozásához
    private void createDummyKiserletFile() throws IOException {
        File file = new File("kiserlet.txt");
        String content = "F\nF\nI\nI\nF\n";
        Files.write(file.toPath(), content.getBytes());
    }
}
