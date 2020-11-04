package net.leaotech.promotions

import spock.lang.Specification

import static net.leaotech.Utils.*

class CardHoldersDiscountTest extends Specification {

    def underTest = new CardHoldersDiscount()

    def "Check if applicable and correct final amount"() {
        given: "a basket with items where this promotion applies"
            def basket = [item1, item3, item1]
            underTest.setItems(basket)
        when:
            underTest.process()

        then: 'is applicable as expected'
            underTest.isApplicable()

        and: 'correct new total'
            underTest.retrieveNewTotal() == 36.95
    }

    def "Check not applicable"() {
        given: "a basket with items where this promotion DOES NOT apply"
            def basket = [item2, item3, item1]
            underTest.setItems(basket)
        when:
            underTest.process()

        then: 'not applicable as expected'
            !underTest.isApplicable()
    }

    def "Has the correct priority"() {
        expect:
            underTest.getPriorityOrder() == 1
    }
}
