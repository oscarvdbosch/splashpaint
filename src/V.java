import java.util.ArrayList;
import java.util.stream.Collectors;

public class V {

    static int D = 12;
    static char g[][];

    public static void main(String[] a) {
        g = new char[D][D];String s[] = a[0].split(",");
        int i=0;
        do {
            g[i%D][i/D] = s[i].charAt(0);
        } while (++i<D*D);
        System.out.println(s().t());
    }

    public static S s() {
        S r = null;
        int i=0;
        do {
            S s = v(i%D,i/D);
            r=((r==null||s.size()<r.size())&&s.size()>0)?s:r;
        } while (++i<D*D);
        return r;
    }

    private static S v(int x, int y) {
        S s = new S();
        if (g[x][y] == 'X') {
            s.add(new int[]{x, y});
            g[x][y] = 0;
            for (int dx = x > 0 ? -1 : 0; dx <= (x < (D - 1) ? 1 : 0); dx++) {
                for (int dy = y > 0 ? -1 : 0; dy <= (y < (D - 1) ? 1 : 0); dy++) {
                    s.addAll(v(x + dx, y + dy));
                }
            }
        }
        return s;
    }

    public static class S extends ArrayList<int[]> {
        public String t() {
            return size() + "=>" + stream().map(i -> "(" + i[0] + "," + i[1] + ")").collect(Collectors.joining(","));
        }

    }

}
