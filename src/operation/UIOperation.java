package operation;

//import static org.testng.AssertJUnit.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.asserts.SoftAssert;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;


public class UIOperation {

	WebDriver driver;
	SoftAssert softassert;
	public UIOperation(WebDriver driver, SoftAssert softassert){
		this.driver = driver;
		this.softassert = softassert;
		
	}
	//public void perform(Properties p,String operation,String objectName,String objectType,String value) throws Exception{
	public void perform(String operation,String objectName,String objectType,String value) throws Exception{
		System.out.println("");
		
		switch (operation.toUpperCase()) {
		case "CLICK":
			//Perform click
			driver.findElement(this.getObject(objectName,objectType)).click();
			break;
		case "CLICKLISTHOMEPAGE":
			value = "//*[@id='"+value+"']";
			driver.findElement(By.xpath(value)).click();
			break;
		case "SETTEXT":
			//Set text on control
			driver.findElement(this.getObject(objectName,objectType)).sendKeys(value);
			break;
			
		case "GOTOURL":
			//Get url of application
			driver.get(value);
			driver.manage().window().maximize();
			break;
		case "GETTEXT":
			//Get text of an element
			driver.findElement(this.getObject(objectName,objectType)).getText();
			break;
		case "NAVIGATEBACK":
			//Get text of an element
			driver.navigate().back();
			break;	
		case "SUBMIT":
			driver.findElement(this.getObject(objectName,objectType)).submit();
			break;
		case "VERIFYTITLE":
			//Get text of an element
			assertEquals(driver.getTitle(),value);
			break;	
		case "SOFTVERIFYTITLE":
			//softassert.assertEquals(driver.getTitle(),value);
			softassert.assertEquals(driver.getTitle(),value,"Wrong Title");
			break;			
		case "ISELEMENTPRESENT":
			//Get text of an element
			assertTrue(driver.findElements(this.getObject(objectName,objectType)).size()>0,"Page does not contain element; "+driver.findElements(this.getObject(objectName,objectType)));
			break;
		case "SOFTISELEMENTPRESENT":
			//Get text of an element
			softassert.assertTrue(driver.findElements(this.getObject(objectName,objectType)).size()>0,"Page does not contain element; "+driver.findElements(this.getObject(objectName,objectType)));
			break;		
		case "VALIDATETEXT":
			assertTrue(driver.getPageSource().contains(value),"Page does not contain text; "+value);
			break;
		case "SOFTVALIDATETEXT":
			softassert.assertTrue(driver.getPageSource().contains(value),"Page does not contain text; "+value);
			break;	
		case "VALIDATEELEMENTTEXT":
			assertTrue(driver.findElement(this.getObject(objectName,objectType)).getText().contains(value),"Expected; "+value+" ; Actual; "+driver.findElement(this.getObject(objectName,objectType)).getText());
			break;
		case "SOFTVALIDATEELEMENTTEXT":
			softassert.assertTrue(driver.findElement(this.getObject(objectName,objectType)).getText().contains(value),"Expected; "+value+" ; Actual; "+driver.findElement(this.getObject(objectName,objectType)).getText());
			break;
		case "SOFTVALIDATEALL":
			softassert.assertAll();
			break;
		case "GETSCREENSHOTELEMENT":
			System.out.println("TEst");
			File source=driver.findElement(this.getObject(objectName,objectType)).getScreenshotAs(OutputType.FILE);
			File destFile =new File("./Screenshots/"+driver.findElement(this.getObject(objectName,objectType)).getText()+"_"+(System.currentTimeMillis())+".png");
			FileHandler.copy(source, destFile);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Find element BY using object type and value
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	
		private By getObject(String objectName,String objectType) throws Exception{
		
			if(objectType.equalsIgnoreCase("XPATH")){				
				return By.xpath(objectName);
			}
			//find by class
			else if(objectType.equalsIgnoreCase("CLASSNAME")){				
				return By.className(objectName);				
			}
			//find by name
			else if(objectType.equalsIgnoreCase("NAME")){				
				return By.name(objectName);				
			}
			
			else if(objectType.equalsIgnoreCase("ID")){				
				return By.id(objectName);				
			}
			//Find by css
			else if(objectType.equalsIgnoreCase("CSS")){				
				return By.cssSelector(objectName);				
			}
			//find by link
			else if(objectType.equalsIgnoreCase("LINK")){				
				return By.linkText(objectName);				
			}
			//find by partial link
			else if(objectType.equalsIgnoreCase("PARTIALLINK")){				
				return By.partialLinkText(objectName);				
			}
			else if(objectType.equalsIgnoreCase("")){				
				return null;				
			}else
			{
				throw new Exception("Wrong object type");
			}
	}
}
