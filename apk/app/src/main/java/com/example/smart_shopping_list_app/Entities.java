package com.example.smart_shopping_list_app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.Transaction;
//import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

//import java.sql.Date;
import java.util.List;

/*class Converters {
    @TypeConverter
    static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
}*/

@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    public int IDUser;

    public String FirstName;
    public String LastName;
    public String Email;

    public User(String FirstName, String LastName, String Email){
         this.FirstName = FirstName;
         this.LastName = LastName;
         this.Email = Email;
    }
}

@Dao
interface UserDao {
    @Insert
    void insert(User user);

    @Query("DELETE FROM User WHERE IDUser = :idUser")
    void delete(int idUser);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();
}


@Entity
class Lists {
    @PrimaryKey(autoGenerate = true)
    public int IDList;

    public String Name;
    public Long CreateDate;
    public Long LastUpdateDate;


    public Lists(String Name, Long CreateDate, Long LastUpdateDate){
        this.Name = Name;
        this.CreateDate = CreateDate;
        this.LastUpdateDate = LastUpdateDate;
    }
}

@Dao
interface ListsDao {
    @Insert
    void insert(Lists list);

    @Query("DELETE FROM Lists WHERE IDList = :idList")
    void delete(int idList);

    @Query("SELECT Max(IDList) FROM Lists WHERE Name = :name")
    int getListByName(String name);

    @Update
    void update(Lists list);
}


@Entity
class Group {
    @PrimaryKey(autoGenerate = true)
    public int IDGroup;

    public String Name;

    public Group(String Name) {
        this.Name = Name;
    }
}

@Dao
interface GroupDao {
    @Insert
    void insert(Group group);

    @Query("DELETE FROM `Group` WHERE IDGroup = :idGroup")
    void delete(int idGroup);

    @Update
    void update(Group group);

    @Query("SELECT * FROM `Group`")
    List<Group> selectAllGroups();

    @Query("SELECT Max(IDGroup) FROM `Group` WHERE Name = :name")
    int getGroupByName(String name);
}


@Entity(primaryKeys = {"IDUser", "IDList2"},
        foreignKeys = {
            @ForeignKey(entity = User.class,
                    parentColumns = "IDUser",
                    childColumns = "IDUser"
            ),
            @ForeignKey(entity = Lists.class,
                    parentColumns = "IDList",
                    childColumns = "IDList2")
        },
        indices = {@Index("IDUser"), @Index("IDList2")})
class ListUser {
    @ColumnInfo(name = "IDUser")
    int IDUser;

    @ColumnInfo(name = "IDList2")
    int IDList;

    public ListUser(int IDList, int IDUser) {
        this.IDList = IDList;
        this.IDUser = IDUser;
    }

    public int getIDList() {
        return IDList;
    }

    public int getIDUser() {
        return IDUser;
    }
}

@Dao
interface ListUserDao {
    @Insert
    void insert(ListUser listUser);

    @Query("DELETE FROM ListUser WHERE IDList2 =:idList")
    void deleteListUser(int idList);
}


@Entity
class Product {
    @PrimaryKey(autoGenerate = true)
    public Integer IDProduct;

    public String Name;

    public Product(String Name) {
        this.Name = Name;
    }
}

@Dao
interface ProductDao {
    @Insert
    void insert(Product product);

    @Query("SELECT Max(IDProduct) FROM Product WHERE Name = :name")
    int selectProductID(String name);
}



@Entity(foreignKeys = {
            @ForeignKey(entity = Group.class,
                parentColumns = "IDGroup",
                childColumns = "IDGroup2"
            )
        },
        indices = {@Index("IDGroup2")})
class GroupItem {
    @PrimaryKey(autoGenerate = true)
    public int IDGroupItem;

    @ColumnInfo( name = "IDGroup2")
    public int IDGroup;

    public String ProductName;

    public double Amount;

    public String Unit;

    public GroupItem(int IDGroup, String ProductName, double Amount, String Unit) {
        this.IDGroup = IDGroup;
        this.ProductName = ProductName;
        this.Amount = Amount;
        this.Unit = Unit;
    }
}

@Dao
interface GroupItemDao {
    @Insert
    void insert(GroupItem groupItem);

    @Query("DELETE FROM GroupItem WHERE IDGroup2 = :idGroup")
    void deleteGroupItemByIDGroup(int idGroup);

    @Query("DELETE FROM GroupItem WHERE IDGroupItem = :idGroupItem")
    void deleteSingleGroupItem(int idGroupItem);

    @Update
    void update(GroupItem groupItem);

    @Query("SELECT * FROM GroupItem WHERE IDGroup2 = :idGroup")
    List<GroupItem> selectAllGroupItemFormGroup(int idGroup);
}



@Entity(foreignKeys = {
            @ForeignKey(entity = Lists.class,
                    parentColumns = "IDList",
                    childColumns = "IDList"
            )
        },
        indices = {@Index("IDList")})
class ListItem {
    @PrimaryKey(autoGenerate = true)
    public int IDListItem;

    public int IDList;

    public String ProductName;

    public String Status;

    public double Amount;

    public String Unit;

    public String color;

    public ListItem(int IDList, String ProductName, String Status, double Amount, String Unit, String color) {
        this.IDList = IDList;
        this.ProductName = ProductName;
        this.Status = Status;
        this.Amount = Amount;
        this.Unit = Unit;
        this.color = color;
    }
}

@Dao
interface ListItemDao {
    @Insert
    void insert(ListItem item);

    @Query("DELETE FROM ListItem WHERE IDListItem = :idListItem")
    void delete(int idListItem);

    @Query("DELETE FROM ListItem WHERE IDList = :idList")
    void deleteByIDList(int idList);

    @Update
    void update(ListItem listItem);

    @Query("SELECT * FROM ListItem WHERE IDList = :idList")
    List<ListItem> selectListItemFromList(int idList);
}


@Entity(primaryKeys = {"IDProduct1", "IDProduct2"},
        foreignKeys = {
            @ForeignKey(entity = Product.class,
                    parentColumns = "IDProduct",
                    childColumns = "IDProduct1"
            ),
            @ForeignKey(entity = Product.class,
                    parentColumns = "IDProduct",
                    childColumns = "IDProduct2")
        },
        indices = {@Index("IDProduct1"), @Index("IDProduct2")})
class Distance {
    @ColumnInfo(name = "IDProduct1")
    public int IDProduct1;

    @ColumnInfo(name = "IDProduct2")
    public int IDProduct2;

    public double Distance;

    Distance(int IDProduct1, int IDProduct2, double Distance) {
        this.IDProduct1 = IDProduct1;
        this.IDProduct2 = IDProduct2;
        this.Distance = Distance;
    }
}

@Dao
interface DistanceDao {
    @Insert
    void insert(Distance distance);

    @Update
    void update(Distance distance);
}


class ListUserAndList {
    @Embedded
    ListUser listUser;

    @Relation(parentColumn = "IDList2",
                entityColumn = "IDList")
    public List<Lists> userList;
}

@Dao
interface ListUserAndListDao {
    @Transaction
    @Query("SELECT * FROM ListUser WHERE IDUser=:id")
    List<ListUserAndList> getListsWithUsers(int id);
}