package com.example.smart_shopping_list_app;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {User.class, Group.class, GroupItem.class, ListItem.class, Lists.class,
        ListUser.class, Product.class, Distance.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract GroupDao groupDao();
    public abstract GroupItemDao groupItemDao();
    public abstract ListItemDao listItemDao();
    public abstract ListsDao listsDao();
    public abstract ListUserDao listUserDao();
    public abstract ProductDao productDao();
    public abstract ListUserAndListDao listUserAndListDao();
    public abstract DistanceDao distanceDao();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database").addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddTestUser(INSTANCE).execute();
        }
    };

    private static class AddTestUser extends AsyncTask<Void, Void, Void> {
        private final  UserDao userDao;

        AddTestUser(AppDatabase appDatabase) {this.userDao = appDatabase.userDao(); }

        @Override
        protected Void doInBackground(Void... voids) {
            User user = new User("Fabian", "Morawiec", "email@gmail.com");
            userDao.insert(user);
            return null;
        }
    }
}
