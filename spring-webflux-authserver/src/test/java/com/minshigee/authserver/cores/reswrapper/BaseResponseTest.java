package com.minshigee.authserver.cores.reswrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseResponseTest {

    @Test
    public void testBaseResponse() {
        TestClass testClass = new TestClass("minshigee", 21L);
        BaseResponse res = BaseResponse.builder()
                .body(testClass)
                .build();
        Assertions.assertNotNull(res.toEntity().getBody());
    }
}

class TestClass {
    String name;
    Long age;
    public TestClass(String _name, Long _age) {
        this.name = _name;
        this.age = _age;
    }
}