package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;


public class LandingPage extends BasePage{

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    private void clickWeigh(){
        driver.findElement(By.cssSelector("#weigh")).click();
    }

    private void clickReset(){
        driver.findElement(By.cssSelector("button.button:nth-child(1)")).click();
    }

    public String getResults(){
        String result = getText(By.cssSelector(".result > button:nth-child(2)"));

        while(result.equals("?")){
            result = getText(By.cssSelector(".result > button:nth-child(2)"));
        }

        return result;
    }

//    Fills left 'bowl' with options from group 1 (0,1,2)
    public void fillGroupOne(){
        driver.findElement(By.id("left_0")).sendKeys("0");
        driver.findElement(By.id("left_1")).sendKeys("1");
        driver.findElement(By.id("left_2")).sendKeys("2");
    }
//    Fills right 'bowl' with options from group 2 (3,4,5)
    public void fillGroupTwo(){
        driver.findElement(By.id("right_0")).sendKeys("3");
        driver.findElement(By.id("right_1")).sendKeys("4");
        driver.findElement(By.id("right_2")).sendKeys("5");
    }
//    Fills right 'bowl' with options from group 3 (6,7,8)
    public void fillGroupThree(){
        driver.findElement(By.id("right_0")).sendKeys("6");
        driver.findElement(By.id("right_1")).sendKeys("7");
        driver.findElement(By.id("right_2")).sendKeys("8");
    }

//    Function that finds the fake gold bar and returns the fake gold bar as an int
//    Inputs, the group we believe the fake gold bar to be in
//    The logic for this is to find the bar in the group that weighs differently that the other two bars
    public int findFakeGoldBar(int group){
        clickReset();

//      Depending on the group we are testing, start comparing the first two gold bars in the group
        if(group == 1){
            enterText(By.id("left_0"),"0");
            enterText(By.id("right_0"),"1");
        } else if (group == 2) {
            enterText(By.id("left_0"),"3");
            enterText(By.id("right_0"),"4");
        } else {
            enterText(By.id("left_0"),"6");
            enterText(By.id("right_0"),"7");
        }

        clickWeigh();
        String resultText = getResults();

//      If the first two options in the group are equal, the third option must weigh differently
        if (resultText.contains("=") && group == 1) {
            return 2;
        } else if (resultText.contains("=") && group == 2) {
            return 5;
        } else if (resultText.contains("=") && group == 3) {
            return 8;
        }

        clickReset();

//      Since we haven't found equality yet, we now compare the first and third bar
//      in the group under test
        if(group == 1){
            enterText(By.id("left_0"),"0");
            enterText(By.id("right_0"),"2");
        } else if (group == 2) {
            enterText(By.id("left_0"),"3");
            enterText(By.id("right_0"),"5");
        } else {
            enterText(By.id("left_0"),"6");
            enterText(By.id("right_0"),"8");
        }

        clickWeigh();
        resultText = getResults();

//      If we find equality here, we can assume the second bar option of the group is the fake
        if (resultText.contains("=") && group == 1) {
            return 1;
        } else if (resultText.contains("=") && group == 2) {
            return 4;
        } else if (resultText.contains("=") && group == 3) {
            return 7;
        }

//      At this point we can only assume the first bar in the group under test weighs differently
        if (group == 1) {
            return 0;
        } else if (group == 2) {
            return 3;
        } else {
            return 6;
        }
    }

//     Function to find the group that fake gold bar is in.
//     The three possible groups are 1, 2, and 3
//     Group 1 = Gold Bars 0, 1, 2
//     Group 2 = Gold Bars 3, 4, 5
//     Group 3 = Gold Bars 6, 7, 8
//     The logic here is to find the group that weighs differently than the other two groups
    public int findFakeGoldBarGroup(){
        fillGroupOne(); //fills left bowl of scale
        fillGroupTwo(); //fills right bowl of scale

        clickWeigh();
        String resultText = getResults();

//      If group 1 and 2 are equal we can assume the fake bar is in group 3
        if (resultText.contains("=")) {
            return 3;
        }

        clickReset();

        fillGroupOne();
        fillGroupThree();

        clickWeigh();
        resultText = getResults();

//      If group 1 and 3 are equal, we can assume the fake bar is in group 2
        if (resultText.contains("=")) {
            return 2;
        }

//      By this point we know that group 1 and 3 are not equal, and group 1 and 2 are not equal
//      We must conclude that group 1 contains the fake gold bar at this point
        return 1;
    }

//     Function to click the option we think is the fake gold.
//     Input = int, representing what gold bar we think is fake
//     Options as input are 0-9
    public void clickFakeGold(int fakeGold){
        driver.findElement(By.id(STR."coin_\{fakeGold}")).click();
    }

    public void printResults(){
        //Get alertText
        String alertMessage = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();

        //Get list of weighs
        //.game-info.li
        List<WebElement> weighs = driver.findElements(By.tagName("li"));
        System.out.println("Total Weighs = " + weighs.size());

        System.out.println("List of Weighs:");
        for(int j = 0; j < weighs.size(); j++){
            System.out.println((j+1) + ". " + weighs.get(j).getText());
        }

        System.out.println("Alert Message = " + alertMessage);
    }
}