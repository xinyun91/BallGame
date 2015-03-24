package engine.util;

import java.util.Comparator;
import java.util.List;

import engine.Entity;

public class EntitySort {
    public enum SortOptions {
        X_AXIS, Y_AXIS;
    }

    public static void sortEntities(List<Entity> list, SortOptions so) {
        if (so == SortOptions.Y_AXIS) {
            list.sort(new Comparator<Entity>() {

                @Override
                public int compare(Entity o1, Entity o2) {
                    if (o1.getYCoordinate() < o2.getYCoordinate()) {
                        return -1;
                    } else if (o1.getYCoordinate() == o2.getYCoordinate()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }else{
            list.sort(new Comparator<Entity>() {

                @Override
                public int compare(Entity o1, Entity o2) {
                    if (o1.getXCoordinate() < o2.getXCoordinate()) {
                        return -1;
                    } else if (o1.getXCoordinate() == o2.getXCoordinate()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }
}
