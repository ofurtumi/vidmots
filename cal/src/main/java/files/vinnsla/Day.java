/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 10:50:43
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 14:02:52
 */

package files.vinnsla;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day {
    private LocalDate date;
    private List<Event> eventlist;

    public int getEventNum() {
        return eventlist.size();
    }
    
    public Day(LocalDate d) {
        date = d;
        eventlist = new ArrayList<Event>();
    }
    
    public LocalDate getDate() {
        return this.date;
    }

    public void addEvent(Event event) {
        eventlist.add(event);
        eventlist.sort(Event.startHourComparator());
    }

    public void removeEvent(Event event) {
        eventlist.remove(event);
    }

    public int compareTo(Day thatDay) {
        return this.date.compareTo(thatDay.date);
    }

    public Iterator<Event> iterator() {
        return this.eventlist.iterator();
    }
}
