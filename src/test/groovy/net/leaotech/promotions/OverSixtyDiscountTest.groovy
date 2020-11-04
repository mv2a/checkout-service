package net.leaotech.promotions

import spock.lang.Specification

import static net.leaotech.Utils.item1
import static net.leaotech.Utils.item2
import static net.leaotech.Utils.item3


class OverSixtyDiscountTest extends Specification {

    def underTest = new OverSixtyDiscount()

    def "Check if applicable and correct final amount"() {
        given: 'a basket with items where this promotion applies'
            def basket = [item1, item2, item3]
            underTest.setItems(basket)
        when:
            underTest.process()

        then: 'is applicable as expected'
            underTest.isApplicable()

        and: 'correct new total'
            underTest.retrieveNewTotal() == 66.78
    }

    def "Check not applicable"() {
        given: 'a basket with items where this promotion DOES NOT apply'
            def basket = [item3, item3, item1]
            underTest.setItems(basket)
        when:
            underTest.process()

        then: 'not applicable as expected'
            !underTest.isApplicable()
    }

    def "Has the correct priority"() {
        expect:
            underTest.getPriorityOrder() == 2
    }
}
