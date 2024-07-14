package search_engine_exercises.page.google

import geb.Page
import search_engine_exercises.module.google.GoogleSearchResult

class GoogleResultsPage extends Page {

    static atCheckWaiting = 'shorter'

    static at = {
        title.contains('Google Search')
        resultContainer
    }

    static content = {
        centerColumn
                { $(id: 'center_col') }
        resultContainer
                { centerColumn.find(id: 'rso') }
        results
                { resultContainer.find('.g[data-hveid]').collect{ it.module(GoogleSearchResult) } }
    }
}
