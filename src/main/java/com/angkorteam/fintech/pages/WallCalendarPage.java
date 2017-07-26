package com.angkorteam.fintech.pages;

import com.angkorteam.fintech.Page;
import com.angkorteam.fintech.dto.Function;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

/**
 * Created by socheatkhauv on 6/18/17.
 */
@AuthorizeInstantiation(Function.ALL_FUNCTION)
public class WallCalendarPage extends Page {
}
