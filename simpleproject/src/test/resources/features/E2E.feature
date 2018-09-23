Feature: This is E2E feature

@SmokeTest
Scenario: This is E2E scenario
  Given Navigate to Application URL
  When Click Sign In
  And Enters "<Username>" in Username
  And Enters Password
  And Clicks SignIn
  Then Should land on HomePage

@RegressionTest
Scenario: This is E2E scenario
  Given Navigate to Application URL
  When Click Sign In
  And Enters "<Username>" in Username
  And Enters Password
  And Clicks SignIn
  Then Should land on HomePage

@E2ETest
Scenario Outline: This is E2E scenario
  Given Navigate to Application URL
  When Click Sign In
  And Enters "<Username>" in Username
  And Enters Password
  And Clicks SignIn
  Then Should land on HomePage
Examples:
  |Username|
  |Jason|