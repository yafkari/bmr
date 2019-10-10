package bmr_afkari;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class IntTextField extends TextField {

    public IntTextField() {
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

        if (!value.matches("\\d{0,3}")) {
            return false;
        }

        try {
            double dvalue = Double.parseDouble(value);
            if (dvalue < 1 || dvalue > 121) {
                return false;
            } else {
                return true;
            }
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
