package com.example.smart_shopping_list_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private Repository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    //////////////Insert//////////////

    void insertNewListItem(ListItem listItem) {
        repository.insertNewListItem(listItem);
    }

    void insertNewList(Lists list) {
        repository.insertNewList(list);
    }

    void insertNewListUser(ListUser listUser) { repository.insertNewListUser(listUser);}

    void insertNewGroup(Group group) { repository.insertNewGroup(group); }

    void insertNewGroupItem(GroupItem groupItem) { repository.insertNewGroupItem(groupItem); }

    void insertNewProduct(Product product) { repository.insertNewProduct(product); }

    void insertNewUser(User user) { repository.insertNewUser(user); }

    void insertNewDistance(Distance distance) { repository.insertNewDistance(distance); }


    //////////////Delete//////////////

    void deleteListUser(int idList) {
        repository.deleteListUser(idList);
    }

    void deleteList(int idList) { repository.deleteList(idList); }

    void deleteUser(int idUser) { repository.deleteUser(idUser); }

    void deleteSingleListItem(int idListItem, int idList) { repository.deleteSingleListItem(idListItem, idList); }

    void deleteListItemByIDList(int idList) { repository.deleteListItemByIDList(idList); }

    void deleteGroup(int idGroup) { repository.deleteGroup(idGroup); }

    void deleteGroupItemByIDGroup(int idGroup) { repository.deleteGroupItemByIDGroup(idGroup); }

    void deleteSingleGroupItem(int idGroupItem) { repository.deleteSingleGroupItem(idGroupItem);}

    void deleteAllDistances() { repository.deleteAllDistances(); }



    //////////////Select//////////////

    int getListID(String name){
        return repository.getListID(name);
    }

    List<User> getAllUsers(){
        return repository.getAllUsers();
    }

    List<ListItem> getAllProductsFromList(int id) { return repository.selectListItemJoinProduct(id); }

    List<ListUserAndList> getAllUsersLists(int idUser) { return repository.getAllUsersLists(idUser); }

    int selectProductID(String name) { return repository.selectProductID(name); }

    List<Integer> selectAllProductsID() { return repository.selectAllProductsID(); }

    String selectProductName(int id) { return repository.selectProductName(id); }

    List<Group> selectAllGroups() { return repository.selectAllGroups(); }

    List<GroupItem> selectAllGroupItemFromGroup(int idGroup) { return repository.selectAllGroupItemFromGroup(idGroup); }

    int selectGroupID(String name) { return repository.selectGroupID(name); }

    List<Distance> selectAllDistances() { return repository.selectAllDistances(); }

    String selectUserEmail(int id) { return repository.selectUserEmail(id); }

    int selectUserId(String email) { return repository.selectUserId(email); }


    //////////////Update//////////////

    void updateListItem(ListItem listItem) { repository.updateListItem(listItem); }

    void updateList(Lists list) { repository.updateList(list); }

    void updateGroupItem(GroupItem groupItem) { repository.updateGroupItem(groupItem); }

    void updateGroup(Group group) { repository.updateGroup(group); }

    void updateDistance(Distance distance) { repository.updateDistance(distance); }
}
