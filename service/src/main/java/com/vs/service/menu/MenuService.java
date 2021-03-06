package com.vs.service.menu;

import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.model.user.User;
import com.vs.repository.DBOperations;
import com.vs.repository.MenuRepository;
import com.vs.service.SaveFile;
import jersey.repackaged.com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
public class MenuService implements IMenuService {

    @Autowired
    MenuRepository repository;

    @Autowired
    MongoTemplate template;

    @Autowired
    DBOperations dbOperations;


    @Override
    public void createUserMenu(Menu menu) {
        repository.insert(menu);
    }

    @Override
    public void updateUserMenu(Menu menu) {

        Menu nMenu = repository.findByMenuId(menu.getMenuId());

        List<Item> items = menu.getItems(ItemStatus.LOCKED);
        Preconditions.checkState((items.size() == 0), "Update NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Locked Items in menu. ");

        items = menu.getItems(ItemStatus.HOLD);
        Preconditions.checkState((items.size() == 0), "Update NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Hold Items in menu. ");

        repository.save(menu);
    }

    @Override
    public void deleteUserMenu(String userName, String menuId) throws Exception {

        Menu menu = repository.findByMenuId(menuId);
        Preconditions.checkNotNull(menu, "Menu not found:" + menuId);

        List<Item> items = menu.getItems(ItemStatus.LOCKED);
        Preconditions.checkState((items.size() == 0), "DELETE NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Locked Items in menu. ");

        items = menu.getItems(ItemStatus.HOLD);
        Preconditions.checkState((items.size() == 0), "DELETE NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Hold Items in menu. ");

        repository.delete(menuId);
    }

    @Override
    public List<Menu> getUserMenus(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public List<Menu> getUserMenuByName(String userName, String menuName) {
        List<Menu> menus = repository.findByUserNameAndName(userName, menuName);
        return menus;
    }

    @Override
    public List<Menu> getUserMenuByNameOrId(String userName, String menuNameOrId) {
        return repository.findByUserNameOrNameAndMenuId(userName, menuNameOrId, userName);
    }

    @Override
    public Menu getUserMenuById(String userName, String menuId) {
        return repository.findByUserNameAndMenuId(userName, menuId);
    }

    @Override
    public boolean menuExists(String menuId) {
        return repository.exists(menuId);
    }

    @Override
    public List<Menu> getMenus() {
        return repository.findAll();
    }

    @Override
    public List<Menu> getMenuByName(String menuName) {
        return repository.findByName(menuName);
    }


    @Override
    public Menu getMenuById(String menuId) {
        return repository.findByMenuId(menuId);
    }


}
