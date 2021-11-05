package ru.kpfu.itis.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.helpers.processors.*;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.helpers.notifiers.MailMessageTemplate;
import ru.kpfu.itis.helpers.notifiers.MailNotifier;
import ru.kpfu.itis.helpers.notifiers.Notifier;
import ru.kpfu.itis.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        Properties dbProperties = new Properties();
        Properties mailProperties = new Properties();
        try {
            dbProperties.load(servletContext.getResourceAsStream("WEB-INF/properties/db.properties"));
            mailProperties.load(servletContext.getResourceAsStream("WEB-INF/properties/mail.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(dbProperties.getProperty("db.driver"));
        hikariConfig.setJdbcUrl(dbProperties.getProperty("db.url"));
        hikariConfig.setUsername(dbProperties.getProperty("db.user"));
        hikariConfig.setPassword(dbProperties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(dbProperties.getProperty("db.hikari.pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        servletContext.setAttribute("dataSource", dataSource);

        MailMessageTemplate mailMessageTemplate = new MailMessageTemplate();
        Notifier notifier = new MailNotifier(mailProperties);

        InfoProcessor registrationInfoProcessor = new UserInfoProcessor();
        InfoProcessor orderInfoProcessor = new OrderInfoProcessor();
        InfoProcessor orderUpdateInfoProcessor = new OrderUpdateInfoProcessor();
        InfoProcessor bookingInfoProcessor = new BookingInfoProcessor();

        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        RoomsRepository roomsRepository = new RoomsRepositoryJdbcTemplateImpl(dataSource);
        BookingsRepository bookingsRepository = new BookingsRepositoryJdbcTemplateImpl(dataSource);
        SuggestionsRepository suggestionsRepository = new SuggestionsRepositoryJdbcTemplateImpl(dataSource);
        OrdersRepository ordersRepository = new OrdersRepositoryJdbcTemplateImpl(dataSource);

        SecurityService securityService = new SecurityServiceImpl(usersRepository, registrationInfoProcessor);
        RoomService roomService = new RoomServiceImpl(roomsRepository);
        BookingService bookingService = new BookingServiceImpl(bookingsRepository, usersRepository, notifier,
                mailMessageTemplate, bookingInfoProcessor);
        OrderService orderService = new OrderServiceImpl(suggestionsRepository, ordersRepository, usersRepository,
                bookingsRepository, notifier, mailMessageTemplate, orderInfoProcessor, orderUpdateInfoProcessor);

        servletContext.setAttribute("securityService", securityService);
        servletContext.setAttribute("roomService", roomService);
        servletContext.setAttribute("bookingService", bookingService);
        servletContext.setAttribute("orderService", orderService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
          HikariDataSource hikariDataSource = (HikariDataSource) servletContextEvent.getServletContext().getAttribute("dataSource");
          hikariDataSource.close();
    }
}
