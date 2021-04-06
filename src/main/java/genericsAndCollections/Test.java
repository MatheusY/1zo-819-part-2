package genericsAndCollections;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Long> list1 = new ArrayList<Long>(); // Line 1
        list1.add(5L);   // Line 2
        List<? extends Number> list2 = null;  // Line 3
 
        if (list1 instanceof List<?>) { // Line 4
            list2 = list1;      // Line 5
        }
 
        System.out.println(list2);
    }
}
