package com.gps.web.tiles;

import com.gps.service.ClientService;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;



public class LeftMenuController implements ViewPreparer {
	
    @Autowired	
    ClientService clientCommonService;

	public void execute(TilesRequestContext context,
			AttributeContext attributeContext) throws PreparerException {
		
		Menu menu =new Menu("Configuration");
		ArrayList<LeftMenuController.Menu> menuList = new ArrayList<LeftMenuController.Menu>();
		ArrayList<LeftMenuController.MenuItem> menuItemList = new ArrayList<LeftMenuController.MenuItem>();
		menuItemList.add(new MenuItem("Data Migration", "data_migration.htm"));
		menuItemList.add(new MenuItem("About", "about.jsp"));
		menu.setMenuItems(menuItemList);
		menuList.add(menu);
		context.getRequestScope().put("menuList", menuList);
	}
	public static class Menu {
		private String caption;
		ArrayList<LeftMenuController.MenuItem> menuItems = new ArrayList<LeftMenuController.MenuItem>();

		public Menu(String caption) {
			this.caption=caption;
		}

		public ArrayList<LeftMenuController.MenuItem> getMenuItems() {
			return menuItems;
		}

		public String getCaption() {
			return caption;
		}

		public void setCaption(String caption) {
			this.caption = caption;
		}

		public void setMenuItems(ArrayList<LeftMenuController.MenuItem> menuItems) {
			this.menuItems = menuItems;
		}
		
	}
	
	
	public static class MenuItem {
		private String url;
		private String caption;

		public MenuItem(String caption, String url) {
			this.caption = caption;
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public String getCaption() {
			return caption;
		}
	}
}
