package pl.wiktorszczeszek.ui.buttons;

import javax.swing.JToggleButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ActionCancelButton extends JToggleButton {
    private String performText;
    private final String cancelText;
    private final List<ActionListener> performActionListeners;
    private final List<ActionListener> cancelActionListeners;

    public ActionCancelButton(String text) {
        super(text);
        performText = text;
        cancelText = "Anuluj";
        performActionListeners = new ArrayList<>();
        cancelActionListeners = new ArrayList<>();
        addActionListener(_ -> toggleState());
    }

    private void toggleState() {
        if (isSelected()) {
            super.setText(cancelText);
            notifyPerformActionListeners();
        } else {
            super.setText(performText);
            notifyCancelActionListeners();
        }
    }

    private void notifyPerformActionListeners() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Action");
        for (ActionListener listener : performActionListeners) {
            listener.actionPerformed(event);
        }
    }

    private void notifyCancelActionListeners() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Cancel");
        for (ActionListener listener : cancelActionListeners) {
            listener.actionPerformed(event);
        }
    }

    public void addPerformActionListener(ActionListener listener) {
        performActionListeners.add(listener);
    }

    public void addCancelActionListener(ActionListener listener) {
        cancelActionListeners.add(listener);
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        if (b) super.setText(cancelText);
        super.setText(performText);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        performText = text;
    }
}
