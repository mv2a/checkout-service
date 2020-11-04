package net.leaotech.checkout


import net.leaotech.promotions.CardHoldersDiscount
import net.leaotech.promotions.OverSixtyDiscount
import spock.lang.Specification
import spock.lang.Unroll

import static net.leaotech.Utils.item1
import static net.leaotech.Utils.item2
import static net.leaotech.Utils.item3

class CheckoutTest extends Specification {

    Checkout co
    def overSixtyDiscount = new OverSixtyDiscount()
    def cardHoldersDiscount = new CardHoldersDiscount()

    def setup() {
        co = new CheckoutImpl([overSixtyDiscount, cardHoldersDiscount])
        //resetting the prices
        item1.setPrice(9.25)
        item2.setPrice(45.00)
        item3.setPrice(19.95)
    }

    //IF unfamiliar with this test structure and spock data table see:
    //http://spockframework.org/spock/docs/1.0/data_driven_testing.html#data-tables
    @Unroll
    def "Verify acceptance scenario for: #_description_"() {

        given: "a row in the table, corresponding to a combination of items in a basket"
            def items = _basket_

        when: "Each item gets scanned/added"
            items.forEach { it -> co.scan(it) }

        then: "The total as expected"
            co.total() == _expectedTotal_

        where: "As defined on the table below, as per the requirements"
            _description_                                   | _basket_                     | _expectedTotal_
            'Over £60, Discount of 10%'                     | [item1, item2, item3]        | 66.78
            '2+ card holders then the price drops to £8.50' | [item1, item3, item1]        | 36.95
            'All rules should apply'                        | [item1, item2, item1, item3] | 73.76
    }

    @Unroll
    def "Order of items shouldn't affect the total for scenario: #_description_"() {

        given: "a given scenario, (a row in the table, corresponding to a combination of items)"
            def items = _basket_

        when: "Each item gets scanned/added"
            items.forEach { it -> co.scan(it) }

        then: "The total is as expected"
            co.total() == _expectedTotal_

        where: "As defined on the table below, as per the requirements"
            _description_                                   | _basket_                     | _expectedTotal_
            'Over £60, Discount of 10%'                     | [item2, item3, item1]        | 66.78
            '2+ card holders then the price drops to £8.50' | [item3, item1, item1]        | 36.95
            'All rules should apply'                        | [item2, item1, item3, item1] | 73.76
    }


    //as the the above but with the items on a different order, _expectedTotal_ for each remains the same
    @Unroll
    def "Promotion order is enforced for '#_description_'"() {

        when:
            _item_.forEach { it -> co.scan(it) }

        //both 'then' clauses below test the order on which isApplicable is invoked

        then: "cardHoldersDiscount is executed first as it has the highest priority"
            cardHoldersDiscount.isApplicable(_item_) >> true

        then: "and consecutively overSixtyDiscount"
            overSixtyDiscount.isApplicable(_item_) >> true

        where: "a list of items, each row representing a basket of items"
            _item_                       | _description_
            [item2, item1, item3, item1] | 'both promotions example1'
            [item1, item2, item1, item3] | 'both promotions example2'
            [item1, item2, item3, item1] | 'both promotions example3'
            [item3, item1, item1, item2] | 'both promotions example4'
            [item1, item1, item2]        | 'both promotions example5'
            [item3, item1, item1]        | 'single promotion example 1'
            [item2, item3, item1]        | 'single promotion example 2'
    }

    def "empty basket should not make the scan fail"() {

        given: 'an empty basket/no items'
            def basket = []

        when: "the list is scanned"
            co.scan(basket)

        //maybe a suitable exception in the future
        then:
            noExceptionThrown()
    }
}
