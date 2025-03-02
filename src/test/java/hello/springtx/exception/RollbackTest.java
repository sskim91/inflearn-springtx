package hello.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService service;

    @Test
    void runtimeException()  {
        assertThrows(RuntimeException.class, () -> {
            service.runtimeException();
        });
    }

    @Test
    void checkedException() {
        assertThrows(MyException.class, () -> {
            service.checkedException();
        });
    }

    @Test
    void rollbackForCheckedException() {
        assertThrows(MyException.class, () -> {
            service.rollbackForCheckedException();
        });
    }

    @TestConfiguration
    static class RollbackTestConfig {

        @Bean
        public RollbackService rollbackService() {
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService {

        //런타임 예외 발생: 롤백
        @Transactional
        public void runtimeException() {
            log.info("call runtimeException");
            throw new RuntimeException();
        }

        //체크 예외 발생: 커밋
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();
        }

        //체크 예외 발생 rollbackFor 지정: 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackForCheckedException() throws MyException {
            log.info("call rollbackForCheckedException");
            throw new MyException();
        }
    }

    static class MyException extends Exception {

    }
}
