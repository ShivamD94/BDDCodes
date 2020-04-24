@Smoke
Feature: Add to cart on product page

  Scenario Outline: Verify Hilti Homepage for country <country>  
    Given User opens Hilti website for country <country>
    When User navigates to product page for "<productCode>"
    And User adds to cart products with given properties for <country> with following data
      | cartridgeColor | packSize | quantity | dataForCountry |
      | green          | 100 pc   |        1 | US             |
      | red            | 1000 pc  |        1 | US             |
      | yellow         | 100 pc   |        3 | US             |
      | green          | 1000 pc  |        2 | CA             |
      | red            | 100 pc   |        1 | CA             |
      | yellow         | 1000 pc  |        2 | CA             |
    And User navigates to cart page.
    Then User can see the products are added to cart page for <country>.
      | cartridgeColor | packSize | quantity | dataForCountry |
      | green          | 100 pc   |        1 | US             |
      | red            | 1000 pc  |        1 | US             |
      | yellow         | 100 pc   |        3 | US             |
      | green          | 1000 pc  |        2 | CA             |
      | red            | 100 pc   |        1 | CA             |
      | yellow         | 1000 pc  |        2 | CA             |
    Examples: 
      | country | productCode |
      | US      | r457        |
      | CA      | r457        |
