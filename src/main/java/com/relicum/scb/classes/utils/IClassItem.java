package com.relicum.scb.classes.utils;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Bukkit-SCB
 *
 * @author Relicum
 * @version 0.1
 */
public interface IClassItem {


    public Material getMaterials(Material material);

    public ItemMeta getItemsMeta(Material mat);

    public ItemEnum getType();
}
