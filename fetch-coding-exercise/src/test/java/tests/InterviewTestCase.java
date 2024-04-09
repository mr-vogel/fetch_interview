package tests;

import org.testng.annotations.Test;
import pageObjects.LandingPage;


public class InterviewTestCase extends BaseTest {

    // Here is how I chose to solve this problem
    //
    //1. Divide the 9 gold bars into three groups of three bars each.
    //   Group 1 = Options 0, 1, and 2
    //   Group 2 = Options 3, 4, and 5
    //   Group 3 = Options 6, 7, and 8
    //
    //2. Compare the weight of Group 1 VS Group 2
    //   If they weigh the same, we can assume Group 3 contains the fake gold bar
    //   If not, we need to compare Group 1 VS Group 3
    //   If Group 1 = Group 3, we can assume group 2 contains the fake bar
    //   If not, we have to assume the fake bar is in Group 1 as this point
    //
    //3. Take the group containing the fake bar and repeat the process,
    //   dividing it into three smaller groups of one bar each.
    //
    //Using this process we can find the odd bar out every time within 4 weighs

    @Test
    public void test(){

        LandingPage landingPage = new LandingPage(driver);

        //Find the group the fake gold bar is in
        int result = landingPage.findFakeGoldBarGroup();

        //Find the fake gold bar
        result = landingPage.findFakeGoldBar(result);

        //Click the fake gold bar
        landingPage.clickFakeGold(result);

        //Print results
        landingPage.printResults();
    }
}
