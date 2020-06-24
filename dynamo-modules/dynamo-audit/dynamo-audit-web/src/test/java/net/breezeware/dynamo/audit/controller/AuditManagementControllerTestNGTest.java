package net.breezeware.dynamo.audit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.breezeware.dynamo.audit.IntegrationTestApplication;
import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

@SpringBootTest(classes = { IntegrationTestApplication.class })
public class AuditManagementControllerTestNGTest extends AbstractTestNGSpringContextTests {

	Logger logger = LoggerFactory.getLogger(AuditManagementControllerTestNGTest.class);

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	MockHttpSession session;

	@BeforeMethod
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void listDeviceTest() throws Exception {
		try {
			CurrentUserDto currentUserDto = new CurrentUserDto();
			currentUserDto.setEmail("karthik@breezeware.net");
			currentUserDto.setFullName("Karthik Muthukumaraswamy");
			currentUserDto.setOrganizationId("ORG101");
			currentUserDto.setRolesCsv("ROLE_SYSTEM_ADMIN");
			currentUserDto.setUserTimeZoneId("Asia/Kolkata");

			session.setAttribute("currentUser", currentUserDto);

			mockMvc.perform(get("/audit/logs").session(session)).andExpect(status().isOk())
					.andExpect(view().name("audit/list-audit-logs")).andExpect(model().attributeExists("pagedItems"))
					.andExpect(model().attributeExists("activeNav")).andReturn().getRequest().getSession();
		} catch (Exception ex) {
			Assert.fail("Failed - " + ex.getMessage());
		}
	}
}