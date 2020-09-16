import database.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final Logger LOG = LogManager.getLogger("appLog");

    public static void main(String[] args) {
        final String CONFIG_FILE_NAME = "hibernate.cfg.xml";
        CreateSessionFactory ownSessionFactory = new CreateSessionFactory(CONFIG_FILE_NAME);
        Transaction transaction = null;
        try (Session session = ownSessionFactory.sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<LinkedPurchaseList> lplQuery = criteriaBuilder.createQuery(LinkedPurchaseList.class);
            CriteriaQuery<TeacherToCoursePool> t2CQuery = criteriaBuilder.createQuery(TeacherToCoursePool.class);

            LOG.debug("Before checking emtpty TeacherToCoursePool");
            /*
            Заполняем TeacherToCoursePool - таблицу связывания ManyToMany учителей и курсов для возможности
            добавления на один курс нескольких учителей. Предварительно проверяем, не заполнена ли она ранее.
             */
            if (session
                    .createQuery(t2CQuery.select(t2CQuery.from(TeacherToCoursePool.class)))
                    .list().isEmpty()) {
                LOG.debug("TeacherToCoursePool has to be created");
                fillingTeacherToCoursePool(session);
            }
            LOG.debug("Before checking emtpty LinkedPurchaseList");
            /*
            Заполняем таблицу LinkedPurchaseList из таблицы Subscriptions. Можно заполнить аналогичным способом
            из таблицы PurchaseList, для чего метод upgradePurchaseList(session) надо выполнить перед данным действием,
            но как сам upgrade PurchaseList, так и сама идея связать PurchaseList с остальными таблицами представляется
            иррациональной
             */
            if (session
                    .createQuery(lplQuery.select(lplQuery.from(LinkedPurchaseList.class)))
                    .list().isEmpty()) {
                fillLinkedPurchaseList(session);
            }
            LOG.debug("Before testing");
            /*
            Запуск тестов проверки связывания LinkedPurchaseList и TeacherToCoursePool с БД
             */
            testFilledTables(session);
            /*
            Апгрейдим PurchaseList, устанавливая ей в качестве ID естественный ключ Course-Students по их ID,
            не по именам!!
             */
            upgradePurchaseList(session);

            transaction.commit();

        } catch (Exception e) {
            try {
                assert transaction != null;
                if (transaction.getStatus().isOneOf(TransactionStatus.ACTIVE, TransactionStatus.MARKED_ROLLBACK)) {
                    transaction.rollback();
                }
            } catch (Exception rbex) {
                LOGGER.error(rbex);
                rbex.printStackTrace();
            }
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private static void fillingTeacherToCoursePool(Session session) {
        LOG.debug("Filling TeacherToCoursePool get started");
        String fillT2CQuery = "insert into TeacherToCoursePool(course, teacher) " +
                "select c, t " +
                "from Course c " +
                "join Teacher t on c.teacher = t";
        session.createQuery(fillT2CQuery).executeUpdate();
        LOG.info(session.createQuery("from TeacherToCoursePool").list().size() + " rows. TeacherToCoursePool was filled");
    }

    private static void fillLinkedPurchaseList(Session session) {
        LOG.debug("Filling LinkedPurchaseList get starting");
        CriteriaQuery<Subscriptions> sbsQuery = session.getCriteriaBuilder().createQuery(Subscriptions.class);
        Root<Subscriptions> s = sbsQuery.from(Subscriptions.class);
        s.fetch("id").fetch("course");
        s.fetch("id").fetch("student");
        sbsQuery.select(s);

        List<Subscriptions> subscriptionsList = session.createQuery(sbsQuery).list();

        final long[] count = {0};
        subscriptionsList.forEach(sb -> {
            LinkedPurchaseList linkedPurchaseList =
                    new LinkedPurchaseList(sb.getId().getCourse(), sb.getId().getStudent());
            linkedPurchaseList.setSubscriptionDate(sb.getSubscriptionDate());
            LOG.info("FROM Subscriptions " + sb.getId().getCourse().getName() + "-" +
                    sb.getId().getStudent().getName() + "-" +
                    sb.getSubscriptionDate());
            session.save(linkedPurchaseList);

            if (++count[0] % 100 == 0) {
                session.flush();
                session.clear();
                LOG.debug("Batch size is reached");
            }
        });
        LOG.debug("Filling LinkedPurchaseList is finished");
    }

    private static void testFilledTables(Session session) {
        LOG.info("Tests get starting");
         /*
        Тест 1 - Вывод списка студентов и их возраста для каждого курса из LinkedPurchaseList
         */
        List<LinkedPurchaseList> purchases = session.createQuery("from LinkedPurchaseList").list();
        purchases.forEach(p -> {
            System.out.println("Возраст студента " + p.getKey().getStudent().getName()
                    + " с курса " + p.getKey().getCourse().getName() +
                    " " + p.getKey().getStudent().getAge() + getWrittenYears(p.getKey().getStudent().getAge()));
        });

        LOG.info("Test1 completed");

        /*
        Тест 2 - добавление второго учителя на курс. После теста - удаление его из БД.
         */
        String plQuery = "from TeacherToCoursePool";
        List<TeacherToCoursePool> poolList = session.createQuery(plQuery).getResultList();
        LOG.info("Размер пула УЧИТЕЛЬ-КУРС - " + poolList.size());

        TeacherToCoursePool testTeacherToCoursePool =
                new TeacherToCoursePool((session.get(Course.class, 1)), session.get(Teacher.class, 2));
        session.saveOrUpdate(testTeacherToCoursePool);
        poolList = session.createQuery(plQuery).getResultList();
        LOG.info("Размер пула УЧИТЕЛЬ-КУРС - " + poolList.size());

        poolList.stream()
                .filter(f -> f.getCourse().getId() == 1)
                .forEach(fp -> LOG.info("Проверка добавления учителя в курс " + fp.toString()));
        LOG.info("TEST2 COMPLETED");
        session.delete(testTeacherToCoursePool);
    }

    private static void upgradePurchaseList(Session session) {
        String upgradePurshaseQuery = "insert into Purchase(course, student, courseName, studentName, price, subscriptionDate)" +
                " select c, s, p.courseName, p.studentName, p.price, p.subscriptionDate " +
                "from Purchase p " +
                "join Course c on p.courseName = c.name " +
                "join Students s on p.studentName = s.name";

        if (session.createQuery("select p.courseId from Purchase p")
                .getResultStream().anyMatch(p -> (int) p == 0)) {
            session.createQuery(upgradePurshaseQuery).executeUpdate();
            session.createQuery("delete Purchase p where p.courseId = 0").executeUpdate();
        }
        LOG.info("PurchaseList was upgraded");
    }

    private static String getWrittenYears(int age) {

        if (age % 10 == 1) {
            return "год";
        } else if (age % 10 == 2 || age % 10 == 3 || age % 10 == 4) {
            return "года";
        } else return "лет";
    }
}