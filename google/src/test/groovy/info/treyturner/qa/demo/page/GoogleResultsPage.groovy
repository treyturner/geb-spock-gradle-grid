package info.treyturner.qa.demo.page

import geb.Page
import info.treyturner.qa.demo.module.GoogleSearchResult

class GoogleResultsPage extends Page {

    static atCheckWaiting = 'shorter'

    static at = {
        title.contains('Google Search')
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
