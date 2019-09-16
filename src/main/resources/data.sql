Create Trigger `seq_no_generation`
BEFORE INSERT ON `currency` FOR EACH ROW
BEGIN
	SET NEW.seq_no = (SELECT COUNT(*)+1 FROM currency);
END ^;
INSERT into currency (id,code) values ("1324123412","USD"),("asdfasdfasd","CNY");