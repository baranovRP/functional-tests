/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.User;
import org.jtalks.tests.jcommune.webdriver.Users;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.logOutIfLoggedIn;

/** @author Guram Savinov */
public class SignIn {

    @BeforeMethod
    @Parameters("app-url")
    public void setup(String appUrl) {
        driver.get(appUrl);
        logOutIfLoggedIn();
    }

    @Test
    public void correctUsernameAndPassword_JC_20() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void emptyUsernameCausesError_JC_21() throws Exception {
        String password = StringHelp.getRandomString(9);
        Users.signIn(new User("", password));
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void incorrectUsernameCausesError_JC_21() throws Exception {
        User user = Users.signUp();
        user.setUsername(StringHelp.getRandomString(8));
        Users.signIn(user);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void incorrectPassword_JC_22() throws Exception {
        User user = Users.signUp();
        user.setPassword(user.getPassword() + "a");
        Users.signIn(user);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void incorrectUsernameAndPassword() throws Exception {
        String username = StringHelp.getRandomString(8);
        String password = StringHelp.getRandomString(9);
        Users.signIn(new User(username, password));
    }

    @Test
    public void checkUsernameIsCaseInsensitive() throws Exception {
        User user = Users.signUp();
        user.setUsername(user.getUsername().toUpperCase());
        Users.signIn(user);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void checkPasswordIsnotCaseInsensitive() throws Exception {
        User user = Users.signUp();
        user.setPassword(user.getPassword().toUpperCase());
        Users.signIn(user);
    }
}
