package com.example.smart_shopping_list_app.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {User.class, Category.class, Group.class, GroupItem.class, ListItem.class,
                        Lists.class, ListUser.class, Product.class, Status.class, Unit.class,
                        Distance.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();
    public abstract GroupDao groupDao();
    public abstract GroupItemDao groupItemDao();
    public abstract ListItemDao listItemDao();
    public abstract ListsDao listsDao();
    public abstract ListUserDao listUserDao();
    public abstract ProductDao productDao();
    public abstract StatusDao statusDao();
    public abstract UnitDao unitDao();
    public abstract DistanceDao distanceDao();
}
