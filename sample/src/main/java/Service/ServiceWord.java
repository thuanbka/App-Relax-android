package Service;

import Entity.QuestionWord;

import java.util.ArrayList;

public class ServiceWord  {
    public ArrayList<QuestionWord> listQuestion = new ArrayList<QuestionWord>();
    public ServiceWord()
    {
        QuestionWord word =new QuestionWord();
        word.setId("w001");
        word.setIcon("w001");
        word.setResult("mật mã");
        listQuestion.add(word);
        listQuestion.add(new QuestionWord("w002","w002","bao la"));
        listQuestion.add(new QuestionWord("w003","w003","kinh độ"));
        listQuestion.add(new QuestionWord("w004","w004","hứng thú"));
        listQuestion.add(new QuestionWord("w005","w005","nhạc cụ"));
        listQuestion.add(new QuestionWord("w006","w006","đầu thú"));
        listQuestion.add(new QuestionWord("w007","w007","xem tướng"));
        listQuestion.add(new QuestionWord("w008","w008","bóng đá"));
        listQuestion.add(new QuestionWord("w009","w009","xương rồng"));
        listQuestion.add(new QuestionWord("w010","w010","kiếm tiền"));
        listQuestion.add(new QuestionWord("w011","w011","hoa mắt"));
        listQuestion.add(new QuestionWord("w012","w012","tương lai"));
        listQuestion.add(new QuestionWord("w013","w013","cánh đồng"));
        listQuestion.add(new QuestionWord("w014","w014","chó treo mèo đậy"));
        listQuestion.add(new QuestionWord("w015","w015","lòng lang dạ thú"));
    }



    public QuestionWord questionWord(int vt)
    {
        return listQuestion.get(vt);
    }


}
