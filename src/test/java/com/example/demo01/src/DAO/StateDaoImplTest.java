package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.State;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StateDaoImplTest {

    @SpyBean
    StateDao stateDao;

    @Test
    public void testfindByCountryOrderByNameAsc(){
        List<State>list= stateDao.findByCountryOrderByNameAsc(2);
        list.stream().forEach(state->assertNotNull(state));

    }
    @Test
    @Transactional
    public void testDeleteById(){
        stateDao.delete(1);
       State state= stateDao.getCategoryById(new State(1));
      assertNull(state);
    }

}