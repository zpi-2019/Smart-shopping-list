package com.example.smart_shopping_list_app;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

class Repository {
    private DistanceDao distanceDao;
    private GroupDao groupDao;
    private GroupItemDao groupItemDao;
    private ListItemDao listItemDao;
    private ListsDao listsDao;
    private ListUserDao listUserDao;
    private ProductDao productDao;
    private UserDao userDao;
    private ListUserAndListDao listUserAndListDao;


    Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        distanceDao = db.distanceDao();
        groupDao = db.groupDao();
        groupItemDao = db.groupItemDao();
        listItemDao = db.listItemDao();
        listsDao = db.listsDao();
        listUserDao = db.listUserDao();
        productDao = db.productDao();
        userDao = db.userDao();
        listUserAndListDao = db.listUserAndListDao();
    }

    //////////////Insert//////////////

    void insertNewListItem(ListItem listItem) { new InsertListItemAsync(listItemDao).execute(listItem); }

    private static class InsertListItemAsync extends AsyncTask<ListItem, Void, Void> {
        private ListItemDao listItemDao;

        InsertListItemAsync(ListItemDao listItemDao) {
            this.listItemDao = listItemDao;
        }

        @Override
        protected Void doInBackground(final ListItem... params){
            listItemDao.insert(params[0]);
            return null;
        }
    }


    void insertNewList(Lists list) { new InsertListsAsync(listsDao).execute(list); }

    private static class InsertListsAsync extends AsyncTask<Lists, Void, Void> {
        private ListsDao listsDao;

        InsertListsAsync(ListsDao listsDao) {
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(Lists... lists) {
            listsDao.insert(lists[0]);
            return null;
        }
    }


    void insertNewListUser(ListUser listUser) { new InsertListUserAsync(listUserDao).execute(listUser);}

    private static class InsertListUserAsync extends AsyncTask<ListUser, Void, Void> {
        private ListUserDao listUserDao;

        InsertListUserAsync(ListUserDao listUserDao) {
            this.listUserDao = listUserDao;
        }

        @Override
        protected Void doInBackground(ListUser... listUsers) {
            listUserDao.insert(listUsers[0]);
            return null;
        }
    }


    void insertNewGroup(Group group) { new InsertNewGroupAsync(groupDao).execute(group); }

    private static class InsertNewGroupAsync extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        InsertNewGroupAsync(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.insert(groups[0]);
            return null;
        }
    }


    void insertNewGroupItem(GroupItem groupItem) { new InsertNewGroupItemAsync(groupItemDao).execute(groupItem); }

    private static class InsertNewGroupItemAsync extends AsyncTask<GroupItem, Void, Void> {
        private GroupItemDao groupItemDao;

        InsertNewGroupItemAsync(GroupItemDao groupItemDao) {
            this.groupItemDao = groupItemDao;
        }

        @Override
        protected Void doInBackground(GroupItem... groupItems) {
            groupItemDao.insert(groupItems[0]);
            return null;
        }
    }


    void insertNewProduct(Product product) { new InsertNewProductAsync(productDao).execute(product); }

    private static class InsertNewProductAsync extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        InsertNewProductAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }


    void insertNewUser(User user) { new InsertNewUserAsync(userDao).execute(user); }

    private static class InsertNewUserAsync extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        InsertNewUserAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }


    void insertNewDistance(Distance distance) { new InsertNewDistanceAsync(distanceDao).execute(distance);}

    private static class InsertNewDistanceAsync extends AsyncTask<Distance, Void, Void> {
        private DistanceDao distanceDao;

        InsertNewDistanceAsync(DistanceDao distanceDao) {
            this.distanceDao = distanceDao;
        }

        @Override
        protected Void doInBackground(Distance... distances) {
            distanceDao.insert(distances[0]);
            return null;
        }
    }



    //////////////Delete//////////////

    void deleteListUser(int idList) { new DeleteListUserAsync(listUserDao).execute(idList); }

    private static class DeleteListUserAsync extends AsyncTask<Integer, Void, Void> {
        private ListUserDao listUserDao;

        DeleteListUserAsync(ListUserDao listUserDao) {
            this.listUserDao = listUserDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            listUserDao.deleteListUser(integers[0]);
            return null;
        }
    }


    void deleteList(int idList) { new DeleteListAsync(listsDao).execute(idList); }

    private static class DeleteListAsync extends AsyncTask<Integer, Void, Void> {
        private ListsDao listsDao;

        DeleteListAsync(ListsDao listsDao) {
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            listsDao.delete(integers[0]);
            return null;
        }
    }


    void deleteUser(int idUser) { new DeleteUserAsync(userDao).execute(idUser); }

    private static class DeleteUserAsync extends AsyncTask<Integer, Void, Void> {
        private UserDao userDao;

        DeleteUserAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            userDao.delete(integers[0]);
            return null;
        }
    }


    void deleteSingleListItem(int idListItem) { new DeleteSingleListItemAsync(listItemDao).execute(idListItem); }

    private static class DeleteSingleListItemAsync extends AsyncTask<Integer, Void, Void> {
        private ListItemDao listItemDao;

        DeleteSingleListItemAsync(ListItemDao listItemDao) {
            this.listItemDao = listItemDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            listItemDao.delete(integers[0]);
            return null;
        }
    }

    void deleteListItemByIDList(int idList) { new DeleteListItemByIDListAsync(listItemDao).execute(idList); }

    private static class DeleteListItemByIDListAsync extends AsyncTask<Integer, Void, Void> {
        private ListItemDao listItemDao;

        DeleteListItemByIDListAsync(ListItemDao listItemDao) {
            this.listItemDao = listItemDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            listItemDao.deleteByIDList(integers[0]);
            return null;
        }
    }


    void deleteGroup(int idGroup) { new DeleteGroupAsync(groupDao).execute(idGroup); }

    private static class DeleteGroupAsync extends AsyncTask<Integer, Void, Void> {
        private GroupDao groupDao;

        DeleteGroupAsync(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            groupDao.delete(integers[0]);
            return null;
        }
    }


    void deleteGroupItemByIDGroup(int idGroup) { new DeleteGroupItemByIDGroupAsync(groupItemDao).execute(idGroup); }

    private static class DeleteGroupItemByIDGroupAsync extends AsyncTask<Integer, Void, Void> {
        private GroupItemDao groupItemDao;

        DeleteGroupItemByIDGroupAsync(GroupItemDao groupItemDao) {
            this.groupItemDao = groupItemDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            groupItemDao.deleteGroupItemByIDGroup(integers[0]);
            return null;
        }
    }


    void deleteSingleGroupItem(int idGroupItem) { new DeleteSingleGroupItemAsync(groupItemDao).execute(idGroupItem); }

    private static class DeleteSingleGroupItemAsync extends AsyncTask<Integer, Void, Void> {
        private GroupItemDao groupItemDao;

        DeleteSingleGroupItemAsync(GroupItemDao groupItemDao) {
            this.groupItemDao = groupItemDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            groupItemDao.deleteSingleGroupItem(integers[0]);
            return null;
        }
    }



    //////////////Select//////////////

    List<ListItem> selectListItemJoinProduct(int idList) {
        List<ListItem> list = null;
        try {
            list = new SelectListItemJoinProductAsync(listItemDao).execute(idList).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class SelectListItemJoinProductAsync extends AsyncTask<Integer, Void, List<ListItem>> {
        private ListItemDao listItemDao;

        SelectListItemJoinProductAsync(ListItemDao listItemDao) {
            this.listItemDao = listItemDao;
        }

        @Override
        protected List<ListItem> doInBackground(Integer... integers) {
            return listItemDao.selectListItemFromList(integers[0]);
        }
    }


    List<ListUserAndList> getAllUsersLists(int idUser) {
        List<ListUserAndList> list = null;
        try {
            list = new GetAllUsersLists(listUserAndListDao).execute(idUser).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class GetAllUsersLists extends AsyncTask<Integer, Void, List<ListUserAndList>>{
        private ListUserAndListDao listUserDao;

        GetAllUsersLists(ListUserAndListDao listUserDao) {
            this.listUserDao = listUserDao;
        }

        @Override
        protected List<ListUserAndList> doInBackground(Integer... integers) {
            return listUserDao.getListsWithUsers(integers[0]);
        }
    }


    int getListID(String name){
        int result = 0;
        try {
            result = new GetListID(listsDao).execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class GetListID extends AsyncTask<String, Void, Integer>{
        private ListsDao listsDao;

        GetListID(ListsDao listsDao){
            this.listsDao = listsDao;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return listsDao.getListByName(strings[0]);
        }
    }


    List<User> getAllUsers(){
        List<User> list = null;
        try {
            list = new GetUsersList(userDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class GetUsersList extends AsyncTask<Void, Void, List<User>>{
        private UserDao userDao;

        GetUsersList(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }
    }


    int selectProductID(String name) {
        int result = 0;
        try {
            result = new SelectProductIDAsync(productDao).execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class SelectProductIDAsync extends AsyncTask<String, Void, Integer> {
        private ProductDao productDao;

        SelectProductIDAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return productDao.selectProductID(strings[0]);
        }
    }


    List<Group> selectAllGroups() {
        List<Group> list = null;
        try {
            list = new SelectAllGroupsAsync(groupDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class SelectAllGroupsAsync extends AsyncTask<Void, Void, List<Group>> {
        private GroupDao groupDao;

        SelectAllGroupsAsync(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected List<Group> doInBackground(Void... voids) {
            return groupDao.selectAllGroups();
        }
    }


    List<GroupItem> selectAllGroupItemFromGroup(int idGroup) {
        List<GroupItem> list = null;
        try {
            list = new SelectAllGroupItemFromGroupAsync(groupItemDao).execute(idGroup).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class SelectAllGroupItemFromGroupAsync extends AsyncTask<Integer, Void, List<GroupItem>> {
        private GroupItemDao groupItemDao;

        SelectAllGroupItemFromGroupAsync(GroupItemDao groupItemDao) {
            this.groupItemDao = groupItemDao;
        }

        @Override
        protected List<GroupItem> doInBackground(Integer... integers) {
            return groupItemDao.selectAllGroupItemFormGroup(integers[0]);
        }
    }


    int selectGroupID(String name){
        int result = 0;
        try {
            result = new SelectGroupIDAsync(groupDao).execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class SelectGroupIDAsync extends AsyncTask<String, Void, Integer>{
        private GroupDao groupDao;

        SelectGroupIDAsync(GroupDao groupDao){
            this.groupDao = groupDao;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return groupDao.getGroupByName(strings[0]);
        }
    }


    //////////////Update//////////////

    void updateListItem(ListItem listItem) { new UpdateListItemAsync(listItemDao).execute(listItem); }

    private static class UpdateListItemAsync extends AsyncTask<ListItem, Void, Void> {
        private ListItemDao listItemDao;

        UpdateListItemAsync(ListItemDao listItemDao) {
            this.listItemDao = listItemDao;
        }

        @Override
        protected Void doInBackground(ListItem... listItems) {
            listItemDao.update(listItems[0]);
            return null;
        }
    }


    void updateList(Lists list) { new UpdateListAsync(listsDao).execute(list); }

    private static class UpdateListAsync extends AsyncTask<Lists, Void, Void> {
        private ListsDao listsDao;

        UpdateListAsync(ListsDao listsDao) {
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(Lists... lists) {
            listsDao.update(lists[0]);
            return null;
        }
    }


    void updateGroupItem(GroupItem groupItem) { new UpdateGroupItemAsync(groupItemDao).execute(groupItem); }

    private static class UpdateGroupItemAsync extends AsyncTask<GroupItem, Void, Void> {
        private GroupItemDao groupItemDao;

        UpdateGroupItemAsync(GroupItemDao groupItemDao) {
            this.groupItemDao = groupItemDao;
        }

        @Override
        protected Void doInBackground(GroupItem... groupItems) {
            groupItemDao.update(groupItems[0]);
            return null;
        }
    }


    void updateGroup(Group group) { new UpdateGroupAsync(groupDao).execute(group); }

    private static class UpdateGroupAsync extends AsyncTask<Group, Void, Void> {
        private GroupDao groupDao;

        UpdateGroupAsync(GroupDao groupDao) {
            this.groupDao = groupDao;
        }

        @Override
        protected Void doInBackground(Group... groups) {
            groupDao.update(groups[0]);
            return null;
        }
    }


    void updateDistance(Distance distance) { new UpdateDistanceAsync(distanceDao).execute(distance); }

    private static class UpdateDistanceAsync extends AsyncTask<Distance, Void, Void> {
        private DistanceDao distanceDao;

        UpdateDistanceAsync(DistanceDao distanceDao) {
            this.distanceDao = distanceDao;
        }

        @Override
        protected Void doInBackground(Distance... distances) {
            distanceDao.update(distances[0]);
            return null;
        }
    }
}