//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yunzhu.module.common.security.totp;

public interface IGoogleAuthenticator {
    GoogleAuthenticatorKey createCredentials();

    GoogleAuthenticatorKey createCredentials(String var1);

    int getTotpPassword(String var1);

    int getTotpPassword(String var1, long var2);

    int getTotpPasswordOfUser(String var1);

    int getTotpPasswordOfUser(String var1, long var2);

    boolean authorize(String var1, int var2) throws GoogleAuthenticatorException;

    boolean authorize(String var1, int var2, long var3) throws GoogleAuthenticatorException;

    boolean authorizeUser(String var1, int var2) throws GoogleAuthenticatorException;

    boolean authorizeUser(String var1, int var2, long var3) throws GoogleAuthenticatorException;

    ICredentialRepository getCredentialRepository();

    void setCredentialRepository(ICredentialRepository var1);
}
