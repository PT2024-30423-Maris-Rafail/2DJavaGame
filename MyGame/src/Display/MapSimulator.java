package Display;

import java.util.Random;

public class MapSimulator {
    public static void simulateMap1() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (i == 0 || i == 49 || j == 0 || j == 49) {
                    System.out.print("0 ");
                } else {
                    if (j == 24) System.out.print("2 ");
                    else {

                        int x = random.nextInt(20) + 1;
                        if (x <= 3) System.out.print((3 + x % 3) + " ");
                        else System.out.print("1 ");
                    }
                }


            }
            System.out.println();
        }
    }

    public static void simulateMap2() {
        for (int i = 0; i < 51; i++) {
            for (int j = 0; j < 51; j++) {
                if (i == 0 || i == 49 || j == 0 || j == 49) {
                    System.out.print("0 ");
                } else {
                    System.out.print("1 ");
                }

            }
            System.out.println();
        }

    }


}
