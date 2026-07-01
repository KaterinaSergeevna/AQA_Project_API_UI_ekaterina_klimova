Feature: Advanced product search on MX.by

  @Search
  Scenario: Verification of clearing advanced search filters
    Given User opens main page of mx by
    When User goes to advanced search page
    And User enters keyword "Пылесос" to advance search input
    And User sets price range from "200" to "800"
    And User clears all search filters
    Then All fields of advanced search form should be empty

  @Search
  Scenario: User can open expand search
    Given User opens main page of mx by
    When User goes to advanced search page
    Then Advanced search page title should display "расширенный поиск"
