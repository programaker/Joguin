package com.gmail.programaker.joguin.alien;

import java.io.Serializable;

/** Changes the Earth's environment so that it becomes inhabitable by the aliens.
 * Unfortunately it turns inhospitable for the native life forms.
 * Each device has its own defense power to protect itself */
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
