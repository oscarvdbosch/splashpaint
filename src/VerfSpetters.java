import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * VerfSpetters - Java Magazine Code Challange by Sander Mak
 * <p/>
 * Java Magazine 2014 - nr 4. September/Oktober
 *
 * @author Oscar van den Bosch
 */
public class VerfSpetters {

    public static void main(String[] canvasData) {
        Canvas canvas = new Canvas(canvasData[0], 12, 12);
        System.out.println(canvas.getSmallestVerfSpetter());
    }

    /**
     * Een coordinaat on the canvas
     */
    public static class Coordinaat {
        public final int x, y;

        public Coordinaat(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Een verfspetter is een lijst van aansluitende coordinaten op het G
     */
    public static class VerfSpetter extends ArrayList<Coordinaat> {
        @Override
        public String toString() {
            return size() + "=>" + stream().map(coordinate -> "(" + coordinate.x + "," + coordinate.y + ")").collect(Collectors.joining(","));
        }

    }

    /**
     * G is een WxH grid, waarbij ieder coordinaat een X of O bevat
     */
    public static class Canvas {
        static final char SPETTER_CHAR = 'X';
        private final int width;
        private final int height;
        char spetters[][];

        Canvas(String data, int width, int height) {
            this.width = width;
            this.height = height;
            spetters = new char[width][height];
            String args[] = data.split(",");
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    spetters[x][y] = args[x + y * width].charAt(0);
                }
            }
        }

        /**
         * Bereken de kleinste verfspetter op dit canvas *
         */
        public VerfSpetter getSmallestVerfSpetter() {
            VerfSpetter smallest = null;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    VerfSpetter spetter = getVerfSpetter(x, y);
                    if ((smallest == null || spetter.size() < smallest.size()) && spetter.size() > 0) {
                        smallest = spetter;
                    }
                }
            }
            return smallest;
        }

        /**
         * Bereken de grootte van de verfspetter op locatie X, Y
         *
         * @param x locatie op het canvas
         * @param y locatie op het canvas
         * @return Verfspetter
         */
        private VerfSpetter getVerfSpetter(int x, int y) {
            VerfSpetter spetter = new VerfSpetter();
            if (spetters[x][y] != SPETTER_CHAR) {
                return spetter; // Geen S
            }
            spetter.add(new Coordinaat(x, y));

            // Wis de 'getelde' spetter
            spetters[x][y] = 0;

            // Voeg recursief de 8 naburige velden toe
            for (int dx=x>0?-1:0; dx<=(x<(width-1)?1:0); dx++) {
                for (int dy=y>0?-1:0; dy<=(y<(height-1)?1:0); dy++) {
                    spetter.addAll(getVerfSpetter(x + dx, y + dy));
                }
            }
            return spetter;
        }

    }
}
