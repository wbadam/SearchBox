package org.vaadin.addons.searchbox;

import java.util.Arrays;

import org.vaadin.addonhelpers.AbstractTest;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;

public class BasicSearchBoxUsageUI extends AbstractTest {

    @Override
    public Component getTestComponent() {
        SearchBox<String> searchBox = new SearchBox<>();

        searchBox.setSuggestionGenerator((s, integer) -> Arrays
                .asList(s + "1", s + "2", s + "3"));

        searchBox.setSearchButtonPosition(SearchBox.ButtonPosition.RIGHT);
        searchBox.setSearchButtonIcon(VaadinIcons.SEARCH, "Search");
        searchBox.setSearchButtonCaption(null);

        searchBox.setPlaceholder("Search");

        searchBox.addSearchListener(event -> {
            System.out.println(event.getSearchTerm());
        });

        return searchBox;
    }

}
