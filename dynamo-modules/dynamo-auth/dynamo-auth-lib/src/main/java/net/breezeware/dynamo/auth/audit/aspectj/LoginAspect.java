package net.breezeware.dynamo.auth.audit.aspectj;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.breezeware.dynamo.auth.audit.entity.LoginAudit;

/**
 * Aspect class to create an entry into the audit logs whenever a user logs in
 * to the application.
 */
@Aspect
@Component
public class LoginAspect {

    Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    // @Autowired
    // private AuthAuditService authAuditService;

    /**
     * Creates a entry into the audit logs when a user attempts to login to the
     * application. The 'loadByUsername()' method in implementations of
     * 'UserDetailsService' used by Spring Security are monitored for this purpose.
     * @param userDetails the Spring entity that contains the core user information
     */
    @AfterReturning(pointcut = "execution(* *.loadUserByUsername(..))", returning = "userDetails")
    public void doLoginCheck(UserDetails userDetails) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        LoginAudit loginAudit = new LoginAudit();

        loginAudit.setLoginDate(Calendar.getInstance());
        loginAudit.setClientDetails(request.getHeader("User-Agent"));
        loginAudit.setProtocol(request.getProtocol());
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        loginAudit.setIpAddress(ipAddress);

        loginAudit.setLoginEmail(userDetails.getUsername());
        loginAudit.setLoginRoles(getRolesCsv(userDetails.getAuthorities()));

        loginAudit.setCreatedDate(Calendar.getInstance());
        loginAudit.setModifiedDate(Calendar.getInstance());

        logger.info("loginAudit = " + loginAudit);

        // persist the login audit details
        // authAuditService.auditLogin(loginAudit);
    }

    private String getRolesCsv(Collection<? extends GrantedAuthority> authorities) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        int rolesCount = authorities.size();

        for (GrantedAuthority ga : authorities) {
            sb.append(ga.getAuthority().toString());
            index++;

            if (index < rolesCount) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }
}