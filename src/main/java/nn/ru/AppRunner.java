package nn.ru;

import nn.ru.entity.User;
import nn.ru.entity.Post;
import nn.ru.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppRunner {

    private static final Logger logger = LogManager.getLogger(AppRunner.class);
    private static final Logger fileLogger = LogManager.getLogger("FileLogger");
    public static void main(String[] args) {
        // Создание конфигурации Hibernate и получение фабрики сессий
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(Tag.class);

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();

        session.beginTransaction();

        // Создание нового пользователя
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john@example.com");

        // Создание нового поста
        Post post = new Post();
        post.setTitle("Hibernate Tutorial");
        post.setContent("Learn Hibernate step by step");
        post.setUser(user);

        // Создание нового тега
        Tag tag = new Tag();
        tag.setName("Java");

        // Добавление тега к посту
        post.getTags().add(tag);

        // Добавление поста к пользователю
        user.getPosts().add(post);

        // Сохранение пользователя (каскадно сохранится и пост, и тег)
        session.save(user);

        session.getTransaction().commit();
        session.close();

        factory.close();
    }
}
