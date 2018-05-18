# SearchBox

SearchBox is a component for Vaadin 8 that helps performing searches and has autocomplete functionality.

## Usage

Instantiate `SearchBox` with the button caption or icon and position attributes and attach a listener to be notified on search events.

```Java
SearchBox searchBox = new SearchBox("Search", SearchBox.ButtonPosition.RIGHT);
searchBox.addSearchListener(e -> Notification.show(e.getSearchTerm()));
```

Optionally, set a suggestion generator to show search suggestions for the user.

```Java
searchBox.setSuggestionGenerator(this::suggestPlanets);

// ...

private List<String> suggestPlanets(String query, int limit) {
    return DataSource.getPlanets().stream().filter(p -> p.contains(query))
                    .limit(cap).collect(Collectors.toList());
}
```

### Building Parts

The component is made of a search field (TextField) and a search button (Button). The search button can be on either side of the search field, or it can be hidden. If a suggestion generator is set, AutocompleteExtension is applied to the search field.

### Search Button

The search button can have a caption, an icon or both, just like the Button component in Vaadin.

Its position, relative to the search field, can be defined in the search box's constructor or later with the `setSearchButtonPosition()` method. The button can be on either side of the field (`LEFT`, `RIGHT`) or invisible (`HIDDEN`).

To join the button visually into the text field, call `setButtonJoined(true)`.

The button component can be accessed and further customized using the `getSearchButton()` method. 

### Search Field

Set a placeholder text to the search field using the `setPlaceholder()` method.

If you need to customize the search field, use `getSearchField()` to access the TextField component.

### Search Modes

There are three search modes to choose from that determines the frequency of search events.

- `EXPLICIT` (default): event is fired when search button is clicked or when ENTER key is pressed.
- `DEBOUNCE`: event is fired when typing stops for a set time.
- `EAGER`: event is fired at each key press while typing.

```Java
searchBox.setSearchMode(SearchMode.DEBOUNCE);
searchBox.setDebounceTime(200); // event fires 200 ms after typing
```

### Search Suggestions

To activate search suggestions, set a suggestion generator to the search box component.

```Java
searchBox.setSuggestionGenerator(this::suggestUsers);

// ...

// The suggestion generator method that returns a list of suggestions
private List<String> suggestUsers(String query, int limit) {
    return DataSource.getUsers().stream().map(User::getName)
                    .filter(p -> p.contains(query))
                    .limit(cap).collect(Collectors.toList());
}
```

It is also possible to customize how the suggestions are displayed. You can do this by adding value and caption converters along with the generator. Note that the generator now returns a list of User objects instead of a list of names.

```Java
searchBox.setSuggestionGenerator(this::suggestUsers, this::convertValueUser, this::convertCaptionUser);

// ...

// The suggestion generator method. Returns a list of users.
private List<User> suggestUsers(String query, int limit) {
    return DataSource.getUsers().stream().filter(p -> p.contains(query))
                    .limit(cap).collect(Collectors.toList());
}

// Converts a User object into a String to be set as the value of the search field.
private String convertValueUser(DataSource.User user) {
    return user.getName();
}

// Converts a User object into an HTML string to be displayed as suggestion item
private String convertCaptionUser(DataSource.User user, String query) {
    return "<div class='suggestion-container'>"
            + "<img src='" + user.getPicture() + "' class='userimage'>"
            + "<span class='username'>"
            + user.getName().replaceAll("(?i)(" + query + ")", "<b>$1</b>")
            + "</span>"
            + "</div>";
}
```

Please refer to AutocompleteExtension's documentation for more details.

## Examples

### Appearance

#### Simple search box

```Java
SearchBox searchBox = new SearchBox("Search", SearchBox.ButtonPosition.RIGHT);
```

#### Simple search box, button with icon on the left and 

```Java
SearchBox searchBox = new SearchBox(VaadinIcons.SEARCH, SearchBox.ButtonPosition.LEFT);
```

#### Hidden search button and placeholder text

```Java
SearchBox searchBox = new SearchBox((String) null, SearchBox.ButtonPosition.HIDDEN);
searchBox.setPlaceholder("Search...");
```

#### Joined button

```Java
SearchBox searchBox = new SearchBox(VaadinIcons.SEARCH, SearchBox.ButtonPosition.RIGHT);
searchBox.setButtonJoined(true);
searchBox.setPlaceholder("Search...");
```
