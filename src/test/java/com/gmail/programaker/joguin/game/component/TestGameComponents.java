package com.gmail.programaker.joguin.game.component;

import com.gmail.programaker.joguin.util.TestRepositoryFactory;
import com.gmail.programaker.joguin.util.TestUtil;

public class TestGameComponents extends GameComponents {
    public TestGameComponents() {
        super(new TestRepositoryFactory(false), TestUtil.sleep);
    }
}
