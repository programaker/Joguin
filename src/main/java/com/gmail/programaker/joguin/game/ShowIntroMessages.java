package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:ShowIntroMessages.properties")
public class ShowIntroMessages {
    private final String intro;
    private final String start;
    private final String errorInvalidOption;

    @Autowired
    public ShowIntroMessages(
        @Value("${intro}")
        String intro,

        @Value("${start}")
        String start,

        @Value("${error-invalid-option}")
        String errorInvalidOption
    ) {
        this.intro = intro;
        this.start = start;
        this.errorInvalidOption = errorInvalidOption;
    }

    public String intro() {
        return intro;
    }

    public String start() {
        return start;
    }

    public String invalidOption() {
        return errorInvalidOption;
    }
}
