package com.angkorteam.fintech.pages;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.angkorteam.fintech.Application;

public class TestIndexPage {

    private WicketTester tester;

    @Before
    public void setUp() {
	tester = new WicketTester(new Application());
    }

    @Test
    public void homepageRendersSuccessfully() {
	// start and render the test page
	tester.startPage(IndexPage.class);

	// assert rendered page class
	tester.assertRenderedPage(IndexPage.class);
    }
}
