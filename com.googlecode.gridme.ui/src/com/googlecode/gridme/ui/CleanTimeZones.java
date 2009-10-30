/*******************************************************************************
 * Copyright (c) 2009 Dmitry Grushin <dgrushin@gmail.com>.
 * 
 * This file is part of GridMe.
 * 
 * GridMe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GridMe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with GridMe.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Dmitry Grushin <dgrushin@gmail.com> - initial API and implementation
 ******************************************************************************/
package com.googlecode.gridme.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.TimeZone;

/** CleanTimeZones
 * This class is used to generate an alphabetically sorted list of
 * available time zone IDs, that has less redundancy than the list
* provided by TimeZone.getAvailableIDs().
*
* Three letter abbreviations are removed unless it is "UTC" or the
* abbreviation has a unique set of time zone rules.
*
* US Time zones, and UTC, are moved to the top of the list. */
public class CleanTimeZones
{
  /**
  * list, This holds our time zone list. It is generated only once per
  * application run.
  */
  static private String[] list = null;

  /** generateList
  * This is called to generate the times zone list. */
  static private String[] generateList()
  {
    // Get our starting list.
    ArrayList<String> startList = new ArrayList<String>(Arrays.asList(TimeZone
        .getAvailableIDs()));
    Collections.sort(startList);
    // Loop through the available time zones, checking for possible deletions.
    for(Iterator<String> iter = startList.iterator(); iter.hasNext();)
    {
      String current = iter.next();
      if(shouldDelete(current, (ArrayList<String>) startList.clone()))
      {
        iter.remove();
      }
    }
    // Move anything starting with the letter "U" to the top of the list.
    ArrayList<String> uList = new ArrayList<String>();
    for(Iterator<String> iter = startList.iterator(); iter.hasNext();)
    {
      String current = iter.next();
      if(current.toLowerCase().startsWith("u"))
      {
        uList.add(current);
        iter.remove();
      }
    }
    for(int i = (uList.size() - 1); i >= 0; --i)
    {
      startList.add(0, uList.get(i));
    }

    return startList.toArray(new String[0]);
  }

  /** get
  * Call this to get the cleaned list of time zones. */
  static public String[] get()
  {
    if(list == null)
    {
      list = generateList();
    }
    return list;
  }

  /** shouldDelete
  * This is called to determine if a time zone should be deleted. */
  static private boolean shouldDelete(String zoneString,
      ArrayList<String> copyOfRemainingList)
  {
    // Define a list of duplicate zones that you want to remove.
    String[] deleteZones = new String[] { "Etc/Greenwich", "Etc/UCT",
        "Etc/UTC", "Etc/Universal", "Etc/Zulu", "Zulu", "Etc/GMT-0",
        "Etc/GMT0", "GMT0", "Greenwich", "Universal" };
    // Define a list of zone prefixes that you wish to keep.
    String[] protectedPrefixes = new String[] { "UTC", "US/" };
    // Get the time zone instance from the zone string.
    TimeZone zone = TimeZone.getTimeZone(zoneString);
    // Calculate how many copies remain in the list.
    int copies = 0;
    for(String remainingString : copyOfRemainingList)
    {
      TimeZone remainingZone = TimeZone.getTimeZone(remainingString);
      if(remainingZone.hasSameRules(zone))
      {
        ++copies;
      }
    }
    // Remove all zones listed in deleteZones that are duplicates.
    for(String delete : deleteZones)
    {
      if(zoneString.equalsIgnoreCase(delete) && copies > 1)
      {
        return true;
      }
    }
    // Keep all protected prefixes.
    for(String protect : protectedPrefixes)
    {
      if(zoneString.toLowerCase().startsWith(protect.toLowerCase()))
      {
        return false;
      }
    }
    // Remove all three letter zones that are duplicates.
    if(zoneString.length() == 3 && copies > 1)
    {
      return true;
    }
    return false;
  }
}
