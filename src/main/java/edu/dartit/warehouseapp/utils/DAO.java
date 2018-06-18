package edu.dartit.warehouseapp.utils;

import java.util.Collection;

/**
 * Created by vysokov-mg on 18.06.2018.
 */
abstract class DAO<T> {
    abstract Collection<T> getAll();


}
