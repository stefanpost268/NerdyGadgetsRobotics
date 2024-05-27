package interfaces;
import java.lang.reflect.Field;
import java.util.List;

@Deprecated
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
                field.set(instance, data[i]);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
