package com.vz.dam;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vz.dam.mongodb.repository.AssetWrapperRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AssetManagerConfiguration.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class AssetManagerConfigurationTest {
  
  @Autowired protected AssetWrapperRepository assetsDetailsRepository;

  @Autowired private WebApplicationContext context;
  protected MockMvc mockMvc;
  
  protected Pageable pageable = new PageRequest(0, 10);

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }

  @After
  public void tearDown() throws Exception {
  }
}
