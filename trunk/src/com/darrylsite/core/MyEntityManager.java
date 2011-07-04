/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darrylsite.core;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
*/
public class MyEntityManager
{
 public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("sngamePU2");
}
