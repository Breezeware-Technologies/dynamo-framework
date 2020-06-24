package net.breezeware.dynamo.organization.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import net.breezeware.dynamo.organization.TestApplication;
import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.User;
import net.breezeware.dynamo.organization.service.api.OrganizationService;
import net.breezeware.dynamo.util.exeption.DynamoDataAccessException;

@ContextConfiguration(classes = TestApplication.class)
public class OrganizationServiceTest extends AbstractTestNGSpringContextTests {

    Logger logger = LoggerFactory.getLogger(OrganizationServiceTest.class);

    @Autowired
    OrganizationService organizationService;

    @Test
    public void findAllUsersTest() {
        logger.info("*************** Entering createPerson1Test() ***************");

        List<User> allUsers = organizationService.findAllUsers();
        logger.info("# of users = {}", allUsers.size());

        Assert.assertNotNull(allUsers);
        logger.info("*************** Leaving findAllUsersTest() ***************");
    }

    @Test(dependsOnMethods = { "findAllUsersTest" })
    public void createUserTest() {
        logger.info("*************** Entering createUserTest() ***************");

        User user = new User();
        user.setCreatedDate(Calendar.getInstance());
        user.setEmail("cmkarthik@gmail.com");
        user.setFirstName("Karthik");
        user.setLastName("Muthukumaraswamy");
        user.setMiddleInitial("");
        user.setModifiedDate(Calendar.getInstance());

        Organization org = organizationService.findOrganizationById(1);
        user.setOrganization(org);

        user.setPassword("karthik");
        user.setPhoneNumber("859-489-4551");
        user.setStatus(User.STATUS_ACTIVE);

        List<Long> groupIdList = new ArrayList<Long>();
        groupIdList.add(Long.valueOf(1));
        user.setUserGroupId(groupIdList);

        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(Long.valueOf(1));
        user.setUserRoleId(roleIdList);

        user.setUserTimeZoneId(User.userTimeZoneOptions().get(5));

        try {
            long originalUserId = user.getId();
            user = organizationService.createUser(user);
            long updatedUserId = user.getId();

            Assert.assertNotEquals(originalUserId, updatedUserId);
        } catch (DynamoDataAccessException e) {
            logger.error("Exception occured: {}", e);
        }

        logger.info("*************** Entering createUserTest() ***************");
    }

    @Test
    public void retrieveUsersByRoleTest() {
        logger.info("*************** Entering retrieveUsersByRoleTest() ***************");

        long orgId = 2;
        List<String> roles = new ArrayList<String>();
        roles.add("CLINICIAN");
        roles.add("ORGANIZATION_USER");
        roles.add("PATIENT");

        List<User> allUsers = organizationService.retrieveUsersByRole(orgId, roles);
        logger.info("# of users = {}", allUsers.size());

        Assert.assertNotNull(allUsers);
        logger.info("*************** Leaving retrieveUsersByRoleTest() ***************");
    }
}