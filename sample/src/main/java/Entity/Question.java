// Player.java
package Entity;

public class Question {

    private Integer id,level,dapan;
    private String question,answerA,answerB,answerC,answerD;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDapan() {
        return dapan;
    }

    public void setDapan(Integer dapan) {
        this.dapan = dapan;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }
    public String getAnswerCorrect()
    {
        String correct="";
        switch (this.dapan%4)
        {
            case 0: correct = getAnswerA();break;
            case 1: correct = getAnswerB();break;
            case 2: correct = getAnswerC();break;
            case 3: correct = getAnswerD();break;
        }
        return correct;
    }
}
