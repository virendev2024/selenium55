package testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
//video 180
public class Retry implements IRetryAnalyzer
{
    int count =0;
    int maxTry = 1;  // how many times you want to run your failed test cases
    @Override
    public boolean retry(ITestResult result) //iTestResult
    {
        if(count<maxTry)
        {
            count++;
            return true;
        }

        return false;
    }
}
