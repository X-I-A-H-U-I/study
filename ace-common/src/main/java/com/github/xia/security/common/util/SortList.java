package com.github.xia.security.common.util;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;

import java.lang.reflect.Method;

import java.lang.reflect.InvocationTargetException;


/**
 * @explain：sort工具类排序
 * @author: XIA
 * @date: 2020-01-20
 * @since: JDK 1.8
 * @version: 1.0
 */
public class SortList<E>{

  

  public void Sort(List<E> list, final String method){

     //排序

     Collections.sort(list, new Comparator() {        

         @Override
         public int compare(Object a, Object b) {

         int ret = 0;

         try{

             Method m1 = ((E)a).getClass().getMethod(method, null);

             Method m2 = ((E)b).getClass().getMethod(method, null);

             ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString());                  

         }catch(NoSuchMethodException ne){

             System.out.println(ne);

            }catch(IllegalAccessException ie){

                System.out.println(ie);

            }catch(InvocationTargetException it){

                System.out.println(it);

            }

         return ret;

         }

      });

  }

}