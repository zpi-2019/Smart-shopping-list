package com.example.smart_shopping_list_app.LocalDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import java.util.List;

//TODO: Add converter from date to long and from long to date, Create Indexes


@Entity
class User {
    @PrimaryKey
    int IDUser;

    String FirstName;
    String LastName;
    String Email;
}

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAllUseres();
}



@Entity
class Lists {
    @PrimaryKey
    int IDList;

    String Name;
    Long CreateDate;
    Long LastUpadteDate;
}

@Dao
interface ListsDao {
    @Query("SELECT * FROM Lists")
    List<Lists> getAllLists();
}



@Entity
class Status {
    @PrimaryKey
    int IDStatus;

    String Name;
}

@Dao
interface StatusDao {
    @Query("SELECT * FROM Status")
    List<Status> getAllStatuses();
}



@Entity
class Category {
    @PrimaryKey
    int IDCategory;

    String Name;
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();
}



@Entity
class Group {
    @PrimaryKey
    int IDGroup;

    String Name;
}

@Dao
interface GroupDao {
    @Query("SELECT * FROM 'Group'")
    List<Group> getAllGroups();
}



@Entity
class Unit {
    @PrimaryKey
    int IDUnit;

    String Name;
}

@Dao
interface UnitDao {
    @Query("SELECT * FROM Unit")
    List<Unit> getAllUnits();
}



@Entity(primaryKeys = {"IDUser", "IDList"},
        foreignKeys = {
            @ForeignKey(entity = User.class,
                    parentColumns = "IDUser",
                    childColumns = "IDUser"
            ),
            @ForeignKey(entity = Lists.class,
                    parentColumns = "IDList",
                    childColumns = "IDList")
        },
        indices = {@Index("IDUser"), @Index("IDList")})
class ListUser {
    @ColumnInfo(name = "IDUser")
    int IDUser;

    @ColumnInfo(name = "IDList")
    int IDList;
}

@Dao
interface ListUserDao {
    @Query("SELECT * FROM ListUser")
    List<ListUser> getAllListUsers();
}



@Entity(foreignKeys = {
            @ForeignKey(entity = Unit.class,
                parentColumns = "IDUnit",
                childColumns = "IDUnit"
            ),
            @ForeignKey(entity = Category.class,
                parentColumns = "IDCategory",
                childColumns = "IDCategory"
            )
        },
        indices = {@Index("IDCategory"), @Index("IDUnit")})
class Product {
    @PrimaryKey
    int IDProduct;

    @ColumnInfo(name = "IDUnit")
    int IDUnit;

    @ColumnInfo(name = "IDCategory")
    int IDCategory;

    String Name;
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();
}



@Entity(primaryKeys = {"IDGroup", "IDProduct"},
        foreignKeys = {
            @ForeignKey(entity = Group.class,
                parentColumns = "IDGroup",
                childColumns = "IDGroup"
            ),
            @ForeignKey(entity = Product.class,
                parentColumns = "IDProduct",
                childColumns = "IDProduct"
            )
        },
        indices = {@Index("IDProduct"), @Index("IDGroup")})
class GroupItem {
    @ColumnInfo( name = "IDGroup")
    int IDGroup;

    @ColumnInfo(name = "IDProduct")
    int IDProduct;
}

@Dao
interface GroupItemDao {
    @Query("SELECT * FROM GroupItem")
    List<GroupItem> getAllGroupItems();
}



@Entity(primaryKeys = {"IDList", "IDProduct"},
        foreignKeys = {
            @ForeignKey(entity = Lists.class,
                    parentColumns = "IDList",
                    childColumns = "IDList"
            ),
            @ForeignKey(entity = Product.class,
                    parentColumns = "IDProduct",
                    childColumns = "IDProduct"
            ),
            @ForeignKey(entity = Status.class,
                    parentColumns = "IDStatus",
                    childColumns = "IDStatus"
            )
        },
        indices = {@Index("IDList"), @Index("IDProduct"), @Index("IDStatus")})
class ListItem {
    @ColumnInfo(name = "IDList")
    int IDList;

    @ColumnInfo(name = "IDProduct")
    int IDProduct;

    @ColumnInfo(name = "IDStatus")
    int IDStatus;

    double Amount;
}

@Dao
interface ListItemDao {
    @Query("SELECT * FROM ListItem")
    List<ListItem> getAllListItems();
}