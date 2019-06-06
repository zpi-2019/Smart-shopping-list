package com.example.smart_shopping_list_app;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    void insertNewListItem(ListItem listItem) { new InsertListItemAsync(listItemDao, listsDao).execute(listItem); }

    private static class InsertListItemAsync extends AsyncTask<ListItem, Void, Void> {
        private ListItemDao listItemDao;
        private ListsDao listsDao;

        InsertListItemAsync(ListItemDao listItemDao, ListsDao listsDao) {
            this.listItemDao = listItemDao;
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(final ListItem... params){
            listItemDao.insert(params[0]);
            listsDao.updateDate(params[0].IDList);
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


    void insertNewListUser(ListUser listUser) { new InsertListUserAsync(listUserDao, listsDao).execute(listUser);}

    private static class InsertListUserAsync extends AsyncTask<ListUser, Void, Void> {
        private ListUserDao listUserDao;
        private ListsDao listsDao;

        InsertListUserAsync(ListUserDao listUserDao, ListsDao listsDao) {
            this.listUserDao = listUserDao;
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(ListUser... listUsers) {
            listUserDao.insert(listUsers[0]);
            listsDao.updateDate(listUsers[0].IDList);
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


    void deleteSingleListItem(int idListItem, int idList) { new DeleteSingleListItemAsync(listItemDao, listsDao).execute(idListItem, idList); }

    private static class DeleteSingleListItemAsync extends AsyncTask<Integer, Void, Void> {
        private ListItemDao listItemDao;
        private ListsDao listsDao;

        DeleteSingleListItemAsync(ListItemDao listItemDao, ListsDao listsDao) {
            this.listItemDao = listItemDao;
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            listItemDao.delete(integers[0]);
            listsDao.updateDate(integers[1]);
            return null;
        }
    }

    void deleteListItemByIDList(int idList) { new DeleteListItemByIDListAsync(listItemDao, listsDao).execute(idList); }

    private static class DeleteListItemByIDListAsync extends AsyncTask<Integer, Void, Void> {
        private ListItemDao listItemDao;
        private ListsDao listsDao;

        DeleteListItemByIDListAsync(ListItemDao listItemDao, ListsDao listsDao) {
            this.listItemDao = listItemDao;
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            listItemDao.deleteByIDList(integers[0]);
            listsDao.updateDate(integers[0]);
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


    void deleteAllDistances() { new DeleteAllDistancesAsync(distanceDao).execute(); }

    private static class DeleteAllDistancesAsync extends AsyncTask<Void, Void, Void> {
        private DistanceDao distanceDao;

        DeleteAllDistancesAsync(DistanceDao distanceDao) {
            this.distanceDao = distanceDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            distanceDao.deleteAll();
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


    List<Integer> selectAllProductsID() {
        List<Integer> result = null;
        try {
            result = new SelectAllProductsIDAsync(productDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class SelectAllProductsIDAsync extends AsyncTask<Void, Void, List<Integer>> {
        private ProductDao productDao;

        SelectAllProductsIDAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected List<Integer> doInBackground(Void... voids) {
            return productDao.selectAllProductsID();
        }
    }


    String selectProductName(int id) {
        String result = "";
        try {
            result = new SelectProductNameAsync(productDao).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class SelectProductNameAsync extends AsyncTask<Integer, Void, String> {
        private ProductDao productDao;

        SelectProductNameAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            return productDao.selectProductName(integers[0]);
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


    List<Distance> selectAllDistances(){
        List<Distance> list = null;
        try {
            list = new SelectAllDistancesAsync(distanceDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class SelectAllDistancesAsync extends AsyncTask<Void, Void, List<Distance>>{
        private DistanceDao distanceDao;

        SelectAllDistancesAsync(DistanceDao distanceDao) {
            this.distanceDao = distanceDao;
        }

        @Override
        protected List<Distance> doInBackground(Void... voids) {
            return distanceDao.selectAllDistances();
        }
    }


    String selectUserEmail(int id){
        String result = "";
        try {
            result = new SelectUserEmailAsync(userDao).execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class SelectUserEmailAsync extends AsyncTask<Integer, Void, String> {
        private UserDao userDao;

        SelectUserEmailAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            return userDao.getUserEmail(integers[0]);
        }
    }


    int selectUserId(String email){
        int result = 0;
        try {
            result = new SelectUserIdAsync(userDao).execute(email).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class SelectUserIdAsync extends AsyncTask<String, Void, Integer> {
        private UserDao userDao;

        SelectUserIdAsync(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return userDao.getUserID(strings[0]);
        }
    }


    //////////////Update//////////////

    void updateListItem(ListItem listItem) { new UpdateListItemAsync(listItemDao, listsDao).execute(listItem); }

    private static class UpdateListItemAsync extends AsyncTask<ListItem, Void, Void> {
        private ListItemDao listItemDao;
        private ListsDao listsDao;

        UpdateListItemAsync(ListItemDao listItemDao, ListsDao listsDao) {
            this.listItemDao = listItemDao;
            this.listsDao = listsDao;
        }

        @Override
        protected Void doInBackground(ListItem... listItems) {
            listItemDao.update(listItems[0]);
            listsDao.updateDate(listItems[0].IDList);
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
            listsDao.updateDate(lists[0].IDList);
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


    void asyncCalc(MyRecomRecyclerViewAdapter adapter, List<ListItem> mValues) { new AsyncCalc(adapter, productDao, distanceDao).execute(mValues);}


    private static class AsyncCalc extends AsyncTask<List<ListItem>, Void, List<AddListItemFragment.Recommendations>>{
        private MyRecomRecyclerViewAdapter adapter;
        private ProductDao productDao;
        private DistanceDao distanceDao;

        AsyncCalc(MyRecomRecyclerViewAdapter adapter, ProductDao productDao, DistanceDao distanceDao) {
            this.adapter = adapter;
            this.productDao = productDao;
            this.distanceDao = distanceDao;
        }

        boolean containsItem(List<ListItem> list, String name){
            for(ListItem item: list){
                if(item.ProductName.equals(name))
                    return true;
            }
            return false;
        }

        String returnName(int id, List<Product> allProducts){
            for(Product item: allProducts){
                if(item.IDProduct == id)
                    return item.Name;
            }
            return "";
        }

        @Override
        protected final List<AddListItemFragment.Recommendations> doInBackground(List<ListItem>... lists) {
            List<Product> allProducts = productDao.selectAllProducts();
            HashMap<Integer, Double> distancesSum = new HashMap<>();
            List<AddListItemFragment.Recommendations> recom = new ArrayList<>();
            for(Product item: allProducts){
                if(!containsItem(lists[0], item.Name))
                    distancesSum.put(item.IDProduct, 0.0);
            }
            for(ListItem item: lists[0]){
                List<Distance> distances = distanceDao.selectDistancesWithID(productDao.selectProductID(item.ProductName));
                for(Distance item2: distances){
                    if(distancesSum.containsKey(item2.IDProduct1))
                        distancesSum.put(item2.IDProduct1, distancesSum.get(item2.IDProduct1) + item2.Distance);
                    if(distancesSum.containsKey(item2.IDProduct2))
                        distancesSum.put(item2.IDProduct2, distancesSum.get(item2.IDProduct2) + item2.Distance);
                }
            }

            for (Object o : distancesSum.entrySet()) {
                Map.Entry pair = (Map.Entry) o;
                recom.add(new AddListItemFragment.Recommendations(returnName((Integer) pair.getKey(), allProducts), (Double) pair.getValue()));
            }
            Collections.sort(recom);
            return recom;
        }

        @Override
        protected void onPostExecute(List<AddListItemFragment.Recommendations> list) {
            adapter.setmValues(list);
            adapter.notifyDataSetChanged();
        }
    }
}
