package com.store_inventory.pages;

import com.store_inventory.pages.components.UITheme;

public interface NavigationHandler {
  void navigate(String destination);
  void logout();
  void changeTheme(UITheme.ThemeMode mode);
}
