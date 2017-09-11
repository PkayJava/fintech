package com.angkorteam.fintech.pages;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.angkorteam.fintech.junit.Listener;

public class LoginPageITCase {

    @Test
    public void indexPage() {
	Listener.DRIVER.get("http://localhost:8080/fintech");
	WebElement element = Listener.DRIVER.findElement(By.className("login-logo"));
	Assert.assertNotNull(element);
    }

}
