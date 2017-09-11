package com.angkorteam.fintech.junit;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Listener extends RunListener {

    public static WebDriver DRIVER;

    public void testRunStarted(Description description) throws Exception {
	System.setProperty("webdriver.gecko.driver", "C:\\Users\\socheat\\Documents\\geckodriver.exe");
	DRIVER = new FirefoxDriver();
    }

    public void testRunFinished(Result result) throws Exception {
	// TODO : to be check later, now get error
	// DRIVER.quit();
    }

}
