package org.vaadin.addons.searchbox;

import org.vaadin.addonhelpers.AbstractTest;

import com.vaadin.ui.Component;

public class BasicSearchBoxUsageUI extends AbstractTest {

    @Override
    public Component getTestComponent() {
        SearchBox<String> searchBox = new SearchBox<>();
//        searchBox.setSearchButtonPosition(SearchBox.ButtonPosition.LEFT);

//        searchBox.setSuggestionGenerator((s, integer) -> Arrays.asList("sug1", "sug3", "sug3"));

//        searchBox.addSearchListener(event -> {
//
//        });

        searchBox.setSearchButtonPosition(SearchBox.ButtonPosition.LEFT);

        searchBox.addSearchListener(event -> {
            System.out.println(event.getSearchTerm());
        });

        return searchBox;
    }

}
