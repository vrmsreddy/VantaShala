package com.vs.api.suite;

import com.vs.api.menu.MenuControllerTest;
import com.vs.api.order.OrderControllerTest;
import com.vs.api.user.AdminUserControllerTest;
import com.vs.api.user.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by GeetaKrishna on 9/15/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UserControllerTest.class, AdminUserControllerTest.class,
        MenuControllerTest.class,
        AdminUserControllerTest.class,
        OrderControllerTest.class})
public class VantaShalaTestSuite {
}
