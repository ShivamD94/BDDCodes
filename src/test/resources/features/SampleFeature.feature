@Smoke @Sample
Feature: Sample feature to demonstrate the capabilities of this project

  Scenario Outline: Verify Hilti Homepage for country <country>
    Given User opens Hilti website for country <country>
    When User expands products navigation
    Then User verifies category <category_name> is present on the products navigation

    Examples:
      | country | category_name      |
      | US      | Tool Inserts       |
      | DE      | Geräte             |
      | RU      | Электроинструменты |
      | JP      | 建設用電動工具            |
      | CN      | 电动工具               |