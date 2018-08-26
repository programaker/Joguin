package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.util.TestUtil;

public class TestGameComponents extends GameComponents {
    public TestGameComponents() {
        super(new TestRepositoryConfig(false), TestUtil.sleep);
    }
}
