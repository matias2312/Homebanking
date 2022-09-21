package com.minhub.homebanking;

import com.minhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
}
