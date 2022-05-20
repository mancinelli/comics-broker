INSERT INTO `comicdb`.`broker_comics`
(`comicbook_id`,
`file`,
`title`,
`series`,
`number`,
`volume`,
`summary`,
`notes`,
`year`,
`month`,
`publisher`,
`web`,
`pagecount`,
`characters`,
`added`,
`tags`,
`seriescomplete`,
`custom_values_store`,
`comicvine_issue`,
`comicvine_volume`)
SELECT  
	ExtractValue(data, '/ComicBook/@Id') AS Id,  
	ExtractValue(data, '/ComicBook/@File') AS File,  
	ExtractValue(data, '/ComicBook/Title') AS Title,  
	ExtractValue(data, '/ComicBook/Series') AS Series,  
    ExtractValue(data, '/ComicBook/Number') AS Number,
    ExtractValue(data, '/ComicBook/Volume') AS Volume,
    ExtractValue(data, '/ComicBook/Summary') AS Summary,
    ExtractValue(data, '/ComicBook/Notes') AS Notes,
    ExtractValue(data, '/ComicBook/Year') AS Year,
    ExtractValue(data, '/ComicBook/Month') AS Month,
    ExtractValue(data, '/ComicBook/Publisher') AS Publisher,
    ExtractValue(data, '/ComicBook/Web') AS Web,
    ExtractValue(data, '/ComicBook/PageCount') AS PageCount,
    ExtractValue(data, '/ComicBook/Characters') AS Characters,
    LEFT(ExtractValue(data, '/ComicBook/Added'), 19),
    ExtractValue(data, '/ComicBook/Tags') AS Tags,
    ExtractValue(data, '/ComicBook/SeriesComplete') AS SeriesComplete,
    ExtractValue(data, '/ComicBook/CustomValuesStore') AS Custom_Values_Store,
    CASE
		WHEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 1), 1) = 'comicvine_issue' THEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 1), 2)
		WHEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 2), 1) = 'comicvine_issue' THEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 2), 2)
		WHEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 3), 1) = 'comicvine_issue' THEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 3), 2)
	END AS comicvine_issue,
    CASE
		WHEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 1), 1) = 'comicvine_volume' THEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 1), 2)
		WHEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 2), 1) = 'comicvine_volume' THEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 2), 2)
		WHEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 3), 1) = 'comicvine_volume' THEN SPLIT_STRING('=', SPLIT_STRING(',', ExtractValue(data, '/ComicBook/CustomValuesStore'), 3), 2)
	END AS comicvine_issue
    
FROM comicdb.comics -- where data not like '%customvaluesstore%'
ORDER BY Series, Volume, Number