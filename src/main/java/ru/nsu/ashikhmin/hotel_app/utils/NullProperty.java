package ru.nsu.ashikhmin.hotel_app.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class NullProperty {

    public static String[] getNullPropertiesString(Object source){
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        emptyNames.add("id");
        for (PropertyDescriptor pd : propertyDescriptors){
            if(src.getPropertyValue(pd.getName()) == null){
                emptyNames.add(pd.getName());
            }
        }
        emptyNames.forEach(log::info);
        return emptyNames.toArray(new String[0]);
    }
}
