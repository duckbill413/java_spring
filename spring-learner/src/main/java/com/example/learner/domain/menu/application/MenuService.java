package com.example.learner.domain.menu.application;

import com.example.learner.domain.menu.dto.request.InsertMenus;
import com.example.learner.domain.menu.dto.response.MenuInfo;

public interface MenuService {
    void insertMenu(InsertMenus menusDto);

    MenuInfo findMenu(Long menuId);
}
