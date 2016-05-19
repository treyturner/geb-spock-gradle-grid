package info.treyturner.qa.demo.page

import geb.Page

class GoogleSearchPage extends Page {

    static url = "http://google.com"

    static atCheckWaiting = 'short'

    static at = {
        title == "Google"
        searchBox
    }

    static content = {

        searchBox
                { $('input', id:'lst-ib') }
        searchButton
                { $('button', type:'submit') }
    }
}
