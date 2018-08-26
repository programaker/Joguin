package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.util.TestUtil;

public class TestGameConfig extends GameConfig {
    public TestGameConfig() {
        super(new TestRepositoryConfig(false), TestUtil.sleep);
    }
}
