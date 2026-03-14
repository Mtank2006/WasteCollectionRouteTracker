package wastecollection.utils;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Helper {
    public double calculateDistance(double x1, double y1, double x2, double y2) {
        return  (sqrt((pow(x2-x1,2))+(pow(y2-y1,2))));
    }
}
