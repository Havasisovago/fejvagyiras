import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class FejVagyIras {
    public static void main(String[] args) {
        // 1. feladat: Pénzfeldobás szimulációja
        char dobásEredménye = dobas();
        System.out.println("1. feladat");
        System.out.println("Pénzfeldobás eredménye: " + dobásEredménye);

        // 2. feladat: Tipp bekérése és dobás szimulációja
        Scanner scanner = new Scanner(System.in);
        System.out.println("2. feladat");
        System.out.println("Tippeljen! (F/I)= ");
        char tipp = scanner.nextLine().charAt(0);
        char dobásEredménye2 = dobas();
        System.out.println("A tipp " + tipp + ", a dobás eredménye " + dobásEredménye2 + " volt.");
        if (tipp == dobásEredménye2) {
            System.out.println("Ön eltalálta!");
        } else {
            System.out.println("Ön nem találta el.");
        }

        // 3. feladat: Kisérlet hosszának meghatározása
        System.out.println("3. feladat");
        int kiserletHossza = kiserletHossza();
        System.out.println("A kísérlet " + kiserletHossza + " dobásból állt.");

        // 4. feladat: Fejek relatív gyakoriságának meghatározása
        System.out.println("4. feladat");
        double fejRelativGyakorisag = fejRelativGyakorisag(kiserletHossza);
        System.out.printf("A kísérlet során a fej relatív gyakorisága %.2f%% volt.\n", fejRelativGyakorisag);

        // 5. feladat: Pontosan két fejek egymás után előfordulásának megszámolása
        System.out.println("5. feladat");
        int ketFejEgymasUtan = ketFejEgymasUtan();
        System.out.println("A kísérlet során " + ketFejEgymasUtan + " alkalommal dobtak pontosan két fejet egymás után.");

        // 6. feladat: Leghosszabb tisztafej sorozat hossza és kezdőhelye
        System.out.println("6. feladat");
        int[] leghosszabbTisztafej = leghosszabbTisztafej();
        System.out.println("A leghosszabb tisztafej sorozat " + leghosszabbTisztafej[0] + " tagból áll, kezdete a(z) " +
                leghosszabbTisztafej[1] + ". dobás.");

        // 7. feladat: Dobássorozatok kiírása és eredmények számolása
        System.out.println("7. feladat");
        dobassorozatokKiirasa(1000);
    }

    // 1. feladat: Pénzfeldobás szimulációja
    public static char dobas() {
        Random random = new Random();
        return random.nextBoolean() ? 'F' : 'I';
    }

    // 3. feladat: Kisérlet hosszának meghatározása
    public static int kiserletHossza() {
        try {
            Scanner fileScanner = new Scanner(new File("kiserlet.txt"));
            int count = 0;
            while (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
                count++;
            }
            fileScanner.close();
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 4. feladat: Fejek relatív gyakoriságának meghatározása
    public static double fejRelativGyakorisag(int kiserletHossza) {
        try {
            Scanner fileScanner = new Scanner(new File("kiserlet.txt"));
            int fejCount = 0;
            while (fileScanner.hasNextLine()) {
                if (fileScanner.nextLine().charAt(0) == 'F') {
                    fejCount++;
                }
            }
            fileScanner.close();
            return (double) fejCount / kiserletHossza * 100;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 5. feladat: Pontosan két fejek egymás után előfordulásának megszámolása
    public static int ketFejEgymasUtan() {
        try {
            Scanner fileScanner = new Scanner(new File("kiserlet.txt"));
            String previous = fileScanner.nextLine();
            String current;
            String next;
            int count = 0;
            while (fileScanner.hasNextLine()) {
                current = fileScanner.nextLine();
                if (previous.equals("FF") && current.equals("FF")) {
                    count++;
                }
                previous = current;
            }
            fileScanner.close();
            return count;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 6. feladat: Leghosszabb tisztafej sorozat hossza és kezdőhelye
    public static int[] leghosszabbTisztafej() {
        try {
            Scanner fileScanner = new Scanner(new File("kiserlet.txt"));
            String current;
            int maxSequenceLength = 0;
            int currentSequenceLength = 0;
            int maxSequenceStartIndex = 0;
            int currentIndex = 1;

            while (fileScanner.hasNextLine()) {
                current = fileScanner.nextLine();
                if (current.equals("F")) {
                    currentSequenceLength++;
                    if (currentSequenceLength > maxSequenceLength) {
                        maxSequenceLength = currentSequenceLength;
                        maxSequenceStartIndex = currentIndex - maxSequenceLength + 1;
                    }
                } else {
                    currentSequenceLength = 0;
                }
                currentIndex++;
            }
            fileScanner.close();
            return new int[]{maxSequenceLength, maxSequenceStartIndex};
        } catch (IOException e) {
            e.printStackTrace();
            return new int[]{-1, -1};
        }
    }

    // 7. feladat: Dobássorozatok kiírása és eredmények számolása
    public static void dobassorozatokKiirasa(int darab) {
        try {
            FileWriter writer = new FileWriter("dobasok.txt");
            Random random = new Random();
            int fejSorozat = 0;
            int irasSorozat = 0;
            for (int i = 0; i < darab; i++) {
                StringBuilder sorozat = new StringBuilder();
                for (int j = 0; j < 4; j++) {
                    char dobás = random.nextBoolean() ? 'F' : 'I';
                    sorozat.append(dobás);
                    if (dobás == 'F') {
                        fejSorozat++;
                    } else {
                        irasSorozat++;
                    }
                }
                writer.write(sorozat.toString() + " ");
            }
            writer.write(": " + fejSorozat + ", " + irasSorozat);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
