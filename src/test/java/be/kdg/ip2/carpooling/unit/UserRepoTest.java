package be.kdg.ip2.carpooling.unit;

import be.kdg.ip2.carpooling.configuration.SpringMongoConfig;
import be.kdg.ip2.carpooling.domain.User;
import be.kdg.ip2.carpooling.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
/*

@RunWith(SpringRunner.class)
@SpringBootTest
//@Slf4j
@ContextConfiguration(classes = SpringMongoConfig.class)
public class UserRepoTest {
    */
/*@Autowired
    private UserRepository repo;

    private ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    private MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");
*//*

    //TODO IMPORTANT NOTICE: If user does not have an id it will be saved as a new user if it does have an id it wil just update
    //TODO IMPORTANT NOTICE: Save is an UPSERT
*/
/*    @Before
    public void setUp() {
        //repo.deleteAll();
*//*
*/
/*        User user1 = new User("gino.moukhi@student.kdg.be","Snor123");
        User user2 = new User("kris.demuynck@kdg.be","321ronS");
        assertNull(user1.getId());
        assertNull(user2.getId());
        log.info("Before save: " + user1.toString());
        log.info("Before save: " + user2.toString());
//        repo.insert(user1);
//        repo.insert(user2);
        mongoOperations.save(user1);
        mongoOperations.save(user2);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
        log.info("After save: " + user1.toString());
        log.info("After save: " + user2.toString());*//*
*/
/*
    }*//*


    */
/*@Test
    public void clear() {
        repo.deleteAll();
    }*//*


    */
/*@Test
    public void testSomething() {
        User user = new User("gino.moukhi@student.kdg.be","Snor123");
    }

    @Test
    public void testFetchData() {
        User user1 = repo.findUserByEmail("gino.moukhi@student.kdg.be");
        assertNotNull(user1);
        assertEquals("Snor123",user1.getPassword());
        assertEquals(2,repo.findAll().size());
    }

    @Test
    public void testDataUpdate() {
        User user2 = repo.findUserByEmail("kris.demuynck@kdg.be");
        user2.setPassword("Snor789");
        //repo.save(user2);
        repo.save(user2);
        User user3 = repo.findUserByEmail("kris.demuynck@kdg.be");
        assertEquals("Snor789", user3.getPassword());
        //log.warn(user3.getPassword());
        assertEquals(2, repo.findAll().size());
    }

    @After
    public void tearDown() {
        //repo.deleteAll();
    }*//*

}
*/
