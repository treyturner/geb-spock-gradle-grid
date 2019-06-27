package search_engine_exercises.module.google

import geb.Module

class GoogleSearchResult extends Module {

    static content = {
        container
                { $('div', class:'rc') }
        name
                { container.find('h3').first() }
        link
                { container.find('a').first() }
        description
                { container.find('span', class:'st').first() }
    }
}
