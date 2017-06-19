# NYTimes

**NYTimes* is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

## User Stories

The following **required** functionality is completed:

The app has 2 screens defined below.
**Home Screen:**
 * [x] Allows the user to enter a search term and displays the results in either a list or grid view.
 * [x] Use the ActionBar SearchView or custom layout as the query box instead of an EditText.
 * [x] Results should display:
 * [x] Article thumbnail
 * [x] Article headline
 * [x] Selecting an item in search results should open the detail screen.
 * [x] Should have pagination of results

**Detail Screen:**
* [x] Displays a detailed view of article.
* [x] User can share a link to their friends or email it to themselves

* [x] Robust error handling, check if internet is available, handle error cases, network failures.

The following **additional** features are implemented:

* [x] As soon as user opens the app, a network call is made to show them current news.
* [x] User can click on "Filter" which allows selection of **advanced search options** to filter results based on news desk values
* [x] Subsequent searches have any filters applied to the search results
* [x] Filters are stored in shared preferences so that they will be retained even after application closes.
* [x] User can switch between list and grid view for news results
* [x] Heterogenous Layouts for different news articles that only have text or only have images
* [x] Leverages the data binding support module
* [x] Added placeholder images for news items. 
* [x] Added a Splash screen.
* [x] Fixed crashes and handled no response and error response from server.

## Notes

## Open-source libraries used

- [Retrofit2](http://square.github.io/retrofit/) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
- [CardView] - Implement card like layout for news article using Google Design library.

## License

    Copyright [2017] [Gauri Gadkari]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
