
//MedicalTest represents a medical test performed on an animal (NOT A TESTER CLASS FOR THE PROGRAM)
public class MedicalTest extends MedicalData{

    //Fields

    //TestType
    private MedicalTestType testType;

    //Test Result - Binary and non-binary option
    private Measurement testResult;
    private boolean binaryTestResult;

    //Constructor
    public MedicalTest(MedicalTestType testType){
        this.testType = testType;

    }

    //Getter/Setters

    //TestType

    public MedicalTestType getTestType(){
        return testType;
    }

    //testResult

    public Object getTestResult() {
        if(testType.isBinaryResult()){
            return binaryTestResult;
        } else {
            return testResult;
        }
    }

    //setTestResult - Overloaded
    public void setTestResult(boolean binaryTestResult){
        if (testType.isBinaryResult()){
            this.binaryTestResult = binaryTestResult;
            this.testResult = null;
        } else {
            System.out.println("Invalid input - test results must be a measurement value.");
        }

    }
    public void setTestResult(Measurement testResult){
        if (!testType.isBinaryResult()){
            this.testResult = testResult;
        } else {
            System.out.println("Invalid input - test results must either be positive or negative.");
        }

    }

}
