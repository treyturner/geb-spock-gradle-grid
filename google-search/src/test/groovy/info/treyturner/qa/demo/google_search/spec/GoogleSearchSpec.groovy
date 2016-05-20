package info.treyturner.qa.demo.google_search.spec

import info.treyturner.qa.demo.google_search.WebApplicationSpecification
import info.treyturner.qa.demo.google_search.page.GoogleResultsPage
import info.treyturner.qa.demo.google_search.page.GoogleSearchPage

class GoogleSearchSpec extends WebApplicationSpecification {

    def "should load the Google search page"() {

        given: "the user navigates to the Google search page"
        to GoogleSearchPage

        expect: "the Google search page to be shown"
        at GoogleSearchPage
    }

    def "should return search results when performing a search"() {

        given: "the user is at the Google search page"
        at GoogleSearchPage

        when: "the user types 'Civitas' into the search field"
        searchBox = 'Civitas'

        and: "the user clicks the search button"
        searchButton.click()

        then: "the user is brought to the search results page"
        at GoogleResultsPage

        and: "some number of results are returned"
        results.size() > 1
    }

    def "should return civitaslearning.com as the first result for a search for 'civitas'"() {

        given: "the user is at the former search result page"
        at GoogleResultsPage

        expect: "the first result to be for civitaslearning.com"
        results[0].name.text() == "Civitas Learning: Home"
        results[0].link.@href == "https://www.civitaslearning.com/"
        results[0].description.text() == "By building a community of forward-thinking higher education institutions, Civitas Learning brings together the best of education technology, design thinking, ..."
    }
}
