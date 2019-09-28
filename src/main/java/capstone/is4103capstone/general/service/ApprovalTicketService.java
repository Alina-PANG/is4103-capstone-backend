package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.repository.EmployeeRepository;
import capstone.is4103capstone.configuration.DBEntityTemplate;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.Employee;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.finance.admin.EntityCodeHPGeneration;
import capstone.is4103capstone.finance.budget.service.BudgetService;
import capstone.is4103capstone.util.enums.ApprovalStatusEnum;
import capstone.is4103capstone.util.enums.ApprovalTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApprovalTicketService {
    private static final Logger logger = LoggerFactory.getLogger(ApprovalTicketService.class);

    @Autowired
    static  EmployeeRepository employeeRepository;
    @Autowired
    static ApprovalForRequestRepository approvalForRequestRepository;

    public static boolean createTicketAndSendEmail(String requesterUsername, String approverUsername, DBEntityTemplate requestedItem, String content){
        return false;
    }

    public static boolean createTicketAndSendEmail(Employee requester, Employee receiver, DBEntityTemplate requestedItem, String content){
        try {
            ApprovalForRequest ticket = new ApprovalForRequest();
            ticket.setCreatedBy(requester.getUserName());
            ticket.setRequestedItemCode(requestedItem.getCode());
            ticket.setApprovalType(ApprovalTypeEnum.BUDGETPLAN);
            ticket.setRequester(requester);
            ticket.setApprover(receiver);
            ticket.setApprovalStatus(ApprovalStatusEnum.PENDING);
            ticket.setCommentByRequester(content);
            ticket = approvalForRequestRepository.save(ticket);
            String code = EntityCodeHPGeneration.getCode(approvalForRequestRepository, ticket);
            ticket.setCode(code);
            ticket.setObjectName(code);
            approvalForRequestRepository.save(ticket);
            requester.getMyRequestTickets().add(ticket.getId());
            receiver.getMyApprovals().add(ticket.getId());
            sendEmail(ticket);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean approveTicket(String ticketId, String comment){
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepository.findById(ticketId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.APPROVED, ticket);
    }

    public static boolean approveTicket(DBEntityTemplate requestedItem, String comment){
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepository.findById(ticketId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.APPROVED, ticket);
    }


    public static boolean rejectTicket(String ticketId, String comment){
        Optional<ApprovalForRequest> ticketOp = approvalForRequestRepository.findById(ticketId);
        if (!ticketOp.isPresent()) return false;

        ApprovalForRequest ticket = ticketOp.get();
        ticket.setCommentByApprover(comment);
        return modifyRequest(ApprovalStatusEnum.REJECTED, ticket);
    }

    private static boolean modifyRequest(ApprovalStatusEnum result, ApprovalForRequest ticket){
        try{

            ticket.setApprovalStatus(result);
            ticket.setLastModifiedBy(ticket.getRequester().getUserName());
            approvalForRequestRepository.save(ticket);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
    }


    private static boolean approveRequest(){

    }

    private static void sendEmail(ApprovalForRequest ticket){

    }

}
