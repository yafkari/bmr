package bmr_afkari;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DoubleTextField extends TextField {

    public DoubleTextField() {
        super();

        addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!isValid(getText())) {
                    event.consume();
                }
            }
        });

        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                    String oldValue, String newValue) {
                if (!isValid(newValue)) {
                    setText(oldValue);
                }
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
