package org.yjl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.modules.junit4.PowerMockRunner;
import org.yjl.domain.entity.Demo;
import org.yjl.service.IDemoService;

@RunWith(PowerMockRunner.class)
@ExtendWith(MockitoExtension.class)
public class MockitoTest {
    @Mock
    private Demo demo;
    @InjectMocks
    private IDemoService service;


}
