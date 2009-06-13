
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestEnvironment
{
  public static void main(String[] args)
  {
    TestSuite suite = new TestSuite();
    
    suite.addTestSuite(Test.class);

    TestRunner.run(suite);
  }
}