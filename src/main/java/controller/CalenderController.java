package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.components.calendar.CalendarComponentEvents;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import controller.module.Modul;
import model.Course;
import model.User;
import view.MyUI;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Lars Assmus on 09.06.2016.
 */

//TODO: font size im css/Sass ändern
public class CalenderController extends Modul {

    private MyUI ui;
    private Calendar cal;
    private User user;

    public CalenderController(User user, MyUI ui) {
        super(user);
        this.ui = ui;
        this.user = user;
        cal = new Calendar();

        //TODO: Richtige werte einfügen
        cal.setWidth(calcCalWidth(), Sizeable.Unit.PIXELS);
        cal.setHeight("1000px");
        //Calender formatierung
        cal.setReadOnly(true);
        cal.setLastVisibleDayOfWeek(5);
        cal.setFirstVisibleHourOfDay(8);
        cal.setLastVisibleHourOfDay(19);
        cal.setEventCaptionAsHtml(true);
        cal.setHandler((CalendarComponentEvents.DateClickHandler)null);
        setEvents();

        //Styling stuff. Find a way, that works without the extra layout.
        CssLayout container = new CssLayout();
        container.addComponent(cal);

        cal.setStyleName("calender");
        container.setStyleName("moduleSimple");
        layout.setStyleName("page");
        layout.setWidth("100%");

        this.layout.addComponent(container);

        //Browser window size changed listener.
        ui.getPage().addBrowserWindowResizeListener(event ->
                cal.setWidth(calcCalWidth(), Sizeable.Unit.PIXELS));

    }

    /**
     * Calculate the width of the calendar.
     * @return Calendar width in pixel.
     */
    private int calcCalWidth() {
        int naviWidth = 180;
        int pagePadding = 40;
        int modulePadding = 15;
        int scrollBar = 25;

        int browserWidth = ui.getPage().getBrowserWindowWidth();

        return browserWidth - (scrollBar + naviWidth + 2 * pagePadding + 2 * modulePadding);
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
                        String hour = Integer.toString(time.getHour());
                        String min = Integer.toString(time.getMinute());
                        //Time formate.
                        if (time.getHour() < 10) {
                            hour = "0" + hour;
                        }

                        if (time.getMinute() < 10) {
                            min = "0" + min;
                        }

                                //Layout
                        return  "<div id=\"title\">" + co.getName() + "</div>" +

                                "<div id=\"row\">" +
                                "<div>" +
                                "<img src=\"/VAADIN/themes/mytheme/Icons/date.png\" id=\"image\">" +
                                "</div>" +
                                "<div id=\"label\">" + hour + ":" + min+ "</div>" +
                                "</div>" +

                                "<div id=\"row\">" +
                                "<div>" +
                                "<img src=\"/VAADIN/themes/mytheme/Icons/location.png\" id=\"image\">" +
                                "</div>" +
                                "<div id=\"label\">" + co.getRoom() + "</div>" +
                                "</div>" +

                                //Styling
                                "<style type=\"\"> #title" +
                                "{margin-bottom: 5px;}" +
                                "</style>" +

                                "<style type=\"\"> #row" +
                                "{display: flex;}" +
                                "</style>" +

                                "<style type=\"text/css\"> #label" +
                                "{padding-top: 2px; padding-left: 2px; font-size: 17px;}" +
                                "</style>" +

                                "<style type=\"text/css\"> #image" +
                                "{width: 20px; height:20px; margin-left: 1px;}" +
                                "</style>";
                    }

                    @Override
                    public String getDescription() {
                        return co.getAdmin().getName();
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
