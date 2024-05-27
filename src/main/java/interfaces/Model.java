package interfaces;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

public interface Model<T> {
    String[] fillable();
    String getTableName();
    List<T> get();

    default T convertToObject(Object[] data, T instance) {
        String[] fillable = fillable();
        for (int i = 0; i < fillable.length; i++) {
            try {
                Field field = instance.getClass().getDeclaredField(fillable[i]);
                field.setAccessible(true);

                Object value = data[i];
                if (field.getType().equals(BigDecimal.class) && value != null) {
                    value = new BigDecimal(value.toString());
                }

                field.set(instance, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
