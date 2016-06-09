package controller;

import com.vaadin.ui.Calendar;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import controller.module.Modul;
import model.Course;
import model.User;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Lars Assmus on 09.06.2016.
 */

//TODO: font size im css/Sass ändern
public class CalenderController extends Modul {

    Calendar cal;
    User user;

    public CalenderController(User user) {
        super(user);
        this.user = user;
        cal = new Calendar();

        //TODO: Richtige werte einfügen
        cal.setWidth("1000px");
        cal.setHeight("1000px");
        //Calender formatierung
        cal.setReadOnly(true);
        cal.setLastVisibleDayOfWeek(5);
        cal.setFirstVisibleHourOfDay(8);
        cal.setLastVisibleHourOfDay(20);
        cal.setHandler((CalendarComponentEvents.DateClickHandler)null);
        setEvents();
        this.layout.addComponent(cal);

    }


    private void setEvents() {
        for(Course co :user.getCourses())
        {
            for(LocalDateTime time:co.getDates())
            {

                cal.addEvent(new CalendarEvent() {
                    @Override
                    public Date getStart() {
                        return  Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
                    }

                    @Override
                    public Date getEnd() {
                        return Date.from(time.plusMinutes(co.getDuration()).atZone(ZoneId.systemDefault()).toInstant());
                    }

                    @Override
                    public String getCaption() {
                        return co.getName()+"\t" + co.getRoom();
                    }

                    @Override
                    public String getDescription() {
                        return co.getRoom();
                    }

                    @Override
                    public String getStyleName() {
                        return null;
                    }

                    @Override
                    public boolean isAllDay() {
                        return false;
                    }
                });
            }
        }
    }


}
