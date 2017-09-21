package search_engine_exercises.page.google

import geb.Page
import search_engine_exercises.module.google.GoogleSearchResult

class GoogleResultsPage extends Page {

    static atCheckWaiting = 'shorter'

    static at = {
        title.contains('Google')
        results
    }

    static content = {
        centerColumn
                { $('div', id:'center_col') }
        resultContainer
                { centerColumn.find('div', id:'rso') }
        results
                { resultContainer.find('div', class:'g').collect{ it.module(GoogleSearchResult) } }
    }
}
