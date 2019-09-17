Create Trigger `seq_no_generation_approval_for_request`
BEFORE INSERT ON `approval_for_request` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM approval_for_request);
END ^;

Create Trigger `seq_no_generation_company_function`
BEFORE INSERT ON `company_function` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM company_function);
END ^;

Create Trigger `seq_no_generation_cost_center`
BEFORE INSERT ON `cost_center` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM cost_center);
END ^;

Create Trigger `seq_no_generation_country`
BEFORE INSERT ON `country` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM country);
END ^;

Create Trigger `seq_no_generation_currency`
BEFORE INSERT ON `currency` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM currency);
END ^;

Create Trigger `seq_no_generation_demo_entity`
BEFORE INSERT ON `demo_entity` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM demo_entity);
END ^;

Create Trigger `seq_no_generation_employee`
BEFORE INSERT ON `employee` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM employee);
END ^;

Create Trigger `seq_no_generation_office`
BEFORE INSERT ON `office` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM office);
END ^;

Create Trigger `seq_no_generation_region`
BEFORE INSERT ON `region` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM region);
END ^;

Create Trigger `seq_no_generation_schedule`
BEFORE INSERT ON `schedule` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM schedule);
END ^;

Create Trigger `seq_no_generation_security_group`
BEFORE INSERT ON `security_group` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM security_group);
END ^;

Create Trigger `seq_no_generation_team`
BEFORE INSERT ON `team` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM team);
END ^;

Create Trigger `seq_no_generation_actuals_table`
BEFORE INSERT ON `actuals_table` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM actuals_table);
END ^;

Create Trigger `seq_no_generation_bjf`
BEFORE INSERT ON `bjf` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM bjf);
END ^;

Create Trigger `seq_no_generation_budget_category`
BEFORE INSERT ON `budget_category` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM budget_category);
END ^;

Create Trigger `seq_no_generation_budget_sub1`
BEFORE INSERT ON `budget_sub1` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM budget_sub1);
END ^;

Create Trigger `seq_no_generation_budget_sub2`
BEFORE INSERT ON `budget_sub2` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM budget_sub2);
END ^;

Create Trigger `seq_no_generation_fx_record`
BEFORE INSERT ON `fx_record` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM fx_record);
END ^;

Create Trigger `seq_no_generation_invoice`
BEFORE INSERT ON `invoice` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM invoice);
END ^;

Create Trigger `seq_no_generation_merchandise`
BEFORE INSERT ON `merchandise` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM merchandise);
END ^;

Create Trigger `seq_no_generation_plan`
BEFORE INSERT ON `plan` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM plan);
END ^;

Create Trigger `seq_no_generation_plan_line_item`
BEFORE INSERT ON `plan_line_item` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM plan_line_item);
END ^;

Create Trigger `seq_no_generation_project`
BEFORE INSERT ON `project` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM project);
END ^;

Create Trigger `seq_no_generation_purchase_order`
BEFORE INSERT ON `purchase_order` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM purchase_order);
END ^;

Create Trigger `seq_no_generation_purchase_order_line_item`
BEFORE INSERT ON `purchase_order_line_item` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM purchase_order_line_item);
END ^;

Create Trigger `seq_no_generation_statement_of_acct_line_item`
BEFORE INSERT ON `statement_of_acct_line_item` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM statement_of_acct_line_item);
END ^;

Create Trigger `seq_no_generation_seat`
BEFORE INSERT ON `seat` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM seat);
END ^;

Create Trigger `seq_no_generation_seat_allocation`
BEFORE INSERT ON `seat_allocation` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM seat_allocation);
END ^;

Create Trigger `seq_no_generation_seat_map`
BEFORE INSERT ON `seat_map` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM seat_map);
END ^;

Create Trigger `seq_no_generation_action`
BEFORE INSERT ON `action` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM action);
END ^;

Create Trigger `seq_no_generation_contract`
BEFORE INSERT ON `contract` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM contract);
END ^;

Create Trigger `seq_no_generation_contract_line`
BEFORE INSERT ON `contract_line` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM contract_line);
END ^;

Create Trigger `seq_no_generation_dispute`
BEFORE INSERT ON `dispute` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM dispute);
END ^;

Create Trigger `seq_no_generation_outsourcing`
BEFORE INSERT ON `outsourcing` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM outsourcing);
END ^;

Create Trigger `seq_no_generation_outsourcing_assessment`
BEFORE INSERT ON `outsourcing_assessment` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM outsourcing_assessment);
END ^;

Create Trigger `seq_no_generation_outsourcing_assessment_line`
BEFORE INSERT ON `outsourcing_assessment_line` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM outsourcing_assessment_line);
END ^;

Create Trigger `seq_no_generation_outsourcing_assessment_section`
BEFORE INSERT ON `outsourcing_assessment_section` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM outsourcing_assessment_section);
END ^;

Create Trigger `seq_no_generation_vendor`
BEFORE INSERT ON `vendor` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM vendor);
END ^;
