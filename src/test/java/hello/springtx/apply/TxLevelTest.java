package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive;

@SpringBootTest
public class TxLevelTest {

    @Autowired
    LevelService levelService;

    @Test
    void orderTEst() throws Exception {
        levelService.readOnlyFalse();
        levelService.readOnly();
    }

    @TestConfiguration
    static class TxLevelTestConfig {
        @Bean
        LevelService levelService() {
            return new LevelService();
        }
    }

    @Slf4j
    @Transactional(readOnly = true)
    static class LevelService {

        @Transactional(readOnly = false)
        public void readOnlyFalse() {
            log.info("call readOnlyFalse");
            printTxInfo();
        }

        public void readOnly() {
            log.info("call readOnly");
            printTxInfo();

        }

        private void printTxInfo() {
            final boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active = {}", txActive);
            final boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("tx readOnly = {}", readOnly);
        }
    }
}
