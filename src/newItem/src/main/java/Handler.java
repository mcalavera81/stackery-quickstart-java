import java.util.HashMap;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

public class Handler {
    public Object handler(Object event) {
        String tableName = System.getenv("TABLE_NAME"); // get the table name from the automatically populated environment variables

        HashMap<String,AttributeValue> item_values =
            new HashMap<String,AttributeValue>();

        item_values.put("id", new AttributeValue("2")); // modify with each invoke so the id does not repeat
        item_values.put("content", new AttributeValue("Another content")); // modify content here

        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();

        try {
            // Write a new item to the Items table
            ddb.putItem(tableName, item_values);
            System.out.format("Adding item \"%s\" to table  \"%s\".\n", item_values, tableName);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", tableName);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return "Item added to table, done!";
    }
}