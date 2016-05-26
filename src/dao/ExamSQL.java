package dao;

import entity.exam.Exam;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public class ExamSQL extends BaseCach<Integer,Exam>{

    public Exam getExam(int id){
        Exam e = getBeanFromCatch(id);
        if(e!=null) return e;
        //getFromSQL
        return null;
    }
}
