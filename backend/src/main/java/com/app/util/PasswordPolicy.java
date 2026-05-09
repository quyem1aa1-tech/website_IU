package com.app.util;

import org.passay.*;

public class PasswordPolicy {
    public static PasswordValidator getValidator() {
        return new PasswordValidator(
                // Rule 1: Length between 8 and 30 characters
                new LengthRule(8, 30),

                // Rule 2: At least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // Rule 3: At least one digit
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // Rule 4: No whitespace allowed
                new WhitespaceRule()
        );
    }
}
