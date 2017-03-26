package org.vaadin.addons.searchbox.event;

import java.lang.reflect.Method;

import com.vaadin.event.ConnectorEventListener;

@FunctionalInterface
public interface SearchListener extends ConnectorEventListener {

    Method SEARCH_METHOD = SearchListener.class.getDeclaredMethods()[0];

    void search(SearchEvent event);
}
