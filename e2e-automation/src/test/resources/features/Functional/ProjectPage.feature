Feature: To verify Project Page


  Scenario: To verify after logging into Assessment Page
    Given Initialize the "chrome" browser
    Given User open the url "https://analytics.kornferrytalent-dev.com/"
    Then User verify the "Tab_Consumption" is VISIBLE on "PROJECT_PAGE"
    Then User clicks "Tab_Project" on "PROJECT_PAGE"

  Scenario: To Verify all elements are visible in project page
    Then User verify the "Tab_Project" is VISIBLE on "PROJECT_PAGE"
    Then User clicks "Tab_Project" on "PROJECT_PAGE"
    Then User verify the "KFSelect" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "ChkBox_KFSelect" is CLICKABLE on "PROJECT_PAGE"
    Then User verify the "KFAssess" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "ChkBox_KFAssess" is CLICKABLE on "PROJECT_PAGE"
    Then User verify the "Label_Administered" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_ActiveStatus" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_ProjectName" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_EngagementCode" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_NumberOfParticipants" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_Billable" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_NonBillable" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_PontentialLevel" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_CreatedDate" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_ProjectType" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_ProductType" is VISIBLE on "PROJECT_PAGE"
    Then User verify the "Label_AddOns" is VISIBLE on "PROJECT_PAGE"

  Scenario: To Get all data from project page in corresponding list
    Then User copies data of "NumberOfContainer" from "PROJECT_PAGE"

  Scenario: To Check functionalities of CheckBox in project tab
    Then User clicks "ChkBox_KFSelect" on "PROJECT_PAGE"
    Then User copies data after clicking checkbox "KFSelect" from "PROJECT_PAGE"
    Then User clicks "ChkBox_KFSelect" on "PROJECT_PAGE"
    Then User clicks "ChkBox_KFAssess" on "PROJECT_PAGE"
    Then User copies data after clicking checkbox "KFAssess" from "PROJECT_PAGE"
    Then User clicks "ChkBox_KFSelect" on "PROJECT_PAGE"
    Then User copies data after clicking checkbox "KFSelect" and "KFAssess" from "PROJECT_PAGE"
    Then User clicks "ChkBox_KFSelect" on "PROJECT_PAGE"
    Then User copies data after unselecting checkbox "KFSelect" from "PROJECT_PAGE"
    Then User clicks "ChkBox_KFSelect" on "PROJECT_PAGE"
    Then User clicks "ChkBox_KFAssess" on "PROJECT_PAGE"
    Then User copies data after unselecting checkbox "KFAssess" from "PROJECT_PAGE"
    Then User clicks "ChkBox_KFAssess" on "PROJECT_PAGE"