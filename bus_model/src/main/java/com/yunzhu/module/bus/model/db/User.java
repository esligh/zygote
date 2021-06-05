package com.yunzhu.module.bus.model.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author: lisc
 * @date: 2020-10-26 下午 07:35
 * @desc:
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String name;

    public String account;//email or phone

    public String userNo;

    public String pwd;

    public Long timeStamp;
}
