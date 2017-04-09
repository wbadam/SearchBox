package org.vaadin.addons.searchbox.event;

import org.vaadin.addons.searchbox.SearchBox;

import com.vaadin.ui.Component;

/**
 * Search event that is fired when a search is executed.
 * <p>
 * Depending on the search mode, the search is executed either automatically on
 * the user typing in the search field or explicitly by a search action (e.g. by
 * pressing the search button or the ENTER key).
 * <p>
 * Listen for search events by attaching a {@link SearchListener} to the search
 * box.
 *
 * @see org.vaadin.addons.searchbox.SearchBox.SearchMode
 * @see SearchBox#addSearchListener(SearchListener)
 */
public class SearchEvent extends Component.Event {

    private final String query;

    /**
     * Creates a search event.
     *
     * @param source
     *         search box component that initiated the event
     * @param query
     *         the search term
     */
    public SearchEvent(SearchBox source, String query) {
        super(source);

        this.query = query;
    }

    /**
     * Gets the search term.
     *
     * @return search term queried by the user
     */
    public String getSearchTerm() {
        return this.query;
    }
}
