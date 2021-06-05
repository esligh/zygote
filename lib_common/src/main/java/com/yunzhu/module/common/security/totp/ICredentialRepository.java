//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yunzhu.module.common.security.totp;

import java.util.List;

public interface ICredentialRepository {
    String getSecretKey(String var1);

    void saveUserCredentials(String var1, String var2, int var3, List<Integer> var4);
}
