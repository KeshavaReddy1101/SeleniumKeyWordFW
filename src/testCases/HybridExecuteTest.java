package testCases;

import java.util.Properties;
import operation.ReadObject;
import operation.UIOperation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import excelExportAndFileIO.ReadExcelFile;

public class HybridExecuteTest {

	public static void main(String[] args) throws Exception {

		UIOperation operation = new UIOperation();
		ReadObject object = new ReadObject();
		Properties allObjects = new Properties();
		allObjects = object.getObjectRepository();
		ReadExcelFile file = new ReadExcelFile();
		Sheet mysheet = file.readExcel(System.getProperty("user.dir"), "src/testCases/TestCase.xlsx", "Sheet1");
		int rowCount = mysheet.getLastRowNum();
		String testCase = null;
		String keyword;
		String Object;
		String ObjectType;
		String Value;
		for (int rowNumber = 1; rowNumber <= rowCount; rowNumber++) {
			Row row = mysheet.getRow(rowNumber);
			if (row.getCell(0) != null)
				testCase = row.getCell(0).toString();
			System.out.println("Testcase: " + testCase + " Started");

			if (row.getCell(1) == null)
				keyword = "";
			else
				keyword = row.getCell(1).toString();
			if (row.getCell(2) == null)
				Object = "";
			else
				Object = row.getCell(2).toString();
			if (row.getCell(3) == null)
				ObjectType = "";
			else
				ObjectType = row.getCell(3).toString();
			if (row.getCell(4) == null)
				Value = "";
			else
				Value = row.getCell(4).toString();
			System.out.println(keyword + "  --  " + Object + "  --  " + ObjectType + "  --  " + Value);
			operation.perform(allObjects, testCase, keyword, Object, ObjectType, Value);

		}

	}

}