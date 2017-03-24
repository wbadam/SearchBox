package org.vaadin.addons.searchbox;

import org.vaadin.addons.AutocompleteExtension;
import org.vaadin.addons.SuggestionGenerator;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class SearchBox<T> extends CustomComponent {

    private final TextField textField = new TextField();
    private Button searchButton = new Button("Search");

    private AutocompleteExtension<T> autocomplete;

    public SearchBox() {
        CssLayout layout = new CssLayout();

//        textField.setIcon(VaadinIcons.SEARCH);
//        textField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        layout.addComponents(textField, searchButton);
        layout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        setCompositionRoot(layout);
    }

    public void setSuggestionGenerator(SuggestionGenerator<T> generator) {
        if (generator != null) {
            autocomplete = new AutocompleteExtension<>(textField);
            autocomplete.setSuggestionGenerator(generator);
        } else {
            textField.removeExtension(autocomplete);
        }
    }
}
