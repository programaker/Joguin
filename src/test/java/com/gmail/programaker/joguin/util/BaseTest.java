package com.gmail.programaker.joguin.util;

import com.gmail.programaker.joguin.config.MessageConfig;
import com.gmail.programaker.joguin.config.TestConfig;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, MessageConfig.class})
public abstract class BaseTest {
    @BeforeClass
    public static void setLocale() {
        Locale.setDefault(Locale.US);
    }
}
