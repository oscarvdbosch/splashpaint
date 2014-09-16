import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * VerfSpetters - Java Magazine Code Challenge by Sander Mak
 *
 * <p/>
 * Java Magazine 2014 - nr 4. September/October
 * <p/>
 * Class SplashPaint calculates the smallest paint spot on a canvas, looking from left to right, top to bottom.
 * In case of many similar sized spots it will returns the first spot encountered.
 * <p/>
 * This class also represents the result of a single iteration, returning a splash as a Vector of Integers
 * (coordinates on the Grid)
 *
 * @author Oscar van den Bosch
 */
public class SplashPaint extends Stack<Integer> {

    // Static Shared Data
    static int q = 0,D = 12;                        // Temp index q, Expected Dimension of the grid
    static char grid[] = new char[D * D];           // Grid of Dim * Dim characters

    public static void main(String[] arg) throws Throwable {

        Files.lines(Paths.get(arg[0])).             // Java 8 Style Read the File given as arg [0]
                forEach(p ->                        // For each line
                {
                    for (String s : p.split(","))   // Explode on ','
                        grid[q++] = s.charAt(0);    // per string element only the first character X/O is put into the grid
                }
        );

        SplashPaint result = null;                   // The calculated result (starts empty)
        q=0;
        for (int ignored : grid) {

            SplashPaint splash = calculate(q++);     // Calculate the SplashPaint size on Coordinate

            if ((result == null || splash.size() < result.size()) && splash.size() > 0) {
                result = splash;                     // If it is the smallest result keep it
            }

        }

        String output = result.stream().map(            // Java 8 Style Lambda + Stream functionality to
                x -> "(" + x % D + "," + x / D + ")").  // print in the proper result format
                collect(Collectors.joining(","));       // Without trailing comma

        System.out.println(result.size() + "=>" + output); // Done
    }

    /**
     * Calculate the new splash
     *
     * @param pos coordinate on the grid
     * @return the new splash
     */
    static SplashPaint calculate(int pos) {

        SplashPaint splash = new SplashPaint();       // Start with an empty splash
        if (grid[pos] == 'X') {                       // If there is a spat, add it
            splash.add(pos);
            grid[pos] = 0;                            // Wipe it off the grid

            int x = pos % D, y = pos / D;             // For each x,y coordinate, calculate the neighbours

            // Check coordinates X-1, X, X+1, but clip the edges
            for (int x_delta = (x > 0) ? -1 : 0; x_delta < (x < (D - 1) ? 2 : 1); x_delta++) {

                // Combine with coordinates Y-1, Y, Y+1, also clip the edges
                for (int y_delta = (y > 0) ? -1 : 0; y_delta < (y < (D - 1) ? 2 : 1); y_delta++) {
                    // Recursively look for neighbouring coordinates with spats
                    splash.addAll(calculate(pos + x_delta + D * y_delta));
                }
            }
        }
        return splash;
    }
}