package org.vaadin.addons.searchbox;

import java.util.Optional;

import org.vaadin.addons.AutocompleteExtension;
import org.vaadin.addons.SuggestionGenerator;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class SearchBox<T> extends CustomComponent {

    private final CssLayout searchBoxLayout = new CssLayout();
    private final TextField textField = new TextField();
    private Button searchButton = new Button("Search");

    private AutocompleteExtension<T> autocomplete;

    public SearchBox() {
//        textField.setIcon(VaadinIcons.SEARCH);
//        textField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        searchBoxLayout.addComponents(textField, searchButton);
        searchBoxLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setCompositionRoot(searchBoxLayout);
    }

    public TextField getSearchField() {
        return textField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

//    public void setSearchButtonPosition(ButtonPosition position) {
//        searchBoxLayout.removeAllComponents();
//
//        switch (position) {
//        case LEFT:
//            searchBoxLayout.addComponentAsFirst(searchButton);
//            break;
//        case RIGHT:
//            searchBoxLayout.addComponent(searchButton);
//            break;
//        case HIDDEN:
//            searchBoxLayout.removeComponent(searchButton);
//            break;
//        }
//    }

    public void setSuggestionGenerator(SuggestionGenerator<T> generator) {
        if (generator != null) {
            ensureAutocomplete().setSuggestionGenerator(generator);
        } else {
            removeSuggestionGenerator();
        }
    }

    public void removeSuggestionGenerator() {
        Optional.ofNullable(autocomplete)
                .ifPresent(AutocompleteExtension::remove);
    }

    private AutocompleteExtension<T> ensureAutocomplete() {
        if (autocomplete == null) {
            autocomplete = new AutocompleteExtension<>(textField);
        }
        return autocomplete;
    }

    public enum ButtonPosition {
        RIGHT, LEFT, HIDDEN
    }
}
