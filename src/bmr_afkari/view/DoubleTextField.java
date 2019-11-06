package bmr_afkari.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Represents a text field that only accepts doubles.
 *
 * @author 52196
 */
public class DoubleTextField extends TextField {

    public DoubleTextField() {
        super();

        addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            if (!isValid(getText())) {
                event.consume();
            }
        });

        textProperty().addListener(
                (ObservableValue<? extends String> observableValue,
                        String oldValue, String newValue) -> {
                    if (!isValid(newValue)) {
                        setText(oldValue);
                    }
                });
    }

    private boolean isValid(final String value) {
        if (value.length() == 0) {
            return true;
        }

        if (!value.matches("\\d{0,3}(\\.\\d{0,2})?")) {
            return false;
        }

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public double getDouble() {
        try {
            return Double.parseDouble(getText());
        } catch (NumberFormatException e) {
            System.out.println(e);
            return -1;
        }
    }
}
