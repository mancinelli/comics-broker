DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `SPLIT_STRING`(delim VARCHAR(12), str VARCHAR(255), pos INT) RETURNS varchar(255) CHARSET utf8
    DETERMINISTIC
BEGIN
	RETURN
		REPLACE(
			SUBSTRING(
				SUBSTRING_INDEX(str, delim, pos),
				LENGTH(SUBSTRING_INDEX(str, delim, pos-1)) + 1
			),
			delim, ''
		);
END$$
DELIMITER ;
