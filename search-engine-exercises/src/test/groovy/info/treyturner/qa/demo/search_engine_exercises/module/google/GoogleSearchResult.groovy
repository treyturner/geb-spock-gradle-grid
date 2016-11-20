package info.treyturner.qa.demo.search_engine_exercises.module.google

import geb.Module

class GoogleSearchResult extends Module {

    static content = {
        container
                { $('div', class:'rc') }
        name
                { container.find('h3', class:'r') }
        link
                { name.find('a') }
        description
                { container.find('span', class:'st') }
    }
}
