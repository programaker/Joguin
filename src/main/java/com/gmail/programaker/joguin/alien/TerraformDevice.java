package com.gmail.programaker.joguin.alien;

import java.io.Serializable;

public class TerraformDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int defensePower;

    public TerraformDevice(int defensePower) {
        this.defensePower = defensePower;
    }

    public int getDefensePower() {
        return defensePower;
    }
}
