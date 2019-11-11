package capstone.is4103capstone.general.service;

import capstone.is4103capstone.admin.service.EmployeeService;
import capstone.is4103capstone.entities.ApprovalForRequest;
import capstone.is4103capstone.entities.finance.BJF;
import capstone.is4103capstone.finance.Repository.ApprovalForRequestRepository;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.BJFModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.ProjectModel;
import capstone.is4103capstone.finance.requestsMgmt.model.dto.RequestFormModel;
import capstone.is4103capstone.finance.requestsMgmt.service.BJFService;
import capstone.is4103capstone.finance.requestsMgmt.service.ProjectService;
import capstone.is4103capstone.finance.requestsMgmt.service.TrainingService;
import capstone.is4103capstone.finance.requestsMgmt.service.TravelService;
import capstone.is4103capstone.general.model.ChatbotViewTicketModel;
import capstone.is4103capstone.supplychain.model.PendingApprovalTicketModel;
import capstone.is4103capstone.util.enums.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatbotSpecialService {

    @Autowired
    ApprovalForRequestRepository ticketRepo;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EntityMappingService entityMappingService;
    @Autowired
    BJFService bjfService;
    @Autowired
    TravelService travelService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TrainingService trainService;

    public List<ChatbotViewTicketModel> getFormTicketsAndDetails(String approvalId) throws Exception {
        if (approvalId == null){
            approvalId = employeeService.getCurrentLoginEmployee().getId();
        }else{
            approvalId = employeeService.validateUser(approvalId).getId();
        }
        // only support             "PROJECT","BJF","TRAVEL","TRAINING"
        List<ChatbotViewTicketModel> list = new ArrayList<>();
        List<ApprovalForRequest> pendingTickets = ticketRepo.findPendingTicketsByApproverId(approvalId);

        for (ApprovalForRequest ticket: pendingTickets){
            RequestFormModel model;
            switch (ticket.getApprovalType()){
                case BJF:
                    model = bjfService.getBJFDetails(ticket.getRequestedItemId());
                    break;
                case PROJECT:
                    model = projectService.getProjectDetails(ticket.getRequestedItemId());
                    break;
                case TRAINING:
                    model = trainService.getTrainingPlanDetails(ticket.getRequestedItemId());
                    break;
                case TRAVEL:
                    model = travelService.getTravelPlanDetails(ticket.getRequestedItemId());
                    break;
                default:
                    continue;
            }

            ChatbotViewTicketModel m = new ChatbotViewTicketModel(model,ticket.getRequester(),ticket);
            list.add(m);
        }

        return list;
    }
}
