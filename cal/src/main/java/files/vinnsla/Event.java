/**
 * @ Author: Þorvaldur Tumi Baldursson
 * @ Create Time: 2022-02-21 10:28:56
 * @ Modified by: Tumi
 * @ Modified time: 2022-02-23 11:22:50
 */

package files.vinnsla;

import java.time.LocalDate;
import java.util.Comparator;

public class Event {
    private LocalDate date;
    private int start;
    private String title;
    private String description;

    public Event(LocalDate d, int h, String t, String de) {
        date = d;
        start = h;
        title = t;
        description = de;
    }

    public static Event.Comp startHourComparator() {
        return new Event.Comp();
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDate(LocalDate d) {
        this.date = d;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setStart(int s) {
        this.start = s;
    }

    public int getStart() {
        return this.start;
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (var1 == null) {
            return false;
        } else if (this.getClass() != var1.getClass()) {
            return false;
        } else {
            Event var2 = (Event) var1;
            return this.date.equals(var2.date) && this.start == var2.start && this.title.equals(var2.title)
                    && this.description.equals(var2.description);
        }
    }

    private static class Comp implements Comparator<Event> {
        @Override
        public int compare(Event thisEvent, Event thatEvent) {
            return Integer.compare(thisEvent.start, thatEvent.start);
        }
    }

    public String toString() {
        return " EVENT: {"
        + this.date
        + this.start
        + this.title
        + this.description+"}";
    }

    // public static void main(String[] args) {
    //     Event e = new Event(LocalDate.now(), 16, "Test færsla", "Þetta er færsla til þess að prófa event klasann");
    //     System.out.println(e.date);
    //     System.out.println(e.title);
    //     System.out.println(e.description);
    //     System.out.println(e.start);
    // }
}
