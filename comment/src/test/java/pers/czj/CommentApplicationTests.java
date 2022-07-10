package pers.czj;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CommentApplicationTests {

    @Test
    void contextLoads() {
        List list = new ArrayList<>();
        list.forEach(System.out::println);
    }

    public static void main(String[] args) {
//        Collections.synchronizedMap()
    }

}
