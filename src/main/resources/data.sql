DROP TRIGGER IF EXISTS seq_no_generation_action ^;
DROP TRIGGER IF EXISTS seq_no_generation_actuals_table ^;
DROP TRIGGER IF EXISTS seq_no_generation_approval_for_request ^;
DROP TRIGGER IF EXISTS seq_no_generation_bjf ^;
DROP TRIGGER IF EXISTS seq_no_generation_budget_category ^;
DROP TRIGGER IF EXISTS seq_no_generation_budget_sub1 ^;
DROP TRIGGER IF EXISTS seq_no_generation_budget_sub2 ^;
DROP TRIGGER IF EXISTS seq_no_generation_business_unit ^;
DROP TRIGGER IF EXISTS seq_no_generation_company_function ^;
DROP TRIGGER IF EXISTS seq_no_generation_contract ^;
DROP TRIGGER IF EXISTS seq_no_generation_contract_line ^;
DROP TRIGGER IF EXISTS seq_no_generation_cost_center ^;
DROP TRIGGER IF EXISTS seq_no_generation_country ^;
DROP TRIGGER IF EXISTS seq_no_generation_currency ^;
DROP TRIGGER IF EXISTS seq_no_generation_dispute ^;
DROP TRIGGER IF EXISTS seq_no_generation_employee ^;
DROP TRIGGER IF EXISTS seq_no_generation_fx_record ^;
DROP TRIGGER IF EXISTS seq_no_generation_invoice ^;
DROP TRIGGER IF EXISTS seq_no_generation_office ^;
DROP TRIGGER IF EXISTS seq_no_generation_outsourcing ^;
DROP TRIGGER IF EXISTS seq_no_generation_outsourcing_assessment ^;
DROP TRIGGER IF EXISTS seq_no_generation_outsourcing_assessment_line ^;
DROP TRIGGER IF EXISTS seq_no_generation_outsourcing_assessment_section ^;
DROP TRIGGER IF EXISTS seq_no_generation_plan ^;
DROP TRIGGER IF EXISTS seq_no_generation_plan_line_item ^;
DROP TRIGGER IF EXISTS seq_no_generation_project ^;
DROP TRIGGER IF EXISTS seq_no_generation_purchase_order ^;
DROP TRIGGER IF EXISTS seq_no_generation_region ^;
DROP TRIGGER IF EXISTS seq_no_generation_schedule ^;
DROP TRIGGER IF EXISTS seq_no_generation_seat ^;
DROP TRIGGER IF EXISTS seq_no_generation_seat_allocation ^;
DROP TRIGGER IF EXISTS seq_no_generation_seat_map ^;
DROP TRIGGER IF EXISTS seq_no_generation_security_group ^;
DROP TRIGGER IF EXISTS seq_no_generation_service ^;
DROP TRIGGER IF EXISTS seq_no_generation_spending_record ^;
DROP TRIGGER IF EXISTS seq_no_generation_statement_of_acct_line_item ^;
DROP TRIGGER IF EXISTS seq_no_generation_team ^;
DROP TRIGGER IF EXISTS seq_no_generation_training_form ^;
DROP TRIGGER IF EXISTS seq_no_generation_travel_form ^;
DROP TRIGGER IF EXISTS seq_no_generation_vendor ^;

Create Trigger `seq_no_generation_action`
BEFORE INSERT ON `action` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM action);
END ^;

Create Trigger `seq_no_generation_actuals_table`
BEFORE INSERT ON `actuals_table` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM actuals_table);
END ^;

Create Trigger `seq_no_generation_approval_for_request`
BEFORE INSERT ON `approval_for_request` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM approval_for_request);
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

Create Trigger `seq_no_generation_business_unit`
BEFORE INSERT ON `business_unit` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM business_unit);
END ^;

Create Trigger `seq_no_generation_company_function`
BEFORE INSERT ON `company_function` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM company_function);
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

Create Trigger `seq_no_generation_dispute`
BEFORE INSERT ON `dispute` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM dispute);
END ^;

Create Trigger `seq_no_generation_employee`
BEFORE INSERT ON `employee` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM employee);
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

Create Trigger `seq_no_generation_office`
BEFORE INSERT ON `office` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM office);
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

Create Trigger `seq_no_generation_security_group`
BEFORE INSERT ON `security_group` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM security_group);
END ^;

Create Trigger `seq_no_generation_service`
BEFORE INSERT ON `service` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM service);
END ^;

Create Trigger `seq_no_generation_spending_record`
BEFORE INSERT ON `spending_record` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM spending_record);
END ^;

Create Trigger `seq_no_generation_statement_of_acct_line_item`
BEFORE INSERT ON `statement_of_acct_line_item` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM statement_of_acct_line_item);
END ^;

Create Trigger `seq_no_generation_team`
BEFORE INSERT ON `team` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM team);
END ^;

Create Trigger `seq_no_generation_training_form`
BEFORE INSERT ON `training_form` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM training_form);
END ^;

Create Trigger `seq_no_generation_travel_form`
BEFORE INSERT ON `travel_form` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM travel_form);
END ^;

Create Trigger `seq_no_generation_vendor`
BEFORE INSERT ON `vendor` FOR EACH ROW
BEGIN
   SET NEW.seq_no = (SELECT COUNT(*)+1 FROM vendor);
END ^;

/* Create Spring Session tables */
/* drop old tables first */
DROP TABLE IF EXISTS SPRING_SESSION_ATTRIBUTES^;
DROP TABLE IF EXISTS SPRING_SESSION^;

/* then create new ones */
CREATE TABLE IF NOT EXISTS SPRING_SESSION
(
    PRIMARY_ID            CHAR(36) NOT NULL,
    SESSION_ID            CHAR(36) NOT NULL,
    CREATION_TIME         BIGINT   NOT NULL,
    LAST_ACCESS_TIME      BIGINT   NOT NULL,
    MAX_INACTIVE_INTERVAL INT      NOT NULL,
    EXPIRY_TIME           BIGINT   NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE = InnoDB
  ROW_FORMAT = DYNAMIC^;

/* then recreate the index */
CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID) ^;
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME) ^;
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME) ^;

CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHAR(36)     NOT NULL,
    ATTRIBUTE_NAME     VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES    BLOB         NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
) ENGINE = InnoDB
  ROW_FORMAT = DYNAMIC^;