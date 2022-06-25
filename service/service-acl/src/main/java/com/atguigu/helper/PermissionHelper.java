package com.atguigu.helper;

import com.atguigu.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaQQ
 * @version 1.0
 * @date 2022/6/22 1:23
 */
public class PermissionHelper {
    /**
     * 构建父子关系
     * @param permissionList
     * @return
     */
    public  static List<Permission> build(List<Permission> permissionList){
        //用于储存菜单的容器
        List<Permission> menu = new ArrayList<>();
        //遍历permissionList
        for (Permission permission : permissionList) {
            //判断当前菜单是否是一级菜单
            if (permission.getParentId() == 0){
               // 获取子菜单
                permission.setChildren(getChildren(permission,permissionList));
                menu.add(permission);
            }
        }
        return menu;
    }

    private static List<Permission> getChildren(Permission permission,List<Permission> permissionList){
        //储存子菜单的集合
        List<Permission> children  = new ArrayList<>();

        //遍历集合
        for (Permission child : permissionList) {
//            判断child的parentId的和permission的id
            if (child.getParentId().equals(permission.getId())){
                child.setChildren(getChildren(child,permissionList));
                children.add(child);
            }
        }
        return children;
    };


}
