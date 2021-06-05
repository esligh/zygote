package com.yunzhu.module.bus.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yunzhu.module.bus.db.dao.UserDao
import com.yunzhu.module.bus.model.db.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null
        private var DB_NAME:String = "app.db"
        fun instance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                    //.addMigrations(MIGRATION_1_2)
                    .build()
            }
            return instance as AppDatabase
        }

//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                Log.d("db","MIGRATION_4_5")
//                database.execSQL("ALTER TABLE EncryptFile ADD COLUMN fileSize INTEGER")
//                database.execSQL("ALTER TABLE EncryptFile ADD COLUMN hash TEXT")
//                database.execSQL("ALTER TABLE EncryptFile ADD COLUMN createTime INTEGER")
//                database.execSQL("ALTER TABLE EncryptFile ADD COLUMN importTime INTEGER")
//            }
//        }
    }

}