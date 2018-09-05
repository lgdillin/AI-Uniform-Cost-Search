import java.util.Comparator;
import java.util.TreeSet;


class CostComparator implements Comparator<MyState> {
  public int compare(MyState a, MyState b) {
    int ax = (int)(a.x * 0.1f);
    int ay = (int)(a.y * 0.1f);

    int bx = (int)(b.x * 0.1f);
    int by = (int)(b.y * 0.1f);

    // Pretend X is the most significant coordinate
    if(ax < bx) return -1;
    else if(ax > bx) return 1;
    else if(ay < by) return -1;
    else if(ay > by) return 1;
    else return 0;
  }
}
