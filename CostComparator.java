import java.util.Comparator;
import java.util.TreeSet;


class CostComparator implements Comparator<MyState> {
  public int compare(MyState a, MyState b) {
    if(a.cost < b.cost) return -1;
    else if(a.cost > b.cost) return 1;
    else return 0;
  }
}
