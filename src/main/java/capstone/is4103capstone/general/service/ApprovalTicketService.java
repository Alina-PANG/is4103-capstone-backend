package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.model.TicketDetailsModel;
import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.requestsMgmt.service.BJFService;
import capstone.is4103capstone.finance.requestsMgmt.service.ProjectService;
import capstone.is4103capstone.finance.requestsMgmt.service.TrainingService;
import capstone.is4103capstone.finance.requestsMgmt.service.TravelService;
import capstone.is4103capstone.general.model.ApprovalTicketModel;
import capstone.is4103capstone.general.model.GeneralRes;
import capstone.is4103capstone.general.model.Mail;
import capstone.is4103capstone.seat.model.EmployeeModel;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    @Autowired
    BJFService bjfService;
    @Autowired
    TravelService travelService;
    @Autowired
    TrainingService trainingService;
    @Autowired
    ProjectService projectService;

    @Value("${spring.mail.username}")
    private static String senderEmailAddr;
    @Value("${frontend.server.address}")
    private static String serverAddress;

    static EmployeeRepository employeeRepo;
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
            if(requester == null || receiver == null){
                logger.error("Requester or approver is null! Cannot create request for approval!");
                throw new Exception("Requester or approver is null! Cannot create request for approval!");
            }

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
            e.printStackTrace();
            logger.error("Internal error in creating ticket for item ["+requestedItem.getClass().getSimpleName()+"] id:"+requestedItem.getId());
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

    public static EmployeeModel getOpenTicketApproverByRequestedItem(String requestedItemId) throws Exception{
        List<ApprovalForRequest> list = approvalForRequestRepo.findTicketsByRequestedItemId(requestedItemId);
        List<ApprovalForRequest> models = new ArrayList<>();
        for (ApprovalForRequest a: list){
            if (a.getApprovalStatus().equals(ApprovalStatusEnum.PENDING))
                models.add(a);
        }
        if (models.size() > 1){
            logger.error("Internal error, multiple open tickets for item");
            return null;
        }
        if (models.size() == 0)
            return null;
        return new EmployeeModel(models.get(0).getApprover());
    }

    public static ApprovalTicketModel getLatestTicketByRequestedItem(String requestedItemId) throws Exception{
        List<ApprovalForRequest> list = approvalForRequestRepo.findTicketsByRequestedItemId(requestedItemId);

        ApprovalForRequest latest = list.get(0);
        for (ApprovalForRequest f:list){
            if (latest.getLastModifiedDateTime().after(latest.getLastModifiedDateTime())){
                latest = f;
            }
        }
        return new ApprovalTicketModel(latest);
//        List<ApprovalForRequest> models = new ArrayList<>();
//        for (ApprovalForRequest a: list){
//            if (a.getApprovalStatus().equals(ApprovalStatusEnum.PENDING))
//                models.add(a);
//        }
//        if (models.size() > 1){
//            logger.error("Internal error, multiple open tickets for item");
//            return null;
//        }
//        if (models.size() == 0)
//            return null;
//        return new ApprovalTicketModel(models.get(0));
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

    public static boolean checkCurrentUserHasApprovalFor(String requestedItemId) throws  Exception{
        EmployeeModel approverOfProject = getOpenTicketApproverByRequestedItem(requestedItemId);
        System.out.println(approverOfProject.getFullName()+" PROJECT "+requestedItemId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currEmployee = (Employee) auth.getPrincipal();
        return  approverOfProject.getId().equals(currEmployee.getId());
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


    public GeneralRes approveTicketAPI(String requestedItemId, String approverComment, boolean isApproved) throws Exception{
        List<ApprovalForRequest> relatedTickets = _approvalForRequestRepository.findTicketsByRequestedItemId(requestedItemId);

        if (relatedTickets.isEmpty())
            throw new EntityNotFoundException("No approval tickets associated with the requested item.");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentEmployee = (Employee) auth.getPrincipal();
        List<ApprovalForRequest> availableTickets = new ArrayList<>();
        for (ApprovalForRequest ticket: relatedTickets)
            if (ticket.getApprovalStatus().equals(ApprovalStatusEnum.PENDING))
                availableTickets.add(ticket);

        if (availableTickets.size() > 1)
            throw new Exception("[Internal Error] Multiple open tickets associated with the item");
        else if (availableTickets.isEmpty())
            throw new Exception("No open tickets associated with the item");

        ApprovalForRequest ticket = availableTickets.get(0);

        if (!currentEmployee.getId().equals(ticket.getApprover().getId()))
            throw new Exception("You don't have the right to approve the tickets;");


        ticket.setApprovalStatus(isApproved? ApprovalStatusEnum.APPROVED:ApprovalStatusEnum.REJECTED);
        ticket.setCommentByApprover(approverComment);
        _approvalForRequestRepository.save(ticket);
        try{
            mapApprovalType(ticket);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new Exception("Internal Error happened: cannot approve or reject ticket."+ex.getMessage());
        }


        return new GeneralRes("Successfully "+ticket.getApprovalStatus()+" "+ticket.getApprovalType()+" request.",false);
    }

    private void mapApprovalType(ApprovalForRequest ticket) throws Exception{
        switch (ticket.getApprovalType()){
            case CONTRACT:
                System.out.println("Already implemented in other ways");
                break;
            case BUDGETPLAN:
                System.out.println("Already implemented in other ways");
                break;
            case TRAVEL:
                travelService.travelPlanApproval(ticket);
                break;
            case TRAINING:
                trainingService.trainingPlanApproval(ticket);
                break;
            case PROJECT:
                projectService.projectApproval(ticket);
                break;
            case BJF:
                bjfService.bjfApproval(ticket);
                break;
            default:
                throw new Exception("Approval type not registered");
        }
    }

    public static void sendEmail(ApprovalForRequest ticket){
        try{
            String subject = "Request for Approval: ["+ticket.getApprovalType().name()+"] "+ ticket.getCode();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("requestor_username", ticket.getRequester().getUserName());
            map.put("requestor_email", ticket.getRequester().getEmail());
            map.put("requestor_name", ticket.getRequester().getFullName());
            map.put("requestor_type", ticket.getRequester().getEmployeeType());

            map.put("comment", ticket.getCommentByRequester());
            map.put("ticket_code", ticket.getCode());
            map.put("request_item_id", ticket.getRequestedItemId());
            map.put("created_datetime", ticket.getCreatedDateTime());

            map.put("url", serverAddress+"/ticket/id/"+ticket.getId());

            Mail mail = new Mail(senderEmailAddr == null? "is4103.capstone@gmail.com":senderEmailAddr, ticket.getApprover().getEmail(), subject, map);
            mailSenderService.sendEmail(mail, "approveBudgetMailTemplate");
        }catch (Exception e){
            logger.error("Sending email error Ticket ID"+ticket.getId());
        }

    }
}
