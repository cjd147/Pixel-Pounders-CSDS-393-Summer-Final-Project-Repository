import java.util.List;

//MedicalTest represents the type of medical test performed on an animal (NOT A TESTER CLASS FOR THE PROGRAM)
public class MedicalTestType {

    //Fields

    //name
    private String name;

    //normal ranges for each animal type (if applicable)
    private List<TestRange> testRangeList;

    //isBinaryResult
    private boolean isBinaryResult;

    //Note
    private String note;


    //Constructor
    public MedicalTestType(String name, boolean isBinaryResult){
        this.name = name;
        this.isBinaryResult = isBinaryResult;
    }
    //Getter/Setter

    //name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //isBinaryResult

    public boolean isBinaryResult() {
        return isBinaryResult;
    }

    public void setBinaryResult(boolean binaryResult) {
        isBinaryResult = binaryResult;
        if (binaryResult){
            testRangeList.clear();
        }
    }

    //note

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    //testRangeList

    public List<TestRange> getTestRangeList() {
        return testRangeList;
    }

    public void setTestRangeList(List<TestRange> testRangeList) {
        this.testRangeList = testRangeList;
    }

    //Nested Class TestRange
    public class TestRange<T extends Animal>{

        //fields

        //isBinaryTest
        private boolean isBinaryTest;

        //binaryTestGoodResult
        private boolean binaryTestGoodResult;

        //testLowerEndRange
        private Measurement testLowerEndRange;

        //testHigherEndRange
        private Measurement testHigherEndRange;

        //AnimalType
        private Class<T> animalType;


        //Constructor
        public TestRange(Class<T> animalType){
            this.animalType = animalType;

        }

        //Getter/Setters

        //isBinaryTest

        public boolean isBinaryTest() {
            return isBinaryTest;
        }

        public void setBinaryTest(boolean binaryTest) {
            isBinaryTest = binaryTest;
        }

        //binaryTestGoodResult

        public boolean isBinaryTestGoodResult() {
            return binaryTestGoodResult;
        }

        public void setBinaryTestGoodResult(boolean binaryTestGoodResult) {
            this.binaryTestGoodResult = binaryTestGoodResult;
        }

        //testLowerEndRange

        public Measurement getTestLowerEndRange() {
            return testLowerEndRange;
        }

        public void setTestLowerEndRange(Measurement testLowerEndRange) {
            if (!isBinaryTest){
                if (this.testHigherEndRange.getAmount() > testLowerEndRange.getAmount()){
                    this.testLowerEndRange = testLowerEndRange;
                } else {
                    System.out.println("Invalid input: Lower end of the range is greater than the higher");
                }
            } else {
                System.out.println("Invalid input: Test produces either a positive or negative result");
            }

        }

        //testHigherEndRange


        public Measurement getTestHigherEndRange() {
            return testHigherEndRange;
        }

        public void setTestHigherEndRange(Measurement testHigherEndRange) {
            if (!isBinaryTest) {
                if (testHigherEndRange.getAmount() > this.testLowerEndRange.getAmount()) {
                    this.testHigherEndRange = testHigherEndRange;
                } else {
                    System.out.println("Invalid input: Higher end of the range is not greater than the lower end");
                }
            } else {
                System.out.println("Invalid input: Test produces either a positive or negative result");
            }
        }


        //AnimalType

        public Class<T> getAnimalType() {
            return animalType;
        }

        public void setAnimalType(Class<T> animalType) {
            this.animalType = animalType;
        }
    }


}
