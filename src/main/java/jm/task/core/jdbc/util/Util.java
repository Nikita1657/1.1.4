package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testsql";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1";

    // Метод для получения соединения
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Соединение с базой данных установлено.");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных:");
            e.printStackTrace();
        }
        return connection;
    }
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Создаем объект конфига
            Configuration configuration = new Configuration();

            // Настройки Hibernate
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver"); // Драйвер для MySQL
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/testsql"); // URL базы данных
            settings.put(Environment.USER, "root"); // Имя пользователя
            settings.put(Environment.PASS, "root1"); // Пароль
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect"); // Диалект для MySQL 8
            settings.put(Environment.SHOW_SQL, "true"); // Показывать SQL-запросы в консоли
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread"); // Управление сессиями
            settings.put(Environment.HBM2DDL_AUTO, "update"); // Автоматическое обновление схемы базы данных

            // Применяем настройки
            configuration.setProperties(settings);

            // Добавляем классы юсеров
            configuration.addAnnotatedClass(User.class);

            // Создаем сессию
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Ошибка при создании SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Закрываем сессию при завершении работы
        getSessionFactory().close();
    }
}

