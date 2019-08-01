package com.sigma.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

/**
 * @author zen peng.
 * @version 1.0
 * date-time: 2018/6/11-12:31
 * BDDMockito
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseSpringBootTest {

    @Autowired
    private TestEntityManager entityManager;

    public void mockObjectDoc() {
        mock(BaseSpringBootTest.class);
        System.out.println("mock(Class classToMock);");
        System.out.println("when(mock.someMethod()).thenReturn(value)");
        System.out.println("doThrow(new RuntimeException()).when(mockedList).clear();\n" +
                "\n" +
                "//将会 抛出 RuntimeException:\n" +
                "\n" +
                "mockedList.clear();");

        System.out.println("@Test\n" +
                "\tpublic void testExample() throws Exception {\n" +
                "\t\tthis.entityManager.persist(new User(\"sboot\", \"1234\"));\n" +
                "\t\tUser user = this.repository.findByUsername(\"sboot\");\n" +
                "\t\tassertThat(user.getUsername()).isEqualTo(\"sboot\");\n" +
                "\t\tassertThat(user.getVin()).isEqualTo(\"1234\");\n" +
                "\t}");
    }
}
