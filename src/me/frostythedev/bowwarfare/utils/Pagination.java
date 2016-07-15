package me.frostythedev.bowwarfare.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Programmed by Tevin on 7/8/2016.
 */
public class Pagination {

    public static <T> List<T> paginate(List<T> objects, int page, int items) {
        int min = page * items;
        int max = Math.min(min + items, objects.size());
        return objects.subList(min, max);
    }

    public static <T> List<List<T>> paginateAll(List<T> objects, int items) {
        List<List<T>> pages = Lists.newArrayList();
        for (int i = 0; i < getTotalPages(objects, items); i++) {
            pages.add(paginate(objects, i, items));
        }
        return pages;
    }

    public static int getTotalPages(List<?> objects, int items) {
        return (objects.size() + (items - 1)) / items;
    }
}
