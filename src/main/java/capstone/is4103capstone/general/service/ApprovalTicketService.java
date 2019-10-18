package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.model.TicketDetailsModel;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalTicketService {
    private static final Logger logger = LoggerFactory.getLogger(ApprovalTicketService.class);

    @Autowired
    EmployeeRepository _employeeRepository;
    @Autowired
    ApprovalForRequestRepository _approvalForRequestRepository;
    @Autowired
    private MailSenderService _mailSenderService;

    @Value("${spring.mail.username}")
    private static String senderEmailAddr;

    static  EmployeeRepository employeeRepo;
    static ApprovalForRequestRepository approvalForRequestRepo;
    static MailSenderService mailSenderService;

    @Autowired
    public void setEmployeeRepo(EmployeeRepository repo){
        ApprovalTicketService.employeeRepo = repo;
    }
    @Autowired
    public void setApprovalRepo(ApprovalForRequestRepository repo){
        ApprovalTicketService.approvalForRequestRepo = repo;
    }
    @Autowired
    public void setMailSenderService(MailSenderService service){
        ApprovalTicketService.mailSenderService = service;
    }

    private final SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public JSONObject getTicketById(String ticketId, String requesterUsername){
        JSONObject res = new JSONObject();
        try{

            Optional<ApprovalForRequest> ticketOp = _approvalForRequestRepository.findById(ticketId);
            if (!ticketOp.isPresent())
                throw new Exception("Ticket with id ["+ticketId+"] not found.");

            ApprovalForRequest ticket = ticketOp.get();
            TicketDetailsModel model = new TicketDetailsModel(ticketId,ticket.getRequester().getUserName(),ticket.getRequester().getEmail(),ticket.getCommentByRequester(),ticket.getCommentByApprover(),ticket.getApprover().getUserName(),ticket.getApprover().getEmail());                    ;
            model.setCreatedDateTime(datetimeFormatter.format(ticket.getCreatedDateTime()));
            model.setReviewDateTime(datetimeFormatter.format(ticket.getLastModifiedDateTime()));
            model.setApprovalStatus(ticket.getApprovalStatus().name());
            JSONObject requestItem = new JSONObject();
            requestItem.put("id",ticket.getRequestedItemId());
            requestItem.put("itemType",ticket.getApprovalType());//need to change to the entity object name; for generalization purpose
            model.setRequestedItem(requestItem);
            res.put("ticketDetails",new JSONObject(model));
            res.put("hasError",false);
            res.put("message","Retrieved ticket.");
        }catch (Exception e){
            res.put("hasError",true);
            res.put("message",e.getMessage());
        }
        return res;

    }



    public static boolean createTicketAndSendEmail(String requesterUsername, String approverUsername, DBEntityTemplate requestedItem, String content,ApprovalTypeEnum approvalType){
        try{
            Employee requester = employeeRepo.findEmployeeByUserName(requesterUsername);
            Employee approver = employeeRepo.findEmployeeByUserName(approverUsername);
            if (requester == null || approver == null){
                throw new Exception("Usernames not correct.");
            }
            return createTicketAndSendEmail(requester,approver,requestedItem,content,approvalType);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }


    }

    public static boolean createTicketAndSendEmail(Employee requester, Employee receiver, DBEntityTemplate requestedItem, String content,ApprovalTypeEnum approvalType){
        try {
            ApprovalForRequest ticket = new ApprovalForRequest();
            ticket.setCreatedBy(requester.getUserName());
            ticket.setRequestedItemId(requestedItem.getId());
            ticket.setApprovalType(approvalType);
            ticket.setRequester(requester);
            ticket.setApprover(receiver);
            ticket.setApprovalStatus(ApprovalStatusEnum.PENDING);
            ticket.setCommentByRequester(content);
            ticket = approvalForRequestRepo.save(ticket);
            String code = EntityCodeHPGeneration.getCode(approvalForRequestRepo, ticket);
            ticket.setCode(code);
            ticket.setObjectName(code);
            approvalForRequestRepo.save(ticket);
            requester.getMyRequestTickets().add(ticket.getId());
            receiver.getMyApprovals().add(ticket.getId());
            sendEmail(ticket);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean approveTicket(String ticketId, String comment){
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepo.findById(ticketId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.APPROVED, ticket);
    }

    public static List<ApprovalTicketModel> getAllNonPendingTicketsByRequestItem(DBEntityTemplate requestedItem){
        List<ApprovalForRequest> list = approvalForRequestRepo.findTicketsByRequestedItemId(requestedItem.getId());
        List<ApprovalTicketModel> models = new ArrayList<>();
        for (ApprovalForRequest a: list){
            if (a.getApprovalStatus().equals(ApprovalStatusEnum.PENDING))
                continue;
            models.add(new ApprovalTicketModel(a.getId(),a.getApprover().getUserName(),a.getCommentByApprover(),a.getCreatedDateTime(),a.getLastModifiedDateTime(),a.getApprovalStatus()));
        }
        return models;
    }

    public static List<ApprovalTicketModel> getAllTicketsByRequestedItem(DBEntityTemplate requestedItem){
        List<ApprovalForRequest> list = approvalForRequestRepo.findTicketsByRequestedItemId(requestedItem.getId());
        List<ApprovalTicketModel> models = new ArrayList<>();
        for (ApprovalForRequest a: list){
            models.add(new ApprovalTicketModel(a.getId(),a.getApprover().getUserName(),a.getCommentByApprover(),a.getCreatedDateTime(),a.getLastModifiedDateTime(),a.getApprovalStatus()));
        }
        return models;
    }

    public static boolean approveTicketByEntity(DBEntityTemplate requestedItem, String comment, String approverUsername){
        String approverId = employeeRepo.findEmployeeByUserName(approverUsername).getId();
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepo.findPendingTicketByEntityIdAndApproverId(requestedItem.getId(),approverId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.APPROVED, ticket);
    }

    public static boolean rejectTicketByEntity(DBEntityTemplate requestedItem, String comment, String approverUsername){
        String approverId = employeeRepo.findEmployeeByUserName(approverUsername).getId();
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepo.findPendingTicketByEntityIdAndApproverId(requestedItem.getId(),approverId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.REJECTED, ticket);
    }


    public static boolean rejectTicket(String ticketId, String comment){
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepo.findById(ticketId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.REJECTED, ticket);
    }

    private static boolean modifyRequest(ApprovalStatusEnum result, ApprovalForRequest ticket){
        try{

            ticket.setApprovalStatus(result);
            ticket.setLastModifiedBy(ticket.getRequester().getUserName());
            approvalForRequestRepo.save(ticket);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }


    private static void approveRequest(){

    }



    public static void sendEmail(ApprovalForRequest ticket){
        String subject = "Request for Approval: "+ ticket.getCode();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("requestor_username", ticket.getRequester().getUserName());
        map.put("requestor_email", ticket.getRequester().getEmail());
        map.put("requestor_name", ticket.getRequester().getFullName());
        map.put("requestor_type", ticket.getRequester().getEmployeeType());

        map.put("comment", ticket.getCommentByRequester());
        map.put("ticket_code", ticket.getCode());
        map.put("request_item_id", ticket.getRequestedItemId());
        map.put("created_datetime", ticket.getCreatedDateTime());

        map.put("url", "http://localhost:3000/ticket/id/"+ticket.getId());

        Mail mail = new Mail(senderEmailAddr == null? "is4103.capstone@gmail.com":senderEmailAddr, ticket.getApprover().getEmail(), subject, map);
        mailSenderService.sendEmail(mail, "approveBudgetMailTemplate");
    }
}
