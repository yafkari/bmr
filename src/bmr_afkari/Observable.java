package bmr_afkari;

/**
 *
 * @author g52196
 */
public interface Observable {
    void registerObserver(Observer obs);

    void removeObserver(Observer obs);

    void notifyObservers();
}
