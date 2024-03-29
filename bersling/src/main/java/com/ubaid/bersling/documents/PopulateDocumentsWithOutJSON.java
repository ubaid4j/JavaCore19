package com.ubaid.bersling.documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.ubaid.bersling.database.DataSourceConfig;

/**
 * this class is responsible for populating table which is without json field
 * actually there are two tables connected with foreign key
 * @author UbaidurRehman
 *
 */
public class PopulateDocumentsWithOutJSON {

	//queries
	String q_document = "insert into documentsNoJSON(id, metadataId) values(?, ?)";
	String q_metaData = "insert into metaData(id, difficulty) values(?, ?)";
	
	public PopulateDocumentsWithOutJSON()
	{
		//getting data source
		DataSourceConfig config = new DataSourceConfig();
		//getting connection
		Connection con = config.getConnection();

		try
		{
			//prepared statements
			PreparedStatement st_document = con.prepareStatement(q_document);
			PreparedStatement st_metaData = con.prepareStatement(q_metaData);

			// loop which 10M in values into the prepared statement
			// and then executing the batch
			for(int i = 1; i <= 10000000; i++)
			{
				st_document.setString(1, Integer.toString(i));
				st_document.setString(2, Integer.toString(i));
				st_document.addBatch();
				if(i % 100000 == 0)
				{
					System.err.println("[INFO]: adding " + i + " entries in JSON document with out json table");
				}

			}
			System.err.println("[INFO]: Adding Batch in with out JSON document table");
			int[] added = st_document.executeBatch();
			System.err.println("[INFO]: Added: " + added);

			// loop which 10M in values into the prepared statement
			// and then executing the batch
			for(int i = 1; i <= 10000000; i++)
			{
				st_metaData.setString(1, Integer.toString(i));
				st_metaData.setString(2, Integer.toString(i));
				st_metaData.addBatch();
				if(i % 100000 == 0)
				{
					System.err.println("[INFO]: adding " + i + " entries in metadata table");
				}

			}
			System.err.println("[INFO]: Adding Batch in meta data table");
			added = st_document.executeBatch();
			System.err.println("[INFO]: Added: " + Arrays.asList(added));

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
