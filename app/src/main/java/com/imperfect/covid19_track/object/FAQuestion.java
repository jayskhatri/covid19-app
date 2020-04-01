package com.imperfect.covid19_track.object;

public class FAQuestion {
    private String qno;
    private String question;
    private String answer;

    public FAQuestion() {
    }

    public FAQuestion(String qno, String question, String answer) {
        this.qno = qno;
        this.question = question;
        this.answer = answer;
    }

    public String getQno() {
        return qno;
    }

    public void setQno(String qno) {
        this.qno = qno;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
