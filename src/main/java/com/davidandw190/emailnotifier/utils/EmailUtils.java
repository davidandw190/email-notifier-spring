package com.davidandw190.emailnotifier.utils;

/**
 * Utility class for generating email messages and verification URLs.
 */
public class EmailUtils {

    /**
     * Generates an email message for account verification.
     *
     * @param name  The recipient's name.
     * @param host  The host URL of the application.
     * @param token The verification token for the account.
     * @return A formatted email message with the verification link.
     */
    public static String getEmailMessage(String name, String host, String token) {
        var messageBuilder = new StringBuilder();

        messageBuilder
                .append("Hello ").append(name).append(",\n\n")
                .append("Your new account has been created. Please click the link below to verify your account:\n\n")
                .append(getVerificationURL(host, token)).append("\n\n")
                .append("The Support Team");

        return messageBuilder.toString();
    }

    /**
     * Generates the verification URL for the provided token.
     *
     * @param host  The host URL of the application.
     * @param token The verification token for the account.
     * @return The complete verification URL.
     */
    private static String getVerificationURL(String host, String token) {
        var verificationLinkBuilder = new StringBuilder();

        verificationLinkBuilder
                .append(host)
                .append("/api/users/token=")
                .append(token);

        return verificationLinkBuilder.toString();
    }
}
