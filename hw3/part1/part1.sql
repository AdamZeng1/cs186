DROP VIEW IF EXISTS q1a, q1b, q1c, q1d, q2, q3, q4, q5, q6, q7;

-- Question 1a
CREATE VIEW q1a(id, amount)
AS
  SELECT cmte_id,transaction_amt from committee_contributions where transaction_amt > 5000
;

-- Question 1b
CREATE VIEW q1b(id, name, amount)
AS
  SELECT cmte_id,name,transaction_amt from committee_contributions where transaction_amt > 5000
;

-- Question 1c
CREATE VIEW q1c(id, name, avg_amount)
AS
  SELECT cmte_id,name,avg(transaction_amt) as avg_amount from committee_contributions where transaction_amt > 5000 group by cmte_id,name
;

-- Question 1d
CREATE VIEW q1d(id, name, avg_amount)
AS
  SELECT cmte_id,name,avg(transaction_amt) as avg_amount from committee_contributions where transaction_amt > 5000 group by cmte_id,name having avg(transaction_amt) > 10000
;

-- Question 2
CREATE VIEW q2(from_name, to_name)
AS
  SELECT com1.name, com2.name FROM intercommittee_transactions AS tr, committees AS com1, committees AS com2
  WHERE tr.cmte_id=com2.id AND com1.pty_affiliation='DEM' AND tr.other_id=com1.id AND com2.pty_affiliation='DEM'
  GROUP BY com1.name, com2.name ORDER BY COUNT(*) DESC FETCH FIRST 10 ROWS ONLY
;

-- Question 3
CREATE VIEW q3(name)
AS
  SELECT name FROM committees WHERE name NOT IN (
    SELECT DISTINCT coms.name FROM committee_contributions AS contr, committees AS coms, candidates as cand
    WHERE contr.cmte_id=coms.id AND contr.cand_id=cand.id AND cand.name='OBAMA, BARACK'
  );
;

-- Question 4.
CREATE VIEW q4 (name)
AS
  SELECT can.name FROM committee_contributions AS con, candidates AS can
  WHERE can.id=con.cand_id
  GROUP BY can.name, con.cand_id
  HAVING COUNT(DISTINCT con.cmte_id) > 0.01*(SELECT COUNT(*) FROM committees com)
;

-- Question 5
CREATE VIEW q5 (name, total_pac_donations) AS
  SELECT com.name, sums.amnt FROM committees AS com LEFT JOIN (
    SELECT cmt.id, SUM(ind.transaction_amt) as amnt
    FROM individual_contributions AS ind, committees as cmt
    WHERE cmt.id=ind.cmte_id AND ind.entity_tp='ORG' GROUP BY cmt.id)
  AS sums ON com.id=sums.id
;

-- Question 6
CREATE VIEW q6 (id) AS
  SELECT DISTINCT contr1.cand_id FROM committee_contributions AS contr1, committee_contributions AS contr2
  WHERE contr1.cand_id=contr2.cand_id AND contr1.entity_tp='PAC' AND contr2.entity_tp='CCM'
;

-- Question 7
CREATE VIEW q7 (cand_name1, cand_name2) AS
 -- SELECT DISTINCT cand1.name, cand2.name FROM committees as com, committee_contributions AS contr1, committee_contributions AS contr2, candidates AS cand1, candidates AS cand2
 -- WHERE cand1.id=contr1.cand_id AND cand2.id=contr2.cand_id AND contr1.cmte_id=contr2.cmte_id AND contr1.cmte_id=com.id AND com.state='RI'
  SELECT DISTINCT cand1.name, cand2.name
  FROM candidates AS cand1, candidates AS cand2, committee_contributions AS contr1, committee_contributions AS contr2
  WHERE cand1.id<>cand2.id AND cand1.id=contr1.cand_id AND cand2.id=contr2.cand_id AND contr1.cmte_id=contr2.cmte_id AND contr2.state='RI' and contr1.state='RI'
;
