package util;

import java.util.List;

/**
 * CsvManager interface:
 * Generic contract for loading and saving data to/from CSV files.
 *  Promotes code reuse and flexibility by using generics (T).
 *
 * @param <T> - The type of data handled by the implementing class.
 */
public interface CsvManager<T> {
    List<T> load();          //  Load data from CSV
    void save(List<T> data); //  Save data to CSV
}
