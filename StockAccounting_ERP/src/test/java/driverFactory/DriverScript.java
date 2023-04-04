package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLiabrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	
	public static WebDriver driver;
	String inputpath="E:\\Software Testing\\Project_By_Ranga_Reddy\\Projects\\StockAccounting_ERP\\FileInput\\DataEngine.xlsx";
	String outputpath="E:\\Software Testing\\Project_By_Ranga_Reddy\\Projects\\StockAccounting_ERP\\FileOutput\\HybridResults.xlsx";
	ExtentReports reports;
	ExtentTest test;
public void startTest() throws Throwable {
	String ModuleStatus="";
	//create instance/object of the ExcelFileutil Class
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	String TCModule="";
	//iterate all rows in master test cases sheet
	for(int i=1;i<=xl.rowCount("MasterTestCases");i++) {
		if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y")) {
			//store corresponding sheet into TCModule variable
			TCModule=xl.getCellData("MasterTestCases", i, 1);
			//iterate all rows in TCModule sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++) {
				test=reports.startTest(TCModule);
				test.assignAuthor("Anshuman");
				test.assignCategory("Functional");
				String Description = xl.getCellData(TCModule,j,0);
				String Object_Type = xl.getCellData(TCModule,j,1);
				String Locator_Type = xl.getCellData(TCModule,j,2);
				String Locator_Value = xl.getCellData(TCModule,j,3);
				String TestData = xl.getCellData(TCModule,j,4);
				reports = new ExtentReports("./ExtentReports/"+Description+" "+TCModule+FunctionLiabrary.captureDate()+".html");
					try {
						if(Object_Type.equalsIgnoreCase("startBrowser")) {
							driver= FunctionLiabrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("openApplication")){
							FunctionLiabrary.openApplication(driver);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("waitForElement")){
							FunctionLiabrary.waitForElement(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("typeAction")) {
							FunctionLiabrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("clickAction")) {
							FunctionLiabrary.clickAction(driver, Locator_Type, Locator_Value, TestData);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("ValidateTitle")) {
							FunctionLiabrary.validateTitle(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("closeBrowser")) {
							FunctionLiabrary.closeBrowser(driver);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("mouseClick")) {
							FunctionLiabrary.mouseClick(driver);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("categoryTable")) {
							FunctionLiabrary.categoryTable(driver, TestData);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("captureData")) {
							FunctionLiabrary.captureData(driver, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if(Object_Type.equalsIgnoreCase("supplierTable")) {
							FunctionLiabrary.supplierTable(driver);
							test.log(LogStatus.INFO, Description);
						}
						//write as Pass in status cell
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						test.log(LogStatus.PASS, Description);
						ModuleStatus="True";
					}catch(Exception e) {
							System.out.println(e.getMessage());
							//write as Fail in status cell
							xl.setCellData(TCModule, j, 5, "Fail", outputpath);
							test.log(LogStatus.FAIL, Description);
							ModuleStatus="False";
							File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
							FileUtils.copyFile(srcFile, new File("./Screenshots/"+Description+"_"+FunctionLiabrary.captureDate()+".png"));
							String image= test.addScreenCapture("./Screenshots/"+Description+"_"+FunctionLiabrary.captureDate()+".png");
							test.log(LogStatus.FAIL, image);
							break;
					}
					catch(AssertionError e) {
							xl.setCellData(TCModule, j, 5, "Fail", outputpath);
							ModuleStatus="False";
							test.log(LogStatus.FAIL, Description+"Fail");
							File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
							FileUtils.copyFile(srcFile, new File("./Screenshots/"+Description+"_"+FunctionLiabrary.captureDate()+".png"));
							String image= test.addScreenCapture("./Screenshots/"+Description+"_"+FunctionLiabrary.captureDate()+".png");
							test.log(LogStatus.FAIL, image);
							break;
					}
					if(ModuleStatus.equalsIgnoreCase("True")) {
							xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
					}else {
							xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
					}
			}
			reports.endTest(test);
			reports.flush();
			
		}else {
						//which test cases flag to N write as Blocked into status cell
						xl.setCellData("MasterTestCases", i, 3, "Blocked", outputpath);
		}
	}
	
	
}
	
	
	
	
	
	
	
	
}	
