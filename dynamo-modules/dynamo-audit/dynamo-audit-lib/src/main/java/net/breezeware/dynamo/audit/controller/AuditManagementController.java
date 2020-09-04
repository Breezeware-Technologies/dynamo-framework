package net.breezeware.dynamo.audit.controller;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Predicate;

import net.breezeware.dynamo.audit.entity.AuditItem;
import net.breezeware.dynamo.audit.service.api.AuditService;
import net.breezeware.dynamo.util.usermgmt.CurrentUserDto;

/**
 * Controller methods for audit log management.
 */
@Controller
@RequestMapping(value = "/audit/*")
@PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN', 'ORGANIZATION_ADMIN')")
public class AuditManagementController {

    Logger logger = LoggerFactory.getLogger(AuditManagementController.class);

    @Autowired
    AuditService auditService;

    /**
     * Retrieves audit logs across all organizations. Accessible only by
     * SYSTEM_ADMIN role.
     * @param model      the holder for Model attributes
     * @param predicate  the interface for Boolean typed expressions. Supports
     *                   binding of HTTP parameters to QueryDSL predicate
     * @param pageable   the interface for pagination information
     * @param parameters the holder for HTTP parameters in request
     * @param session    the HTTPSession object
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN')")
    public String listAuditLogs(Model model, @QuerydslPredicate(root = AuditItem.class) Predicate predicate,
            @PageableDefault(sort = { "id" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        // logger.info("Predicate = {}", predicate.);

        CurrentUserDto currentUserDto = (CurrentUserDto) session.getAttribute("currentUser");

        logger.info("Entering listAuditLogs()");
        logger.info("# of params = " + parameters.size());
        logger.info("user time zone = " + session.getAttribute("currentUser"));
        logger.info("zone ID = " + currentUserDto.getUserTimeZoneId());

        Page<AuditItem> pagedItems = auditService.findAllAuditItems(predicate, pageable);
        logger.info("# of items fetched = {}", pagedItems.getNumberOfElements());

        pagedItems = updateDateToClientTimeZone(pagedItems, currentUserDto.getUserTimeZoneId());

        model.addAttribute("pagedItems", pagedItems);
        model.addAttribute("activeNav", "audit");
        logger.info("Leaving listAuditLogs()");
        return "dynamo-audit/list-audit-logs";
    }

    /**
     * Retrieves audit logs for a single organization.
     * @param model      the holder for Model attributes
     * @param predicate  the interface for Boolean typed expressions. Supports
     *                   binding of HTTP parameters to QueryDSL predicate
     * @param pageable   the interface for pagination information
     * @param parameters the holder for HTTP parameters in request
     * @param session    the HTTPSession object
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/orgLogs", method = RequestMethod.GET)
    public String listOrganizationAuditLogs(Model model, @QuerydslPredicate(root = AuditItem.class) Predicate predicate,
            @PageableDefault(sort = { "id" }, page = 0, size = 12) Pageable pageable,
            @RequestParam MultiValueMap<String, String> parameters, HttpSession session) {

        // logger.info("Predicate = {}", predicate.);
        CurrentUserDto currentUserDto = (CurrentUserDto) session.getAttribute("currentUser");

        logger.info("Entering listAuditLogs()");
        logger.info("# of params = " + parameters.size());
        logger.info("zone ID = " + currentUserDto.getUserTimeZoneId());

        long organizationId = Long.valueOf(currentUserDto.getOrganizationId());

        Page<AuditItem> pagedItems = auditService.findAuditItemsForOrganization(organizationId, predicate, pageable);
        logger.info("# of items fetched = {}", pagedItems.getNumberOfElements());

        pagedItems = updateDateToClientTimeZone(pagedItems, currentUserDto.getUserTimeZoneId());

        model.addAttribute("pagedItems", pagedItems);
        model.addAttribute("activeNav", "audit");

        // model.addAttribute("locale", Locale.US);

        logger.info("Leaving listAuditLogs()");
        return "dynamo-audit/list-audit-logs";
    }

    /**
     * Gets the id of the audit log and re-directs to the view-audit log page.
     * @param id    the ID of the AuditItem that is requested
     * @param model the holder for Model attributes
     * @return a string to identify the Thymeleaf template
     */
    @RequestMapping(value = "/logs", method = RequestMethod.GET, params = { "id" })
    public String viewAuditLog(@RequestParam("id") long id, Model model) {

        logger.info("Entering viewAuditLog()");
        AuditItem auditItem = auditService.getItemById(id);

        model.addAttribute("auditItem", auditItem);
        model.addAttribute("activeNav", "audit");
        logger.info("Leaving viewAuditLog()");
        return "dynamo-audit/view-audit-log";
    }

    private Page<AuditItem> updateDateToClientTimeZone(Page<AuditItem> pagedItems, String zoneId) {

        DateTimeFormatter formatter = null;
        if (zoneId != null && zoneId.length() > 0) {
            // format dates according to user
            formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.of(zoneId));
        } else {
            formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(ZoneId.of("UTC"));
        }

        for (AuditItem item : pagedItems) {
            item.setAuditDateString(formatter.format(item.getAuditDate()));
        }

        return pagedItems;
    }
}