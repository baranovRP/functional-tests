package org.jtalks.tests.jcommune.testlink.post;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import java.util.List;

import static org.jtalks.tests.jcommune.assertion.Existance.*;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author masyan
 */
public class JC33SecurityRegisteredUserToPost {

    String urlTopic;

    @BeforeMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
    public void setupCase(String appUrl, String username, String password, String username2, String password2) {
        driver.get(appUrl);
        signIn(username2, password2);
        clickOnRandomBranch();
        //create topic and answer by User2
        createTopicForTest();
        createAnswerForTest(StringHelp.getRandomString(10));
        //save url to this topic
        urlTopic = driver.getCurrentUrl();
        logOut(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        //create topic by User1
        createTopicForTest();
        createAnswerForTest(StringHelp.getRandomString(10));
    }

    @AfterMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test(alwaysRun = true, enabled = false)
    public void securityRegisteredUserToPostTest() {
        //view the posts list
        List<WebElement> posts = postPage.getPostsList();
        assertionNotEmptyCollection(posts);

        //create post
        assertElementExistsBySelector(driver, postPage.newButtonSel);


        //delete topic (user is owner this topic)
        assertElementExistsBySelector(driver, postPage.deleteTopicButtonSel);

        //delete post (user is owner this post)
        assertElementExistsBySelector(driver, postPage.deleteButtonNearLastPostSel);

        //edit topic (user is owner this topic)
        assertElementExistsBySelector(driver, postPage.editTopicButtonSel);

        //edit post (user is owner this post)
        assertElementExistsBySelector(driver, postPage.editPostButtonSel);

        driver.get(urlTopic);

        //delete topic (user is't owner this topic)
        assertionNotExistBySelector(driver, postPage.deleteTopicButtonSel);

        //delete post (user is't owner this post)
        assertionNotExistBySelector(driver, postPage.deleteButtonNearLastPostSel);

        //edit topic (user is't owner this topic)
        assertionNotExistBySelector(driver, postPage.editTopicButtonSel);

        //edit post (user is't owner this post)
        assertionNotExistBySelector(driver, postPage.editPostButtonSel);
    }
}