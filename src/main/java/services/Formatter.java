package services;

import java.lang.reflect.Field;
import java.util.Collection;

public class Formatter {
    /**
     * Convert a list of models to a 2D array of objects
     */
    public static <T> Object[][] modelListToGenericObject(Iterable<T> models) {
        int size = ((Collection<?>) models).size();

        Object[][] data = new Object[size][];
        int i = 0;
        for (T model : models) {
            data[i] = modelToGenericObject(model);
            i++;
        }

        return data;
    }

    public static <T> Object[] modelToGenericObject(T model) {
        Field[] fields = model.getClass().getDeclaredFields();
        Object[] data = new Object[fields.length];
        int i = 0;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                data[i] = field.get(model);
                i++;
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return data;
    }


}
