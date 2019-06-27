package search_engine_exercises.page.google

import geb.Page

class GoogleSearchPage extends Page {

    static url = "https://google.com"

    static atCheckWaiting = 'short'

    static at = {
        title == "Google"
        searchBox
    }

    static content = {
        searchBox
                { $('input', name:'q') }
        searchButton
                { $('input', name:'btnK').first() }
    }
}
