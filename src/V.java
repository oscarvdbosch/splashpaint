import java.util.ArrayList;
import java.util.stream.Collectors;

public class V extends ArrayList<int[]> {
    static int D = 12;
    static char g[][] = new char[D][D];

    public static void main(String[] a) {
        String c[] = a[0].split(",");
        int i = 0;
        do {
            g[i % D][i / D] = c[i].charAt(0);
        } while (++i < D * D);
        V r = null;
        i = 0;
        do {
            V s = v(i % D, i / D);
            r = ((r == null || s.size() < r.size()) && s.size() > 0) ? s : r;
        } while (++i < D * D);
        System.out.println(r == null ? "" : r.size() + "=>" + r.stream().map(p -> "(" + p[0] + "," + p[1] + ")").collect(Collectors.joining(",")));
    }

    static V v(int x, int y) {
        V v = new V();
        if (g[x][y] == 'X') {
            v.add(new int[]{x, y});
            g[x][y] = 0;
            for (int a = x > 0 ? -1 : 0; a <= (x < (D - 1) ? 1 : 0); a++) {
                for (int b = y > 0 ? -1 : 0; b <= (y < (D - 1) ? 1 : 0); b++) {
                    v.addAll(v(x + a, y + b));
                }
            }
        }
        return v;
    }
}