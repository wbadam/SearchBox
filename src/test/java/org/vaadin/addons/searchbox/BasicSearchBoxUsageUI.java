package org.vaadin.addons.searchbox;

import org.vaadin.addonhelpers.AbstractTest;

import com.vaadin.ui.Component;

public class BasicSearchBoxUsageUI extends AbstractTest {

    @Override
    public Component getTestComponent() {
        SearchBox<String> searchBox = new SearchBox<>();
        return searchBox;
    }

}
