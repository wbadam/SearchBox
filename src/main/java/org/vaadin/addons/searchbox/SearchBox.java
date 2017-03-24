package org.vaadin.addons.searchbox;

import java.util.Optional;

import org.vaadin.addons.AutocompleteExtension;
import org.vaadin.addons.SuggestionGenerator;

import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class SearchBox<T> extends CustomComponent {

    private final CssLayout searchBoxLayout = new CssLayout();

    private final TextField textField = new TextField();

    private final Button searchButton = new Button("Search");
    private ButtonPosition buttonPosition = ButtonPosition.RIGHT;

    private AutocompleteExtension<T> autocomplete;

    private Registration suggestionSelectHandle;
    private Registration buttonClickHandle;

    private int debounceTime = 300;

    public SearchBox() {
        doLayout();
        searchBoxLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        buttonClickHandle = searchButton.addClickListener(clickEvent -> {
            fireSearchEvent(textField.getValue());
        });

        setCompositionRoot(searchBoxLayout);
    }

    public TextField getSearchField() {
        return textField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButtonPosition(ButtonPosition position) {
        if (position == null) {
            throw new IllegalArgumentException(
                    "Button position cannot be null");
        }

        boolean needsRepaint = buttonPosition != position;

        buttonPosition = position;

        if (needsRepaint) {
            doLayout();
        }
    }

    private void doLayout() {
        searchBoxLayout.removeAllComponents();

        if (ButtonPosition.LEFT == buttonPosition) {
            searchBoxLayout.addComponent(searchButton);
        }

        searchBoxLayout.addComponent(textField);

        if (ButtonPosition.RIGHT == buttonPosition) {
            searchBoxLayout.addComponent(searchButton);
        }
    }

    public Registration addSearchListener(SearchListener listener) {
        return addListener(SearchEvent.class, listener,
                SearchListener.SEARCH_METHOD);
    }

    public void setSearchMode(SearchMode searchMode) {
        switch (searchMode) {
        case EAGER:
            setEager();
            break;
        case DEBOUNCE:
            setDebounce();
            break;
        case EXPLICIT:
            setExplicit();
            break;
        }
    }

    private void setEager() {
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(event -> {
            fireSearchEvent(event.getValue());
        });
    }

    private void setDebounce() {
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(event -> {
            fireSearchEvent(event.getValue());
        });
    }

    private void setExplicit() {
        textField.setValueChangeMode(ValueChangeMode.BLUR);
    }

    public void setDebounceTime(int millis) {
        this.debounceTime = millis;
    }

    public int getDebounceTime() {
        return this.debounceTime;
    }

    public void setSuggestionGenerator(SuggestionGenerator<T> generator) {
        if (generator != null) {
            ensureAutocomplete().setSuggestionGenerator(generator);
        } else {
            removeSuggestionGenerator();
        }
    }

    public void removeSuggestionGenerator() {
        Optional.ofNullable(suggestionSelectHandle)
                .ifPresent(Registration::remove);
        Optional.ofNullable(autocomplete)
                .ifPresent(AutocompleteExtension::remove);
    }

    private AutocompleteExtension<T> ensureAutocomplete() {
        if (autocomplete == null) {
            autocomplete = new AutocompleteExtension<>(textField);

            // Fire search event on selecting suggestion
            suggestionSelectHandle = autocomplete
                    .addSuggestionSelectListener(event -> {
                        fireSearchEvent(event.getSelectedValue());
                    });
        }
        return autocomplete;
    }

    private void fireSearchEvent(String searchTerm) {
        fireEvent(new SearchEvent(this, searchTerm));
    }

    public enum ButtonPosition {
        RIGHT, LEFT, HIDDEN
    }

    public enum SearchMode {
        EAGER, DEBOUNCE, EXPLICIT
    }
}